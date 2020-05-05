package com.lab.base.controller;

import com.lab.base.pojo.Messages;
import com.lab.base.service.MessagesService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * messages控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/messages")
public class MessagesController {

	@Autowired
	private MessagesService messagesService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",messagesService.findAll());
	}


	/**
	 * 发送站内短信
	 * @param messages
	 */
	@RequestMapping(value = "/{send_id}/{receive_id}",method= RequestMethod.POST)
	public Result add(@RequestBody Messages messages, @PathVariable String send_id,@PathVariable String receive_id){
		messages.setSendId(send_id);
		messages.setReceiveId(receive_id);
		messages.setCreateTime(new Date());
		messagesService.add(messages);
		redisTemplate.opsForList().leftPush("message_"+receive_id,messages.getId());
		return new Result(true, StatusCode.OK,"发送短信成功");
	}




	/**
	 * 查询未读消息
	 * @return
	 */
	@RequestMapping(value = "/getUnReadMessage/{uid}",method= RequestMethod.GET)
	public Result getUnReadMessages(@PathVariable String uid){
		List<String> list = redisTemplate.opsForList().range("message_"+uid,0,-1);
		List<Messages> messagesList=new ArrayList<>();
		Map<String,Object> map=new HashMap<>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				messagesList.add(messagesService.findById(list.get(i)));
			}
		}
		map.put("messageList",messagesList);
		return  new Result(true, StatusCode.OK,"查询成功",map);
	}


	/**
	 * 设为已读
	 * 从redis中剔除这条
	 */
	@RequestMapping(value = "/setIsRead/{id}/{userId}",method = RequestMethod.PUT)
	public Result setIsRead(@PathVariable String id, @PathVariable String userId) {
		List<Integer> list = redisTemplate.opsForList().range("message_"+userId,0,-1);
		if(!list.contains(id))
			return new Result(false, StatusCode.ERROR,"该信息不存在");
		redisTemplate.opsForList().remove("message_"+userId,-1,id);
		return new Result(true, StatusCode.OK,"该信息已读");
	}

	/**
	 * 个人消息
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/getMessage/{uid}",method = RequestMethod.GET)
	public Result getMessages( @PathVariable String uid) {
		Map<String,Object> map=new HashMap<>();
		map.put("messagesList",messagesService.findByReceiveId(uid));
		return  new Result(true, StatusCode.OK,"所有消息",map);
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true, StatusCode.OK,"查询成功",messagesService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method= RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Messages> pageList = messagesService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK,"查询成功",  new PageResult<Messages>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK,"查询成功",messagesService.findSearch(searchMap));
    }
	

	/**
	 * 修改
	 * @param messages
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Messages messages, @PathVariable String id ){
		messages.setId(id);
		messagesService.update(messages);
		return new Result(true, StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		messagesService.deleteById(id);
		return new Result(true, StatusCode.OK,"删除成功");
	}
	
}

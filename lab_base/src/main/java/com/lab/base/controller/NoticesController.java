package com.lab.base.controller;

import com.lab.base.pojo.Notices;
import com.lab.base.pojo.User;
import com.lab.base.service.NoticesService;
import com.lab.base.service.UserService;
import com.rabbitmq.tools.json.JSONUtil;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * notices控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/notices")
public class NoticesController {

	@Autowired
	private NoticesService noticesService;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private UserService userService;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value = "getAllNotices/{send_depart}",method= RequestMethod.GET)
	public Result findAll(@PathVariable String send_depart){
		Map<String,Object> map=new HashMap<>();
		map.put("noticesList",noticesService.findAll(send_depart));
		return new Result(true, StatusCode.OK,"查询成功",map);
	}

	@RequestMapping(value = "adminGetAllNotices/{send_id}/{send_depart}",method= RequestMethod.GET)
	public Result AdminfindAll(@PathVariable String send_id,@PathVariable String send_depart){
		Map<String,Object> map=new HashMap<>();
		map.put("noticesList",noticesService.AdminfindAll(send_id,send_depart));
		return new Result(true, StatusCode.OK,"查询成功",map);
	}


	@RequestMapping(value = "getNoticesBySendId/{send_id}",method= RequestMethod.GET)
	public Result find(@PathVariable String send_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("noticesList", noticesService.findBySendId(send_id));
		return new Result(true, StatusCode.OK, "查询成功", map);
	}
	@RequestMapping(value = "getBySendDepart/{send_depart}",method= RequestMethod.GET)
	public Result findByDepart(@PathVariable String send_depart){
		Map<String,Object> map=new HashMap<>();
		map.put("noticesList",noticesService.findByDepart(send_depart));
		return new Result(true, StatusCode.OK,"查询成功",map);
	}
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable Integer id){
		return new Result(true, StatusCode.OK,"查询成功",noticesService.findById(id));
	}



	/**
	 * 获取本人未读公告
	 */
	@RequestMapping(value = "/getUnReadNotices/{uid}",method = RequestMethod.GET)
	public Result getUnReadNotices(@PathVariable String uid) {
		List<Integer> list = redisTemplate.opsForList().range("notice_"+uid,0,-1);
		//返回一个list<map> map包括发件人姓名 和 notice id titile

		List<Notices> noticesList=new ArrayList<>();
		Map<String,Object> map=new HashMap<>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				noticesList.add(noticesService.findById(list.get(i)));
			}
		}
		map.put("noticesList",noticesList);
		return  new Result(true, StatusCode.OK,"查询成功",map);
	}


	/**
	 * 发布通告
	 * @param notices
	 */
	@RequestMapping(value = "{send_id}",method= RequestMethod.POST)
	public Result add(@RequestBody Notices notices,@PathVariable String send_id){
		notices.setSendId(send_id);
		notices.setCreateTime(new Date());
		noticesService.add(notices);
		//再存入所有人的redis
		List<User> usersList = userService.findAll();
		for (User user : usersList) {
			redisTemplate.opsForList().leftPush("notice_"+user.getUid(),notices.getId());
		}

		return new Result(true, StatusCode.OK,"增加成功");
	}
	/**
	 * 设为已读
	 * 从redis中剔除这条
	 */
	@RequestMapping(value = "/setIsRead/{id}/{userId}",method = RequestMethod.PUT)
	public Result setIsRead(@PathVariable Integer id, @PathVariable String userId) {
		List<Integer> list = redisTemplate.opsForList().range("notice_"+userId,0,-1);
		boolean flag=false;
		if(list.contains(id)){
			redisTemplate.opsForList().remove("notice_"+userId,-1,id);
			return new Result(true, StatusCode.OK,"该信息已读");
		}
	return new Result(false, StatusCode.ERROR,"该信息不存在");

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
		Page<Notices> pageList = noticesService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK,"查询成功",  new PageResult<Notices>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true, StatusCode.OK,"查询成功",noticesService.findSearch(searchMap));
    }
	

	
	/**
	 * 修改
	 * @param notices
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Notices notices, @PathVariable Integer id ){
		notices.setId(id);
		noticesService.update(notices);
		return new Result(true, StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable Integer id){
		noticesService.deleteById(id);
		return new Result(true, StatusCode.OK,"删除成功");
	}
	
}

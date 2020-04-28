package com.lab.base.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lab.base.pojo.Mark;
import com.lab.base.service.MarkService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * mark控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/mark")
public class MarkController {

	@Autowired
	private MarkService markService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){

		Map<String,Object> map=new HashMap<>();
		map.put("markList", markService.findAll());
		return new Result(true,StatusCode.OK,"查询成功",map);
	}
	
	/**
	 * 根据ID查询
	 * @param noticeId ID
	 * @return
	 */
	@RequestMapping(value="/{noticeId}",method= RequestMethod.GET)
	public Result findById(@PathVariable Integer noticeId){
		return new Result(true,StatusCode.OK,"查询成功",markService.findById(noticeId));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Mark> pageList = markService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Mark>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",markService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param mark
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Mark mark  ){
		markService.add(mark);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param mark
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Mark mark, @PathVariable Integer noticeId ){
		mark.setNoticeId(noticeId);
		markService.update(mark);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param noticeId
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable Integer noticeId){
		markService.deleteById(noticeId);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}

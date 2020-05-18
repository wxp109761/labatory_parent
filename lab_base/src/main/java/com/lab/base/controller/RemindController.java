package com.lab.base.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lab.base.pojo.Remind;
import com.lab.base.service.RemindService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * remind控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/remind")
public class RemindController {

	@Autowired
	private RemindService remindService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		Map<String,Object> map=new HashMap<>();
		map.put("remindList",remindService.findAll());
		return new Result(true,StatusCode.OK,"查询成功",map);
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value = "findByUid/{uid}",method= RequestMethod.GET)
	public Result findByUid(@PathVariable String uid){
		Map<String,Object> map=new HashMap<>();
		map.put("remindList",remindService.findByUid(uid));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable Integer id){
		return new Result(true,StatusCode.OK,"查询成功",remindService.findById(id));
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
		Page<Remind> pageList = remindService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Remind>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",remindService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param remind
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Remind remind  ){
		remind.setCreateTime(new Date());
		remindService.add(remind);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param remind
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Remind remind, @PathVariable Integer id ){
		remind.setId(id);

		remindService.update(remind);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable Integer id){
		remindService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}

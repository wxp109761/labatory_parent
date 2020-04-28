package com.lab.base.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lab.base.pojo.Xjrecord;
import com.lab.base.pojo.Xjresult;
import com.lab.base.service.XjrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * xjrecord控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/xjrecord")
public class XjrecordController {

	@Autowired
	private XjrecordService xjrecordService;
	

	@RequestMapping(value = "/mine/{uid}",method = RequestMethod.GET)
	public Result findByUid(@PathVariable String uid){
		Map<String,Object> map=new HashMap<>();
		map.put("recordList", xjrecordService.QueryRecordsByUid(uid));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}

//	@RequestMapping(value = "/add",method = RequestMethod.GET)
//	public void add(){
//		xjrecordService.add();
//	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",xjrecordService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param xjid ID
	 * @return
	 */
	@RequestMapping(value="/{xjid}",method= RequestMethod.GET)
	public Result findById(@PathVariable String xjid){
		Xjrecord entity=xjrecordService.findById(xjid);
		entity.getItemList();
		return new Result(true,StatusCode.OK,"查询成功",entity);
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
		Page<Xjrecord> pageList = xjrecordService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Xjrecord>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",xjrecordService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param xjrecord
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Xjrecord xjrecord  ){
		xjrecordService.add(xjrecord);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param xjrecord
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Xjrecord xjrecord, @PathVariable String xjid ){
		xjrecord.setXjid(xjid);
		xjrecordService.update(xjrecord);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		xjrecordService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}



	
}

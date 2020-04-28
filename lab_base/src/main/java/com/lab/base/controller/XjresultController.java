package com.lab.base.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lab.base.pojo.Xjresult;
import com.lab.base.service.XjresultService;
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
 * xjresult控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/xjresult")
public class XjresultController {

	@Autowired
	private XjresultService xjresultService;

	/**
	 * 联合item表查询巡检记录的结果集
	 * @param xjid
	 * @return
	 */
	@RequestMapping(value = "/QueryById/{xjid}",method = RequestMethod.GET)
	public Result findXjResult(@PathVariable String xjid){
		Map<String,Object> map=new HashMap<>();
		map.put("resultList",xjresultService.findResult(xjid));

		return new Result(true,StatusCode.OK,"查询成功",map);
	}



	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",xjresultService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param rid ID
	 * @return
	 */
	@RequestMapping(value="/{rid}",method= RequestMethod.GET)
	public Result findById(@PathVariable String rid){
		return new Result(true,StatusCode.OK,"查询成功",xjresultService.findById(rid));
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
		Page<Xjresult> pageList = xjresultService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Xjresult>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",xjresultService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param xjresult
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Xjresult xjresult  ){
		xjresultService.add(xjresult);
		return new Result(true,StatusCode.OK,"增加成功");
	}


	/**
	 * 批量添加
	 */
	@RequestMapping(value = "/addlist",method = RequestMethod.POST)
	public  Result add(@RequestBody List<Xjresult> xjresults){
		xjresultService.add(xjresults);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 修改
	 * @param xjresult
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Xjresult xjresult, @PathVariable String rid ){
		xjresult.setRid(rid);
		xjresultService.update(xjresult);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param rid
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String rid){
		xjresultService.deleteById(rid);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * 删除
	 * @param xjid
	 */
	@RequestMapping(value="/deleteByXjId/{xjid}",method= RequestMethod.DELETE)
	public Result deleteByXjId(@PathVariable String xjid){
		xjresultService.deleteByXJId(xjid);
		return new Result(true,StatusCode.OK,"删除成功");
	}

}

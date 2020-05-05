package com.lab.base.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lab.base.pojo.Laboratory;
import com.lab.base.pojo.Xjrecord;
import com.lab.base.service.LaboratoryService;
import com.lab.base.service.XjrecordService;
import com.lab.base.service.XjresultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
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
 * laboratory控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/laboratory")
public class LaboratoryController {

	@Autowired
	private LaboratoryService laboratoryService;
	@Autowired
	private XjresultService xjresultService;
	@Autowired
	private XjrecordService xjrecordService;

	@RequestMapping(value = "/addLabItemRelations/{labId}/{itemId}",method=RequestMethod.POST)
	public Result addLabItemRelation(@PathVariable String labId,@PathVariable Integer itemId){
		return new Result(true,StatusCode.OK,"增加成功");
	}
    /**
     * 增加
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Laboratory laboratory){
        laboratoryService.add(laboratory);
        return new Result(true,StatusCode.OK,"增加成功");
    }



    /**
	 * 删除实验室及其相关的内容
	 * @param labid
	 */
	@RequestMapping(value="/{labid}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String labid){

		List<Xjrecord> entity=xjrecordService.findXJRecordByLabId(labid);
		for (int i = 0; i <entity.size() ; i++) {
			xjresultService.deleteByXJId(entity.get(i).getXjid());
		}
		xjrecordService.deleteXJRecordByLabId(labid);
		laboratoryService.deleteRelationByLabId(labid);
		laboratoryService.deleteById(labid);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	@RequestMapping(value = "/mine/{uid}",method = RequestMethod.GET)
	public Result findByUid(@PathVariable String uid){
		Map<String,Object> map=new HashMap<>();
		map.put("labList",laboratoryService.findByUid(uid));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}
	@RequestMapping(value = "/byDepartId/{depart_id}",method = RequestMethod.GET)
	public Result findByDepart_id(@PathVariable String depart_id){
		Map<String,Object> map=new HashMap<>();
		map.put("labList",laboratoryService.findByDepartId(depart_id));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		Map<String,Object> map=new HashMap<>();
		map.put("labList",laboratoryService.findAll());
		return new Result(true,StatusCode.OK,"查询成功",map);

	}


	/**
	 * 查询该实验室要检查的条目内容
	 */
	@RequestMapping(value = "/getItems/{labid}",method = RequestMethod.GET)
	public Result findItems(@PathVariable String labid){
		//Laboratory laboratory=new Laboratory()
		Map<String,Object> map=new HashMap<>();
		map.put("itemList",laboratoryService.findById(labid).getItems());
		return new Result(true,StatusCode.OK,"查询成功",map);

	}


	
	/**
	 * 根据ID查询
	 * @param labid ID
	 * @return
	 */
	@RequestMapping(value="/{labid}",method= RequestMethod.GET)
	public Result findById(@PathVariable String labid){
		return new Result(true,StatusCode.OK,"查询成功",laboratoryService.findById(labid));
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
		Page<Laboratory> pageList = laboratoryService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Laboratory>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",laboratoryService.findSearch(searchMap));
    }
	

	/**
	 * 修改
	 * @param laboratory
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Laboratory laboratory, @PathVariable String labid ){
		laboratory.setLabid(labid);
		laboratoryService.update(laboratory);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	

	
}

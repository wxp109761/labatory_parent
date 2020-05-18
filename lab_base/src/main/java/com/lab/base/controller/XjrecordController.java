package com.lab.base.controller;
import java.sql.ResultSet;
import java.util.*;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.lab.base.Util.ExportExcelUtils;


import com.lab.base.pojo.Xjrecord;
import com.lab.base.pojo.xjRecordExcel;
import com.lab.base.service.XjrecordService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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



	@Autowired
	private HttpServletRequest request;


	@RequestMapping(value = "/getRecords/{uid}/{labId}/{date1}/{date2}/{cate}",method = RequestMethod.GET)
	public Result ExportBankCkeckInfo(@PathVariable String uid, @PathVariable String labId, @PathVariable String date1, @PathVariable String date2, @PathVariable String cate) {
		List<Object> result = xjrecordService.getXjRecordExcel(uid, labId, date1, date2, cate);
		List<xjRecordExcel> list = new ArrayList<>();
		for (Object row : result) {
			Object[] rowArray = (Object[]) row;
			xjRecordExcel xjRecordExcel=new xjRecordExcel();
			xjRecordExcel.setXjid(rowArray[0]+"");
			xjRecordExcel.setLabid(rowArray[1]+"");
			xjRecordExcel.setLabel(rowArray[2]+"");
			xjRecordExcel.setXjr_uid(rowArray[3]+"");
			xjRecordExcel.setUser_name(rowArray[4]+"");
			xjRecordExcel.setItemname(rowArray[5]+"");
			xjRecordExcel.setResult(rowArray[6]+"");
			xjRecordExcel.setDescription(rowArray[7]+"");
			xjRecordExcel.setGmt_create(rowArray[8]+"");
			list.add(xjRecordExcel);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("recordexcellist",list);
		return new Result(true,StatusCode.OK,"查询成功",map);
	}

	@RequestMapping(value = "/getExcelRecord/{uid}/{labId}/{date1}/{date2}/{cate}",method = RequestMethod.GET)
	public void ExportBankCkeckInfo(HttpServletResponse response, HttpServletRequest request, @PathVariable String uid, @PathVariable String labId, @PathVariable String date1, @PathVariable String date2, @PathVariable String cate){
//		//这里是笔者实际业务需求中需要得到时间间隔。可忽略
//		String start=request.getParameter("start");
//		String end=request.getParameter("end");
//		System.out.println("打印的起始日期为："+start+"，打印的结束日期为："+end);
//		Map<String,Object> map=new HashMap<>();
//		map.put("recordexcellist",xjrecordService.getXjRecordExcel(uid,labId,date1,date2));
//		//定义导出的excel名字
		String excelName = "巡检记录表";
		List<Object> result=xjrecordService.getXjRecordExcel(uid,labId,date1,date2,cate);
		List<xjRecordExcel> list=new ArrayList<>();

		for (Object row : result) {
			Object[] rowArray = (Object[]) row;
			xjRecordExcel xjRecordExcel=new xjRecordExcel();
			xjRecordExcel.setXjid(rowArray[0]+"");
			xjRecordExcel.setLabid(rowArray[1]+"");
			xjRecordExcel.setLabel(rowArray[2]+"");
			xjRecordExcel.setXjr_uid(rowArray[3]+"");
			xjRecordExcel.setUser_name(rowArray[4]+"");
			xjRecordExcel.setItemname(rowArray[5]+"");
			xjRecordExcel.setResult(rowArray[6]+"");
			xjRecordExcel.setDescription(rowArray[7]+"");
			xjRecordExcel.setGmt_create(rowArray[8]+"");
			list.add(xjRecordExcel);
		}
		for (int i = 0; i <list.size() ; i++) {
			System.out.println(list.get(i).getLabel());
		}
		//获取需要转出的excel表头的map字段
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
		fieldMap.put("xjid","编号");
		fieldMap.put("labid","实验室编号");
		fieldMap.put("label","实验室名称");
		fieldMap.put("xjr_uid","巡检员编号");
		fieldMap.put("user_name","巡检员姓名");
		fieldMap.put("itemname","巡检条目名称");
		fieldMap.put("result","巡检条目结果");
		fieldMap.put("description","结果描述");
		fieldMap.put("gmt_create","巡检时间");
		//导出用户相关信息
		new ExportExcelUtils().export(excelName,list,fieldMap,response);
	}







	@RequestMapping(value = "/mine/{uid}",method = RequestMethod.GET)
	public Result findByUid(@PathVariable String uid){
		Map<String,Object> map=new HashMap<>();
		map.put("recordList", xjrecordService.QueryRecordsByUid(uid));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}


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



	@Autowired
	XjresultService xjresultService;
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
		xjresultService.deleteByXJId(id);
		xjrecordService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	
}

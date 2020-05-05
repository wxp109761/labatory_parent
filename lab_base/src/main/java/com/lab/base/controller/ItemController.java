package com.lab.base.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lab.base.pojo.Item;
import com.lab.base.service.ItemService;
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
 * item控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		Map<String,Object> map=new HashMap<>();
		map.put("itemList", itemService.findAll());
		return new Result(true,StatusCode.OK,"查询成功",map);
	}

	@RequestMapping(value = "/queryByBelong/{belong}",method= RequestMethod.GET)
	public Result QueryByBelong(@PathVariable String belong){

		Map<String,Object> map=new HashMap<>();
		map.put("itemList", itemService.QueryByBelong(belong));
		return new Result(true,StatusCode.OK,"查询成功",map);
	}

	/**
	 * 根据ID查询
	 * @param itemid ID
	 * @return
	 */
	@RequestMapping(value="/{itemid}",method= RequestMethod.GET)
	public Result findById(@PathVariable Integer itemid){
		return new Result(true,StatusCode.OK,"查询成功",itemService.findById(itemid));
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
		Page<Item> pageList = itemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Item>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",itemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param item
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Item item  ){
		itemService.add(item);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param item
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Item item, @PathVariable Integer itemid ){
		item.setItemid(itemid);
		itemService.update(item);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param itemid
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable Integer itemid){
		itemService.deleteById(itemid);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}

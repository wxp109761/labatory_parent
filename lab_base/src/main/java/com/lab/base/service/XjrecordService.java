package com.lab.base.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.google.gson.JsonArray;
import com.lab.base.dao.XjrecordDao;
import com.lab.base.pojo.Item;
import com.lab.base.pojo.Xjrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

/**
 * xjrecord服务层
 * 
 * @author Administrator
 *
 */
@Service
public class XjrecordService {

	@Autowired
	private XjrecordDao xjrecordDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 *根据用户id查询所有巡检记录
	 * @param uid
	 * @return
	 */
	public List<Map> QueryRecordsByUid(String uid){
		return  xjrecordDao.QueryRecords(uid);
	}



//	public void add(){
//		Xjrecord xjrecord=new Xjrecord();
//		xjrecord.setXjid("xj9");
//		xjrecord.setState("安全");
//		xjrecord.setXjrUid("78714725654990848");
//		xjrecord.setGmtCreate(new Date());
//
//		Item item1 = new Item();
//		item1.setItemid(10);
//		item1.setItemname("是否漏电");
//		item1.setBelong("1080");
//		Item item = new Item();
//		item.setItemid(11);
//		item.setItemname("火灾隐患是否存在");
//		item.setBelong("1070");
//
//		//配置用户到角色关系，可以对中间表中的数据进行维护     1-1
//		xjrecord.getItemList().add(item1);
//		//xjrecord.getItems().add(item);
//		//配置角色到用户的关系，可以对中间表的数据进行维护     1-1
//		//role.getUsers().add(user)
//		xjrecordDao.save(xjrecord);
//	}



	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Xjrecord> findAll() {
		return xjrecordDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Xjrecord> findSearch(Map whereMap, int page, int size) {
		Specification<Xjrecord> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return xjrecordDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Xjrecord> findSearch(Map whereMap) {
		Specification<Xjrecord> specification = createSpecification(whereMap);
		return xjrecordDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param xjid
	 * @return
	 */
	public Xjrecord findById(String xjid) {
		return xjrecordDao.findById(xjid).get();
	}

	/**
	 * 增加
	 * @param xjrecord
	 */
	public void add(Xjrecord xjrecord) {
		// xjrecord.setXjid( idWorker.nextId()+"" ); 雪花分布式ID生成器
		xjrecordDao.save(xjrecord);
	}

	/**
	 * 修改
	 * @param xjrecord
	 */
	public void update(Xjrecord xjrecord) {
		xjrecordDao.save(xjrecord);
	}

	/**
	 * 删除
	 * @param xjid
	 */
	public void deleteById(String xjid) {
		xjrecordDao.deleteById(xjid);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Xjrecord> createSpecification(Map searchMap) {

		return new Specification<Xjrecord>() {

			@Override
			public Predicate toPredicate(Root<Xjrecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // xjid
                if (searchMap.get("xjid")!=null && !"".equals(searchMap.get("xjid"))) {
                	predicateList.add(cb.like(root.get("xjid").as(String.class), "%"+(String)searchMap.get("xjid")+"%"));
                }
                // uid
                if (searchMap.get("uid")!=null && !"".equals(searchMap.get("uid"))) {
                	predicateList.add(cb.like(root.get("uid").as(String.class), "%"+(String)searchMap.get("uid")+"%"));
                }
                // labid
                if (searchMap.get("labid")!=null && !"".equals(searchMap.get("labid"))) {
                	predicateList.add(cb.like(root.get("labid").as(String.class), "%"+(String)searchMap.get("labid")+"%"));
                }
                // 状态：0，1，2，3
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

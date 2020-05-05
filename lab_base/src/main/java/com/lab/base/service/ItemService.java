package com.lab.base.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lab.base.dao.ItemDao;
import com.lab.base.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;



/**
 * item服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Item> findAll() {
		return itemDao.findAll();
	}

	public List<Item> QueryByBelong(String belong) {
		return itemDao.queryByBelong(belong);
	}
	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Item> findSearch(Map whereMap, int page, int size) {
		Specification<Item> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return itemDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Item> findSearch(Map whereMap) {
		Specification<Item> specification = createSpecification(whereMap);
		return itemDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param itemid
	 * @return
	 */
	public Item findById(Integer itemid) {
		return itemDao.findById(itemid).get();
	}

	/**
	 * 增加
	 * @param item
	 */
	public void add(Item item) {

	//	item.setItemid(idWorker.nextId());// 雪花分布式ID生成器
		item.setGmtCreate(new Date());
		item.setGmtUpdate(new Date());
		itemDao.save(item);
	}

	/**
	 * 修改
	 * @param item
	 */
	public void update(Item item) {
		itemDao.save(item);
	}

	/**
	 * 删除
	 * @param itemid
	 */
	public void deleteById(Integer itemid) {
		itemDao.deleteById(itemid);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Item> createSpecification(Map searchMap) {

		return new Specification<Item>() {

			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // description
                if (searchMap.get("itemname")!=null && !"".equals(searchMap.get("itemname"))) {
                	predicateList.add(cb.like(root.get("itemname").as(String.class), "%"+(String)searchMap.get("itemname")+"%"));
                }
                // 0--公共检查项目，
                if (searchMap.get("belong")!=null && !"".equals(searchMap.get("belong"))) {
                	predicateList.add(cb.like(root.get("belong").as(String.class), "%"+(String)searchMap.get("belong")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

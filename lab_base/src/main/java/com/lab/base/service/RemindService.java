package com.lab.base.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.lab.base.dao.RemindDao;
import com.lab.base.pojo.Remind;

/**
 * remind服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class RemindService {

	@Autowired
	private RemindDao remindDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Remind> findAll() {
		return remindDao.findAll();
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Remind> findByUid(String uid) {
		return remindDao.findByUidOrderByRemindTimeDesc(uid);
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Remind> findSearch(Map whereMap, int page, int size) {
		Specification<Remind> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return remindDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Remind> findSearch(Map whereMap) {
		Specification<Remind> specification = createSpecification(whereMap);
		return remindDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Remind findById(Integer id) {
		return remindDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param remind
	 */
	public void add(Remind remind) {
		// remind.setId( idWorker.nextId()+"" ); 雪花分布式ID生成器
		remindDao.save(remind);
	}

	/**
	 * 修改
	 * @param remind
	 */
	public void update(Remind remind) {
		remindDao.save(remind);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id) {
		remindDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Remind> createSpecification(Map searchMap) {

		return new Specification<Remind>() {

			@Override
			public Predicate toPredicate(Root<Remind> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // title
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // content
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // is_done
                if (searchMap.get("isDone")!=null && !"".equals(searchMap.get("isDone"))) {
                	predicateList.add(cb.like(root.get("isDone").as(String.class), "%"+(String)searchMap.get("isDone")+"%"));
                }
                // uid
                if (searchMap.get("uid")!=null && !"".equals(searchMap.get("uid"))) {
                	predicateList.add(cb.like(root.get("uid").as(String.class), "%"+(String)searchMap.get("uid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

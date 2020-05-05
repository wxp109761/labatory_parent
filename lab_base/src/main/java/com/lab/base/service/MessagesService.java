package com.lab.base.service;

import com.lab.base.dao.MessagesDao;
import com.lab.base.pojo.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * messages服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class MessagesService {

	@Autowired
	private MessagesDao messagesDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Messages> findAll() {
		return messagesDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Messages> findSearch(Map whereMap, int page, int size) {
		Specification<Messages> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return messagesDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Messages> findSearch(Map whereMap) {
		Specification<Messages> specification = createSpecification(whereMap);
		return messagesDao.findAll(specification);
	}

	/**
	 * 条件查询
	 * @return
	 */
	public List<Messages> findByReceiveId(String receiveId) {

		return messagesDao.findByReceiveId(receiveId);
	}
	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Messages findById(String id) {
		return messagesDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param messages
	 */
	public void add(Messages messages) {
		 messages.setId( idWorker.nextId()+"" ); //雪花分布式ID生成器
		messagesDao.save(messages);
	}

	/**
	 * 修改
	 * @param messages
	 */
	public void update(Messages messages) {
		messagesDao.save(messages);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		messagesDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Messages> createSpecification(Map searchMap) {

		return new Specification<Messages>() {

			@Override
			public Predicate toPredicate(Root<Messages> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // title
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // message
                if (searchMap.get("message")!=null && !"".equals(searchMap.get("message"))) {
                	predicateList.add(cb.like(root.get("message").as(String.class), "%"+(String)searchMap.get("message")+"%"));
                }
                // is_del
                if (searchMap.get("isDel")!=null && !"".equals(searchMap.get("isDel"))) {
                	predicateList.add(cb.like(root.get("isDel").as(String.class), "%"+(String)searchMap.get("isDel")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

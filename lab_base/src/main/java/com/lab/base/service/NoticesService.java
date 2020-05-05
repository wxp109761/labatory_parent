package com.lab.base.service;

import com.lab.base.dao.NoticesDao;
import com.lab.base.pojo.Notices;

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
 * notices服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class NoticesService {

	@Autowired
	private NoticesDao noticesDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Notices> findAll(String send_depart) {
		return noticesDao.queryAllNotices(send_depart);
	}
	public List<Notices> AdminfindAll(String send_id,String send_depart) {
		return noticesDao.AdminAllNotices(send_id,send_id,send_depart);
	}
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Notices> findByDepart(String send_depart) {
		return noticesDao.findBySendDepartOrderByCreateTimeDesc(send_depart);
	}

	/**
	 * 查询自己所发布的公告
	 * @param send_id
	 * @return
	 */
	public List<Notices> findBySendId(String send_id) {
			return noticesDao.findBySendIdOrderByCreateTimeDesc(send_id);
	}

	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Notices> findSearch(Map whereMap, int page, int size) {
		Specification<Notices> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return noticesDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Notices> findSearch(Map whereMap) {
		Specification<Notices> specification = createSpecification(whereMap);
		return noticesDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Notices findById(Integer id) {
		return noticesDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param notices
	 */
	public void add(Notices notices) {
		// notices.setId(idWorker.nextId()+""); //雪花分布式ID生成器
		noticesDao.save(notices);
	}

	/**
	 * 修改
	 * @param notices
	 */
	public void update(Notices notices) {
		noticesDao.save(notices);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id) {
		noticesDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Notices> createSpecification(Map searchMap) {

		return new Specification<Notices>() {

			@Override
			public Predicate toPredicate(Root<Notices> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // title
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // notice
				if (searchMap.get("notice")!=null && !"".equals(searchMap.get("notice"))) {
					predicateList.add(cb.like(root.get("notice").as(String.class), "%"+(String)searchMap.get("notice")+"%"));
				}
                // is_del
                if (searchMap.get("isDel")!=null && !"".equals(searchMap.get("isDel"))) {
                	predicateList.add(cb.like(root.get("isDel").as(String.class), "%"+(String)searchMap.get("isDel")+"%"));
                }
				// notice
				if (searchMap.get("send_depart")!=null && !"".equals(searchMap.get("send_depart"))) {
					predicateList.add(cb.like(root.get("send_depart").as(String.class), "%"+(String)searchMap.get("send_depart")+"%"));
				}
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

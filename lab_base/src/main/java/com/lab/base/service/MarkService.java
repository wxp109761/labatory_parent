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

import util.IdWorker;

import com.lab.base.dao.MarkDao;
import com.lab.base.pojo.Mark;

/**
 * mark服务层
 * 
 * @author Administrator
 *
 */
@Service
public class MarkService {

	@Autowired
	private MarkDao markDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Mark> findAll() {
		return markDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Mark> findSearch(Map whereMap, int page, int size) {
		Specification<Mark> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return markDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Mark> findSearch(Map whereMap) {
		Specification<Mark> specification = createSpecification(whereMap);
		return markDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param noticeId
	 * @return
	 */
	public Mark findById(Integer noticeId) {
		return markDao.findById(noticeId).get();
	}

	/**
	 * 增加
	 * @param mark
	 */
	public void add(Mark mark) {
		// mark.setNoticeId( idWorker.nextId()+"" ); 雪花分布式ID生成器
		markDao.save(mark);
	}

	/**
	 * 修改
	 * @param mark
	 */
	public void update(Mark mark) {
		markDao.save(mark);
	}

	/**
	 * 删除
	 * @param noticeId
	 */
	public void deleteById(Integer noticeId) {
		markDao.deleteById(noticeId);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Mark> createSpecification(Map searchMap) {

		return new Specification<Mark>() {

			@Override
			public Predicate toPredicate(Root<Mark> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // is_read
                if (searchMap.get("isRead")!=null && !"".equals(searchMap.get("isRead"))) {
                	predicateList.add(cb.like(root.get("isRead").as(String.class), "%"+(String)searchMap.get("isRead")+"%"));
                }
                // user_cate
                if (searchMap.get("userCate")!=null && !"".equals(searchMap.get("userCate"))) {
                	predicateList.add(cb.like(root.get("userCate").as(String.class), "%"+(String)searchMap.get("userCate")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

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

import com.lab.base.dao.NoticeDao;
import com.lab.base.pojo.Notice;

/**
 * notice服务层
 * 
 * @author Administrator
 *
 */
@Service
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Notice> findAll() {
		return noticeDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Notice> findSearch(Map whereMap, int page, int size) {
		Specification<Notice> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return noticeDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Notice> findSearch(Map whereMap) {
		Specification<Notice> specification = createSpecification(whereMap);
		return noticeDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Notice findById(String id) {
		return noticeDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param notice
	 */
	public void add(Notice notice) {
		// notice.setId( idWorker.nextId()+"" ); 雪花分布式ID生成器
		noticeDao.save(notice);
	}

	/**
	 * 修改
	 * @param notice
	 */
	public void update(Notice notice) {
		noticeDao.save(notice);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		noticeDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Notice> createSpecification(Map searchMap) {

		return new Specification<Notice>() {

			@Override
			public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // id
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // title
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // content
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // is_cancel
                if (searchMap.get("isCancel")!=null && !"".equals(searchMap.get("isCancel"))) {
                	predicateList.add(cb.like(root.get("isCancel").as(String.class), "%"+(String)searchMap.get("isCancel")+"%"));
                }
                // is_delete
                if (searchMap.get("isDelete")!=null && !"".equals(searchMap.get("isDelete"))) {
                	predicateList.add(cb.like(root.get("isDelete").as(String.class), "%"+(String)searchMap.get("isDelete")+"%"));
                }
                // prioryty
                if (searchMap.get("prioryty")!=null && !"".equals(searchMap.get("prioryty"))) {
                	predicateList.add(cb.like(root.get("prioryty").as(String.class), "%"+(String)searchMap.get("prioryty")+"%"));
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

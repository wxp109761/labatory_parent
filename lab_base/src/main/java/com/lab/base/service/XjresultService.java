package com.lab.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lab.base.dao.XjresultDao;
import com.lab.base.pojo.Xjresult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

/**
 * xjresult服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class XjresultService {

	@Autowired
	private XjresultDao xjresultDao;
	
	@Autowired
	private IdWorker idWorker;


	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Xjresult> findAll() {
		return xjresultDao.findAll();
	}

	/**
	 * 和项目表联合查询巡检记录
	 * @param xjid
	 * @return
	 */
	public List<Map> findResult(String xjid){
		return xjresultDao.QueryItem(xjid);
	}




	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Xjresult> findSearch(Map whereMap, int page, int size) {
		Specification<Xjresult> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return xjresultDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Xjresult> findSearch(Map whereMap) {
		Specification<Xjresult> specification = createSpecification(whereMap);
		return xjresultDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param rid
	 * @return
	 */
	public Xjresult findById(String rid) {
		return xjresultDao.findById(rid).get();
	}

	/**
	 * 增加
	 * @param xjresult
	 */
	public void add(Xjresult xjresult) {
		// xjresult.setRid( idWorker.nextId()+"" ); 雪花分布式ID生成器
		xjresultDao.save(xjresult);
	}

	public void add(List<Xjresult> xjresults){
		xjresultDao.saveAll(xjresults);
	}
	/**
	 * 修改
	 * @param xjresult
	 */
	public void update(Xjresult xjresult) {
		xjresultDao.save(xjresult);
	}

	/**
	 * 删除
	 * @param rid
	 */
	public void deleteById(String rid) {
		xjresultDao.deleteById(rid);
	}

	/**
	 * 删除
	 * @param xjid
	 */
	public void deleteByXJId(String xjid) {
		xjresultDao.deleteAllByXjid(xjid);
	}



	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Xjresult> createSpecification(Map searchMap) {

		return new Specification<Xjresult>() {

			@Override
			public Predicate toPredicate(Root<Xjresult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // rid
                if (searchMap.get("rid")!=null && !"".equals(searchMap.get("rid"))) {
                	predicateList.add(cb.like(root.get("rid").as(String.class), "%"+(String)searchMap.get("rid")+"%"));
                }
                // xjid
                if (searchMap.get("xjid")!=null && !"".equals(searchMap.get("xjid"))) {
                	predicateList.add(cb.like(root.get("xjid").as(String.class), "%"+(String)searchMap.get("xjid")+"%"));
                }
                // itemid
                if (searchMap.get("itemid")!=null && !"".equals(searchMap.get("itemid"))) {
                	predicateList.add(cb.like(root.get("itemid").as(String.class), "%"+(String)searchMap.get("itemid")+"%"));
                }
                // resullt
                if (searchMap.get("resullt")!=null && !"".equals(searchMap.get("resullt"))) {
                	predicateList.add(cb.like(root.get("resullt").as(String.class), "%"+(String)searchMap.get("resullt")+"%"));
                }
                // description
                if (searchMap.get("description")!=null && !"".equals(searchMap.get("description"))) {
                	predicateList.add(cb.like(root.get("description").as(String.class), "%"+(String)searchMap.get("description")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

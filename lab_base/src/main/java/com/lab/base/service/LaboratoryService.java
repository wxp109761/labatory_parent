package com.lab.base.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.lab.base.dao.LaboratoryDao;
import com.lab.base.pojo.Item;
import com.lab.base.pojo.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;


/**
 * laboratory服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class LaboratoryService {

	@Autowired
	private LaboratoryDao laboratoryDao;
	
	@Autowired
	private IdWorker idWorker;


	/**
	 * 增加实验室与Item对应的关系
	 * @param labId
	 * @param itemId
	 */
	public void saveLabItemRelation(String labId,Integer itemId){
		laboratoryDao.saveLabItemRelation(labId,itemId);
	}

	/**
	 * 删除实验室与item的对应关系
	 * @param labId
	 */
	public void deleteRelationByLabId(String labId){
		laboratoryDao.deleteRelationByLabId(labId);
	}





	/**
	 *  根据用户id查询laboratory列表
	 * @param uid
	 * @return
	 */
	public List<Laboratory> findByUid(String uid){
		return laboratoryDao.findByLabUid(uid);
	}

	/**
	 *  根据depart_id查询laboratory列表
	 * @param depart_id
	 * @return
	 */
	public List<Laboratory> findByDepartId(String depart_id){
		return laboratoryDao.findByDepartId(depart_id);
	}


	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Laboratory> findAll() {
		return laboratoryDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Laboratory> findSearch(Map whereMap, int page, int size) {
		Specification<Laboratory> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return laboratoryDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Laboratory> findSearch(Map whereMap) {
		Specification<Laboratory> specification = createSpecification(whereMap);
		return laboratoryDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param labid
	 * @return
	 */
	public Laboratory findById(String labid) {
		return laboratoryDao.findById(labid).get();
	}

	/**
	 * 增加
	 * @param laboratory
	 */
	public void add(Laboratory laboratory) {
		//laboratory.setLabid(idWorker.nextId()+"" );// 雪花分布式ID生成器
		//laboratory.getDepart().tDepart().setDepart_id(departId);
		laboratoryDao.save(laboratory);
	}


	/**
	 * 修改
	 * @param laboratory
	 */
	public void update(Laboratory laboratory) {
		laboratoryDao.save(laboratory);
	}

	/**
	 * 删除
	 * @param labid
	 */
	public void deleteById(String labid) {
		laboratoryDao.deleteById(labid);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Laboratory> createSpecification(Map searchMap) {

		return new Specification<Laboratory>() {

			@Override
			public Predicate toPredicate(Root<Laboratory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // labid
                if (searchMap.get("labid")!=null && !"".equals(searchMap.get("labid"))) {
                	predicateList.add(cb.like(root.get("labid").as(String.class), "%"+(String)searchMap.get("labid")+"%"));
                }
                // 门牌号
                if (searchMap.get("label")!=null && !"".equals(searchMap.get("label"))) {
                	predicateList.add(cb.like(root.get("label").as(String.class), "%"+(String)searchMap.get("label")+"%"));
                }
                // 状态，0，1，2建设中，运行，废弃
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 功能：描述
                if (searchMap.get("function")!=null && !"".equals(searchMap.get("function"))) {
                	predicateList.add(cb.like(root.get("function").as(String.class), "%"+(String)searchMap.get("function")+"%"));
                }
                // 类别
                if (searchMap.get("class")!=null && !"".equals(searchMap.get("class"))) {
                	predicateList.add(cb.like(root.get("class").as(String.class), "%"+(String)searchMap.get("class")+"%"));
                }
                // 安全状态
                if (searchMap.get("safeStatus")!=null && !"".equals(searchMap.get("safeStatus"))) {
                	predicateList.add(cb.like(root.get("safeStatus").as(String.class), "%"+(String)searchMap.get("safeStatus")+"%"));
                }
                // 安全负责人对应id
                if (searchMap.get("labUid")!=null && !"".equals(searchMap.get("labUid"))) {
                	predicateList.add(cb.like(root.get("labUid").as(String.class), "%"+(String)searchMap.get("labUid")+"%"));
                }
                //departid
				if (searchMap.get("depart_id")!=null && !"".equals(searchMap.get("depart_id"))) {
					predicateList.add(cb.like(root.get("depart_id").as(String.class), "%"+(String)searchMap.get("depart_id")+"%"));
				}
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}

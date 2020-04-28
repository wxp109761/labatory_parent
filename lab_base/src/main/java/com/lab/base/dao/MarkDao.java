package com.lab.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lab.base.pojo.Mark;
/**
 * mark数据访问接口
 * @author Administrator
 *
 */
public interface MarkDao extends JpaRepository<Mark,Integer>,JpaSpecificationExecutor<Mark>{
	
}

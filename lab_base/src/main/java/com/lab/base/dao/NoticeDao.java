package com.lab.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lab.base.pojo.Notice;
/**
 * notice数据访问接口
 * @author Administrator
 *
 */
public interface NoticeDao extends JpaRepository<Notice,String>,JpaSpecificationExecutor<Notice>{
	
}

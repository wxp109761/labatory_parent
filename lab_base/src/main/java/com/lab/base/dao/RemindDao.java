package com.lab.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lab.base.pojo.Remind;

import java.util.List;

/**
 * remind数据访问接口
 * @author Administrator
 *
 */
public interface RemindDao extends JpaRepository<Remind,Integer>,JpaSpecificationExecutor<Remind>{
	List<Remind> findByUidOrderByRemindTimeDesc(String uid);
}

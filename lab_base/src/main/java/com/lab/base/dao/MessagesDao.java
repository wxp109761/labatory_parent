package com.lab.base.dao;

import com.lab.base.pojo.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * messages数据访问接口
 * @author Administrator
 *
 */
public interface MessagesDao extends JpaRepository<Messages,String>, JpaSpecificationExecutor<Messages> {
	public List<Messages> findByReceiveId(String uid);
}

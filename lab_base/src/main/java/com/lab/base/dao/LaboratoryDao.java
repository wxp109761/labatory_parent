package com.lab.base.dao;

import com.lab.base.pojo.Item;
import com.lab.base.pojo.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * laboratory数据访问接口
 * @author Administrator
 *
 */
public interface LaboratoryDao extends JpaRepository<Laboratory,String>,JpaSpecificationExecutor<Laboratory>{
   public List<Laboratory> findByLabUid(String uid);

   @Modifying
   @Query(value = "INSERT INTO sys_lab_item VALUES(?1, ?2)", nativeQuery = true)
   void saveLabItemRelation(String labId, Integer itemId);

   @Modifying
   @Query(value = "delete from  sys_lab_item where sys_lab_id=?1", nativeQuery = true)
   void deleteRelationByLabId(String labId);

   public List<Laboratory> findByDepartId(String departId);
}

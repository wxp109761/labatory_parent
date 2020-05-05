package com.lab.base.dao;

import com.lab.base.pojo.Notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * notices数据访问接口
 * @author Administrator
 *
 */
public interface NoticesDao extends JpaRepository<Notices,Integer>, JpaSpecificationExecutor<Notices> {

    @Query(value = "SELECT *FROM notices where send_depart='0' OR send_depart=?1 ORDER BY create_time DESC",nativeQuery = true)
    public List<Notices> queryAllNotices(String send_depart);

    @Query(value = "SELECT *FROM notices WHERE (notices.send_id!=?1 AND notices.send_depart=0) OR (notices.send_id=?2)OR( notices.send_depart=?3) ORDER BY create_time DESC",nativeQuery = true)
    public List<Notices> AdminAllNotices(String send_id,String send_id2,String send_depart);




    public List<Notices> findBySendDepartOrderByCreateTimeDesc(String send_depart);
    public List<Notices> findBySendIdOrderByCreateTimeDesc(String send_id);
}

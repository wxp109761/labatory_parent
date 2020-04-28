package com.lab.base.dao;

import com.google.gson.JsonArray;
import com.lab.base.pojo.Xjrecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * xjrecord数据访问接口
 * @author Administrator
 *
 */
public interface XjrecordDao extends JpaRepository<Xjrecord,String>,JpaSpecificationExecutor<Xjrecord>{


    @Query(value = "SELECT a.xjid,b.label,a.state,a.gmt_update,a.gmt_create from xjrecord AS a LEFT JOIN laboratory AS b ON a.labid=b.labid WHERE a.xjr_uid=? ORDER BY a.gmt_create DESC",nativeQuery = true)
    public List<Map> QueryRecords(String uid);
}

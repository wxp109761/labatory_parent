package com.lab.base.dao;

import com.google.gson.JsonArray;
import com.lab.base.pojo.Xjrecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
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

    public void deleteByLabid(String labid);
    public List<Xjrecord> findByLabid(String labid);
    @Query(value="select r.xjid,r.labid,l.label,r.xjr_uid,u.username," +
            "item.itemname,res.resullt,res.description,r.gmt_create from xjrecord as r," +
            "user as u,laboratory as l,xjresult as res,xjitem as item where u.uid= r.xjr_uid " +
            "and r.labid=l.labid and res.xjid=r.xjid and item.itemid=res.itemid and xjr_uid=?",nativeQuery = true)
    List<Object> getAllRecords(String uid);
    @Query(value="  select r.xjid,r.labid,l.label,r.xjr_uid," +
            "u.username,item.itemname,res.resullt,res.description,r.gmt_create " +
            "from xjrecord as r,user as u,laboratory as l,xjresult as res,xjitem as item " +
            "where u.uid= r.xjr_uid and r.labid=l.labid and res.xjid=r.xjid" +
            " and item.itemid=res.itemid AND xjr_uid=?1 AND r.labid=?2",nativeQuery = true)
    List<Object> getRecordsByLaId(String uid,String labid);
    @Query(value="select r.xjid,r.labid,l.label,r.xjr_uid,u.username,item.itemname," +
            "res.resullt,res.description,r.gmt_create from xjrecord as r,user as u,laboratory" +
            " as l,xjresult as res,xjitem as item where u.uid= r.xjr_uid and r.labid=l.labid" +
            " and res.xjid=r.xjid and item.itemid=res.itemid AND xjr_uid=?1 " +
            "AND r.gmt_create BETWEEN ?2 AND ?3",nativeQuery = true)
    List<Object> getRecordsByDate(String uid, String date1, String date2);
    @Query(value="select r.xjid,r.labid,l.label,r.xjr_uid,u.username,item.itemname," +
            "res.resullt,res.description,r.gmt_create from xjrecord as r,user as u,laboratory" +
            " as l,xjresult as res,xjitem as item where u.uid= r.xjr_uid and r.labid=l.labid AND r.labid=?2" +
            " and res.xjid=r.xjid and item.itemid=res.itemid AND xjr_uid=?1 " +
            "AND r.gmt_create BETWEEN ?3 AND ?4",nativeQuery = true)
    List<Object> getRecordsByLabIdAndDate(String uid, String laid,String date1,String date2);

}

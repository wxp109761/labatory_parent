package com.lab.base.dao;

import com.lab.base.pojo.Xjresult;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


/**
 * xjresult数据访问接口
 * @author Administrator
 *
 */
public interface XjresultDao extends JpaRepository<Xjresult,String>,JpaSpecificationExecutor<Xjresult>{

    @Query(value = "SELECT b.itemname,a.resullt,a.description,b.itemid,b.belong from xjresult AS a LEFT JOIN xjitem AS b ON a.itemid=b.itemid WHERE a.xjid=? ORDER BY b.belong ASC",nativeQuery = true)
    public List<Map> QueryItem(String xjid);

    public void deleteAllByXjid(String xjid);
}

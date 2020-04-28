package com.lab.base.dao;

import com.lab.base.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * item数据访问接口
 * @author Administrator
 *
 */
public interface ItemDao extends JpaRepository<Item,Integer>,JpaSpecificationExecutor<Item>{


    @Query(value = "select *FROM xjitem where belong=?1 or belong=0",nativeQuery = true)
    public List<Item> queryByBelong(String belong);
}

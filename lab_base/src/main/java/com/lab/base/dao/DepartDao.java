package com.lab.base.dao;

import com.lab.base.pojo.Depart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartDao extends JpaRepository<Depart,String> , JpaSpecificationExecutor<Depart> {

}

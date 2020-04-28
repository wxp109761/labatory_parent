package com.lab.base.dao;

import com.lab.base.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaSpecificationExecutor<User>, JpaRepository<User,String> {

    public User findByTel(String phone);
    public User findByJobNumber(String jobnumber);
    public List<User> findByDepartIdAndPermission(String departId, String permission);

    public List<User> findByPermissionNot(String permission);

}

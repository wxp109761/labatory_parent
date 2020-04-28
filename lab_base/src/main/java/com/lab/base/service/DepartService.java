package com.lab.base.service;


import com.lab.base.dao.DepartDao;
import com.lab.base.pojo.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class DepartService {

    @Autowired
    private DepartDao departDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    public List<Depart> findAll(){
        String token= (String) request.getAttribute("claims_user");
        if(token==null||"".equals(token)){
            throw new RuntimeException("权限不足");
        }
        return departDao.findAll();
    }
    public Depart findById(String departid){
        return departDao.findById(departid).get();
    }
    public void save(Depart depart){
        depart.setDepart_id(idWorker.nextId()+"");
        departDao.save(depart);
    }
    public void update(Depart depart){
        departDao.save(depart);
    }

    public void deleteById(String departid){
        String token= (String) request.getAttribute("claims_user");
        if(token==null||"".equals(token)) {
            throw new RuntimeException("权限不足");
        }
        departDao.deleteById(departid);
    }

}

package com.lab.base.service;


import com.lab.base.dao.UserDao;

import com.lab.base.pojo.User;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 发送验证码
     * @param mobile
     */
    public void sendSms(String mobile) {
        //生产六位随机数
        String checkcode= RandomStringUtils.randomNumeric(6);
        //缓存中存一份
        redisTemplate.opsForValue().set("checkcode_"+mobile,checkcode,6, TimeUnit.HOURS);
        //给用户发一份
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("checkcode",checkcode);
        rabbitTemplate.convertAndSend("sms",map);
        //控制台一份
        System.out.println("验证码为"+checkcode);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user) {
        //先根据用户名查询对象
        User userLogin=userDao.findByJobNumber(user.getJobNumber());
        if(userLogin!=null&&encoder.matches(user.getPassword(),userLogin.getPassword())){
            return userLogin;
        }

        //然后拿数据库的密码匹对
        return null;
    }


    public List<User> findByDepartIdPermission(String departId,String permission){
        return userDao.findByDepartIdAndPermission(departId,permission);
    }
    public List<User> findPermissionNot(String permission){
        return userDao.findByPermissionNot(permission);
    }

    public List<User> findAll(){
        return userDao.findAll();
    }
    public List<User> findAllExceptSelf(String uid){
        return userDao.findByUidNot(uid);
    }
    public User findById(String uid){
        return userDao.findById(uid).get();
    }
    public void add(User user){
        user.setUid(idWorker.nextId()+"");
        user.setGmtCreate(new Date());
        user.setGmtUpdate(new Date());
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }
    public void update(User user){
        userDao.save(user);
    }
    public void deleteById(String uid){
        String header=request.getHeader("Authorization");
        if(header==null||"".equals(header)){
            throw  new RuntimeException("权限不足");
        }
        if(!header.startsWith("Bearer ")){
            throw  new RuntimeException("权限不足");
        }
        //得到token
        String token=header.substring(7);
        try{
            Claims claims=jwtUtil.parseJWT(token);
            String roles= (String) claims.get("roles");
            if(roles==null||!roles.equals("user")){
                throw  new RuntimeException("权限不足");
            }
        }catch (Exception e){
            throw  new RuntimeException("权限不足");
        }
        userDao.deleteById(uid);
    }

    /**
     * 条件查询
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // uid
                if (searchMap.get("uid")!=null && !"".equals(searchMap.get("uid"))) {
                    predicateList.add(cb.like(root.get("uid").as(String.class), "%"+(String)searchMap.get("uid")+"%"));
                }
                // job_number
                if (searchMap.get("jobNumber")!=null && !"".equals(searchMap.get("jobNumber"))) {
                    predicateList.add(cb.like(root.get("jobNumber").as(String.class), "%"+(String)searchMap.get("jobNumber")+"%"));
                }
                // password
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // username
                if (searchMap.get("username")!=null && !"".equals(searchMap.get("username"))) {
                    predicateList.add(cb.like(root.get("username").as(String.class), "%"+(String)searchMap.get("username")+"%"));
                }
                // depart_id
                if (searchMap.get("departId")!=null && !"".equals(searchMap.get("departId"))) {
                    predicateList.add(cb.like(root.get("departId").as(String.class), "%"+(String)searchMap.get("departId")+"%"));
                }
                // tel
                if (searchMap.get("tel")!=null && !"".equals(searchMap.get("tel"))) {
                    predicateList.add(cb.like(root.get("tel").as(String.class), "%"+(String)searchMap.get("tel")+"%"));
                }
                // permission
                if (searchMap.get("permission")!=null && !"".equals(searchMap.get("permission"))) {
                    predicateList.add(cb.like(root.get("permission").as(String.class), "%"+(String)searchMap.get("permission")+"%"));
                }
                // avatar_url
                if (searchMap.get("avatarUrl")!=null && !"".equals(searchMap.get("avatarUrl"))) {
                    predicateList.add(cb.like(root.get("avatarUrl").as(String.class), "%"+(String)searchMap.get("avatarUrl")+"%"));
                }
                return cb.or( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }










}

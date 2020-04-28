package com.lab.base.service;


import com.lab.base.dao.UserDao;
import com.lab.base.dao.XjrecordDao;
import com.lab.base.pojo.User;
import com.lab.base.pojo.Xjrecord;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

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
        System.out.println(userLogin+"xxxx");
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
        System.out.println(header+"xxx");
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










}

package com.lab.base.controller;

import com.lab.base.pojo.User;
import com.lab.base.service.UserService;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 发送短信验证码
     * @return
     */
    @RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
    public Result sendSms(@PathVariable("mobile") String mobile){
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK,"发送成功");
    }

    /**
     * 获取学院安全员信息
     */

    @RequestMapping(value = "/getUserList/{departId}/{permission}",method = RequestMethod.GET)
    public Result findAUserByDepartId(@PathVariable String departId,@PathVariable String permission){

        Map<String,Object> map=new HashMap<>();
        map.put("userList", userService.findByDepartIdPermission(departId,permission));
        return new Result(true, StatusCode.OK,"查询成功",map);
    }

    /**
     * 获取学院安全员信息
     */

    @RequestMapping(value = "/permissionNot/{permission}",method = RequestMethod.GET)
    public Result findAUserPermissionNot(@PathVariable String permission){

        Map<String,Object> map=new HashMap<>();
        map.put("userList", userService.findPermissionNot(permission));
        return new Result(true, StatusCode.OK,"查询成功",map);
    }

    @RequestMapping(value = "/exceptSelf/{uid}",method = RequestMethod.GET)
    public Result findUserExceptSelf(@PathVariable String uid){
        Map<String,Object> map=new HashMap<>();
        map.put("userList", userService.findAllExceptSelf(uid));
        return new Result(true, StatusCode.OK,"查询成功",map);
    }


    /**
     * 注册用户
     * @return
     */
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result regist(@PathVariable("code") String code, @RequestBody User user){
        String checkcodeRedis= (String) redisTemplate.opsForValue().get("checkcode_"+user.getTel());
        if(checkcodeRedis.isEmpty()){
            return new Result(false, StatusCode.ERROR,"请先获取手机验证码");
        }
        if(!checkcodeRedis.equals(code)){
            return new Result(false, StatusCode.ERROR,"验证码错误");
        }
        userService.add(user);
        return new Result(true, StatusCode.OK,"注册成功");
    }




    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        User userlogin=userService.login(user);
        if(user==null){
            return new Result(false, StatusCode.LOGINERROR,"登录失败");
        }
        //使用前后端可以通话的操作，采用JWT方式来实现
        //生成令牌
        String token=jwtUtil.createJWT(userlogin.getUid(),user.getJobNumber(),"user");
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("roles","user");
        map.put("uid",userlogin.getUid());
        map.put("jobNumber",userlogin.getJobNumber());
        map.put("username",userlogin.getUsername());
        map.put("password",userlogin.getPassword());
        map.put("departId",userlogin.getDepartId());
        map.put("permission",userlogin.getPermission());
        map.put("tel",userlogin.getTel());
        map.put("gmt_create",userlogin.getGmtCreate());
        map.put("gmt_update",userlogin.getGmtUpdate());
        return new Result(true, StatusCode.OK,"登录成功",map);
    }


    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){

        Map<String,Object> map=new HashMap<>();
        map.put("userList",userService.findAll());
        return new Result(true, StatusCode.OK,"查询成功",map);
    }

    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public Result findById(@PathVariable("uid")String uid){
        return new Result(true, StatusCode.OK,"查询成功",userService.findById(uid));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody User user){
        userService.add(user);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    @RequestMapping(value = "/{uid}",method = RequestMethod.PUT)
    public Result update(@PathVariable("uid") String uid, @RequestBody User user){
        user.setUid(uid);
        userService.update(user);
        return new Result(true, StatusCode.OK,"修改成功");
    }
    @RequestMapping(value = "/{uid}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("uid") String uid){
        userService.deleteById(uid);
        return new Result(true, StatusCode.OK,"删除成功");
    }


}

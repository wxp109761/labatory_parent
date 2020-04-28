package com.lab.base.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //无论如何都放行，具体能不能操作还是在具体的操作中去判断
        //拦截器只是负责把头中包含token令牌的进行一个解析验证
        String header=request.getHeader("Authorization");
        if(header!=null&&!"".equals(header)){
            //如果包含请求头就对其解析
            if(header.startsWith("Bearer ")){
                //得到token
                String token=header.substring(7);
                try{
                    Claims claims=jwtUtil.parseJWT(token);
                    String roles= (String) claims.get("roles");
                    if(roles!=null&&roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw  new RuntimeException("令牌有误");
                }
            }
        }
        return true;
    }
}

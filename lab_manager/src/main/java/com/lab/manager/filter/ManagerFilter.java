package com.lab.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {


    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return 任何object的值并继续执行
     * setsendzuulResponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了");
//
//        //得到request上下文
//        RequestContext currentContext=RequestContext.getCurrentContext();
//        //得到request域
//        HttpServletRequest request=currentContext.getRequest();
//        //得到头信息
//        String header=request.getHeader("Authorization");
//        System.out.println("拦截器  "+header);
//        //判断是否有头信息
//        if(header!=null&&!"".equals(header)){
//            //把头继续传下去
//            currentContext.addZuulRequestHeader("Authorization",header);
//        }
        //得到request上下文
        RequestContext currentContext=RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request=currentContext.getRequest();
        //得到头信息
        String header=request.getHeader("Authorization");
        if(header!=null&&!"".equals(header)){
            if(header.startsWith("bearer")){
                String token=header.substring(7);
                try {
                    Claims claims=jwtUtil.parseJWT(token);
                    String roles=(String)claims.get("roles");
                    if (roles.equals("user")){
                        currentContext.addZuulRequestHeader("Authorization",header);
                        currentContext.addZuulRequestHeader("claims_user",token);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);
                }
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseStatusCode(403);
                currentContext.setResponseBody("权限不足");
                currentContext.getResponse().setContentType("text/html;charset=utf-8");
            }
        }
        return null;
    }
}

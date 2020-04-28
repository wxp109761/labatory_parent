package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@ConfigurationProperties(prefix = "jwt.config")
public class JwtUtil {

    //盐和过期时间
    private String key;
    private  long tt1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTt1() {
        return tt1;
    }

    public void setTt1(long tt1) {
        this.tt1 = tt1;
    }


    /**
     * 生成Jwt
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWT(String id,String subject,String roles){
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        JwtBuilder builder= Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256,key).claim("roles",roles);
        if(tt1>0){
            builder.setExpiration(new Date(nowMillis+tt1));
        }
        return  builder.compact();
    }

    /**
     * 解析JwtStr
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}

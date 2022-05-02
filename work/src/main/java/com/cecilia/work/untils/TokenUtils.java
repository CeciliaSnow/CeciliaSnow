package com.cecilia.work.untils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    @Autowired
    private UserMapper userMapper;
    private static UserMapper staticUserMapper;
    public void init(){
        staticUserMapper = userMapper;
    }
//    生成token
    public static String getToken(User user){
        return JWT.create().
                withExpiresAt(DateUtil.offsetDay(new Date(),1)).
                withAudience(String.valueOf(user.getId())).
                sign(Algorithm.HMAC256(user.getPassword()));
    }
//    获取token中的信息
    public static User getUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            Integer userid = Integer.valueOf(aud);
            return staticUserMapper.selectById(userid);
        }catch (Exception e){

            return null;
        }
    }
}

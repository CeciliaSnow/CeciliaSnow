package com.cecilia.work.common;

import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

//    自定义的realm认证
public class Realm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;
//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        return simpleAuthorizationInfo;
    }

//    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//    用户名，密码
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//        区别于管理员登录
        int i=0;
        String a = usertoken.getUsername();
        List<String> l = Arrays.asList(a.split(","));
        for (String s : l) {
            if (s.equals(String.valueOf(3))){
                i=3;
            }else {
                a=s;
            }
        }
        if(i!=3){
            User user = userMapper.selectByUserName(a);
            String e = bc.encode(user.getPassword());
            String p = String.valueOf(usertoken.getPassword());
            boolean m = bc.matches(p, e);
            if (user==null){
                return null;
            }
            return new SimpleAuthenticationInfo(user,user.getPassword(), "");
        }else {
            User user = userMapper.selectByUserName(a);
            if (user==null){
                return null;
            }else {
                if(user.getRole().equals(String.valueOf(3))){
                    return new SimpleAuthenticationInfo(user,user.getPassword(),"");
                }else {
                    return null;
                }
            }

        }
    }
}

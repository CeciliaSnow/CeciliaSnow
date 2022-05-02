package com.cecilia.work.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.User;
import com.cecilia.work.service.UserService;

import com.cecilia.work.untils.TokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
//    注册操作
    public Result<?> register(User user) throws ParseException {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if(res!=null){
            return Result.error("-1","用户名重复");
        }
//        MD5盐值加密
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String e = bc.encode(user.getPassword());
        User userinfo = User.builder()
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .sex(user.getSex())
                .email(user.getEmail())
                .build();
        userMapper.insert(userinfo);
        return Result.success();
    }
//    登录
    public Result<?> login(User user){
//        获取用户
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        Subject subject = SecurityUtils.getSubject();
//        封装登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try{
            subject.login(token);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",user.getUsername());
            wrapper.eq("password",user.getPassword());
            User res = userMapper.selectOne(wrapper);
//        生成token
            res.setToken(TokenUtils.getToken(res));
            return Result.success(res);

        }catch (UnknownAccountException e){
            return Result.error("-1","用户名不存在！");
        }catch (IncorrectCredentialsException e){
            return Result.error("-1","密码错误！");
        }catch (AuthenticationException e){
            return Result.error("-1","有异常了！！！");
        }

//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("username",user.getUsername());
//        wrapper.eq("password",user.getPassword());
//        User res = userMapper.selectOne(wrapper);
//        if(res!=null){
//        User pwd = userMapper.selectByUserName(user.getUsername());
//            if(user.getPassword().equals(pwd.getPassword()) && user.getUsername().equals(pwd.getUsername())){
//                //        生成token
//                res.setToken(TokenUtils.getToken(res));
//                return Result.success(res);
//            }else{
//                return Result.error("-1","用户名或密码错误！！！");
//            }
//
//        }else{
//            return Result.error("-1","用户名或密码错误！！！");
//        }
    }
//    查询所有用户
    public Result<?> findPages(Integer pageNum, Integer pageSize, String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
           wrapper.like(User::getName,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);

        return Result.success(userPage);
    }
//    更新
    public Result<?> update(User user){
        userMapper.updateById(user);
        return Result.success();
    }
//    逻辑删除
    public Result<?> del(int id){
        userMapper.deleteById(id);
        return Result.success();
    }
    public Result<?> querybyid(int id){
        return Result.success(userMapper.selectById(id));
    }
}

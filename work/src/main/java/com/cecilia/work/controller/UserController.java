package com.cecilia.work.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.PuserMapper;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.Pass;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.Work;
import com.cecilia.work.service.UserService;
import com.cecilia.work.service.impl.UserServiceImpl;
import com.cecilia.work.untils.Aslist;
import com.cecilia.work.untils.TokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    Result result;
    @Autowired
    Aslist aslist;
    @Autowired
    PuserMapper puserMapper;
    @RequestMapping("/sel/{id}")
    public Result<?> sel(@PathVariable("id") int id){
        return Result.success(userMapper.selectById(id));
    }
    @RequestMapping("/role")
    public Result<?> role(@RequestBody User user){
        int i = userMapper.updateById(user);
        return Result.success(i);
    }
//    预改
    @RequestMapping("/query/{userid}/{id}")
    public Result<?> query(@PathVariable("userid") int userid,@PathVariable("id") int id){
        int c = 2;
        int num = puserMapper.num(userid, id);
        if(num==1){
            c=1;
        }
        return Result.success(c);
    }
    @RequestMapping("/register")
    public Result<?> queryUserAll(@RequestBody User user) throws ParseException {
    Result res = userService.register(user);
    return res;
    }
//    登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }
//    分页查询
    @RequestMapping("/adminusers")
    public Result<?> adminuser(@RequestParam(defaultValue="1") Integer pageNum,
                               @RequestParam(defaultValue="10") Integer pageSize,
                               @RequestParam(defaultValue="") String search) {

        Result<?> pages = userService.findPages(pageNum, pageSize, search);
        return pages;
    }
//    用户管理的更改
    @PutMapping("/adminupdate")
    public Result<?> adminupdate(@RequestBody User user){
        return userService.update(user);
    }
//    逻辑删除
    @DeleteMapping("/admindel/{id}")
    public Result<?> admindel(@PathVariable("id") int id){
        return userService.del(id);
    }
    @GetMapping("/del")
    public Result<?> del(int id){
        return null;
    }
    @RequestMapping("/querybyid/{id}")
    public Result<?> querybyid(@PathVariable("id") int id){
        return userService.querybyid(id);
    }
    @PostMapping("/adminlogin")
    public Result<?> adminlogin(@RequestBody User user){
        //        获取用户
        user.setUsername(user.getUsername()+",3");
        Subject subject = SecurityUtils.getSubject();
//        封装登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try{
            subject.login(token);
            String a= user.getUsername();
            List<String> l = Arrays.asList(a.split(","));
            for (String s : l) {
                if (s.equals(String.valueOf(3))){
                }else {
                    a=s;
                }
            }
            user.setUsername(a);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",user.getUsername());
            wrapper.eq("password",user.getPassword());
            User res = userMapper.selectOne(wrapper);
//        生成token
            res.setToken(TokenUtils.getToken(res));
            return Result.success(res);

        }catch (UnknownAccountException e){
            return Result.error("-1","用户或密码错误！");
        }catch (IncorrectCredentialsException e){
            return Result.error("-1","用户或密码错误！");
        }catch (AuthenticationException e){
            return Result.error("-1","用户或密码错误！");
        }
    }
    @PostMapping("/adminup")
    public Result<?> adminup(@RequestBody User user){
        int i = userMapper.updateById(user);
        return Result.success();
    }
    @PostMapping("/pass")
    public Result<?> passd(@RequestBody Pass passs){
        System.out.println(passs);
        User u = userMapper.selectById(passs.getId());
        if(u.getPassword().equals(passs.getPass1())){
            u.setPassword(passs.getPass2());
            userMapper.updateById(u);
            return Result.success("成功");
        }else {
            return Result.error("-1","原密码错误");
        }
    }
}

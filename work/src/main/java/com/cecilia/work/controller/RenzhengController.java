package com.cecilia.work.controller;

import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.RenzhengMapper;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.Renzheng;
import com.cecilia.work.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ren")
public class RenzhengController {
    @Autowired
    RenzhengMapper renzhengMapper;
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/create")
    public Result<?> create(@RequestBody Renzheng renzheng){
        User u = userMapper.selectById(renzheng.getUid());
        renzheng.setZhao(u.getPic2());
        int i = renzhengMapper.insert(renzheng);
        if (i>0){
            u.setIds(1);
            userMapper.updateById(u);
            return Result.success();
        }else {
            return Result.error("400","数据异常！");
        }
    }
    @GetMapping("/queryall")
    public Result<?> all(){
        List<Renzheng> r = renzhengMapper.selectList(null);
        return Result.success(r);
    }
    @GetMapping("/querybyqid/{id}")
    public Result<?> query(@PathVariable int id){
        Renzheng r = renzhengMapper.byqid(id);
        return Result.success(r);
    }
    @RequestMapping("/yes/{id}")
    public Result<?> yes(@PathVariable int id){
        Renzheng r = renzhengMapper.selectById(id);
        User user = userMapper.selectById(r.getUid());
        user.setIds(2);
        userMapper.updateById(user);
        r.setFlag(1);
        renzhengMapper.updateById(r);
        return Result.success();
    }
    @RequestMapping("/no/{id}")
    public Result<?> no(@PathVariable int id){
        int i = renzhengMapper.deleteById(id);
        return Result.success();
    }
}

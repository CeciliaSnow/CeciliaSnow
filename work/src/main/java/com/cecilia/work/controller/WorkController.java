package com.cecilia.work.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.PuserMapper;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.mapper.WorkMapper;
import com.cecilia.work.pojo.Position;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.Work;
import com.cecilia.work.service.impl.WorkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    WorkServiceImpl workService;
    @Autowired
    WorkMapper workMapper;
    @Autowired
    PuserMapper puserMapper;
//    雇佣管理
    @PostMapping("/bework/{userid}")
    public Result<?> bework(@PathVariable("userid") int userid){
        return workService.bework(userid);
    }
    @PostMapping("/bework2/{id}")
    public Result<?> bework2(@PathVariable("id") int id){
        return workService.bework2(id);
    }
//    工作管理
    @PostMapping("/work/{id}")
    public Result<?> work(@PathVariable("id") int id){
        return workService.work(id);
    }
    @PostMapping("/work2/{id}")
    public Result<?> work2(@PathVariable("id") int id){
        return workService.work2(id);
    }
    @PostMapping("/work3/{id}")
    public Result<?> work3(@PathVariable("id") int id){
        Work work = workMapper.selectById(id);
        if(work.getUser() != 0){
            User user = userMapper.selectById(work.getUser());

            return Result.success(work,user);
        }else {
            return Result.success(-1);
        }
    }
//    任务完成
    @PostMapping("/com/{id}")
    public Result<?> com(@PathVariable("id") int id){
        Work work = workMapper.selectById(id);
        work.setFlag(1);
        return Result.success(workMapper.updateById(work));
    }
    //    我的完成
    @PostMapping("/complete/{id}")
    public Result<?> complete(@PathVariable("id") int id){
        return workService.complete(id);
    }
//    雇佣完成
    @PostMapping("/complete2/{id}")
    public Result<?> complete2(@PathVariable("id") int id){
        return Result.success(workMapper.complete2(id));
    }
//    工作管理
    @GetMapping("/query")
    public Result<?> query(@RequestParam(defaultValue="") String search){
        List<Work> works;
        LambdaQueryWrapper<Work> w = Wrappers.<Work>lambdaQuery();
        w.eq(Work::getFlag,0);
        w.eq(Work::getUdown,0);
        w.eq(Work::getFdown,0);
        if (StrUtil.isNotBlank(search)){
            w.like(Work::getTitle,search);
            works = workMapper.selectList(w);
        }else{
            works = workMapper.selectList(w);
        }
        for (Work work : works) {
            work.setName1(userMapper.selectById(work.getUserid()).getName());
            work.setName2(userMapper.selectById(work.getUser()).getName());
        }
        return Result.success(works);
    }
    //    工作完成管理
    @GetMapping("/querycom")
    public Result<?> querycom(@RequestParam(defaultValue="") String search){
        List<Work> works;
        LambdaQueryWrapper<Work> w = Wrappers.<Work>lambdaQuery();
        w.eq(Work::getFlag,1);
        if (StrUtil.isNotBlank(search)){
            w.like(Work::getTitle,search);
            works = workMapper.selectList(w);
        }else{
            works = workMapper.selectList(w);
        }
        for (Work work : works) {
            work.setName1(userMapper.selectById(work.getUserid()).getName());
            work.setName2(userMapper.selectById(work.getUser()).getName());
        }
        return Result.success(works);
    }
    //    中途放弃管理
    @GetMapping("/querygive")
    public Result<?> querygive(@RequestParam(defaultValue="") String search){
        List<Work> works;

        if (StrUtil.isNotBlank(search)){
            works = workMapper.giveup0(search);
        }else{
            works = workMapper.giveup();
        }

        for (Work work : works) {
            work.setName1(userMapper.selectById(work.getUserid()).getName());
            work.setName2(userMapper.selectById(work.getUser()).getName());
        }
        return Result.success(works);
    }
//    删除
    @GetMapping("/admindel/{id}")
    public Result<?> names(@PathVariable int id){
        int i = workMapper.deleteById(id);
        return Result.success(i);
    }
//    更改负责人
    @PostMapping("/genggai")
    public Result<?> geng(@RequestBody Work work){
        return Result.success(workMapper.updateById(work));
    }
//    用户放弃
    @PostMapping("/fangqi1")
    public Result<?> udown(@RequestBody Work work){
        work.setUdown(1);
        return Result.success(workMapper.updateById(work));
    }
    //    商家放弃
    @PostMapping("/fangqi2")
    public Result<?> fdown(@RequestBody Work work){
        work.setFdown(1);
        return Result.success(workMapper.updateById(work));
    }
//    同意终止
    @PostMapping("/fangqit")
    public Result<?> ft(@RequestBody Work work){
        work.setFlag2(1);
        return Result.success(workMapper.updateById(work));
    }
//    取消终止
@PostMapping("/fangqitz/{id}")
public Result<?> zt(@PathVariable int id){
    Work work = workMapper.selectById(id);
    work.setFdown(0);
    work.setUdown(0);
    work.setMsg("");
    return Result.success(workMapper.updateById(work));
}
//    审核终止
    @GetMapping("/fangqis")
    public Result<?> fs(@RequestParam(defaultValue="") String search){
        List<Work> works;

        if (StrUtil.isNotBlank(search)){
            works = workMapper.gives2(search);
        }else{
            works = workMapper.gives20();
        }
        for (Work work : works) {
            work.setName1(userMapper.selectById(work.getUserid()).getName());
            work.setName2(userMapper.selectById(work.getUser()).getName());
        }
        return Result.success(works);
    }
    //    用户审核放弃
    @GetMapping("/fangqis1/{id}")
    public Result<?> fu(@PathVariable int id){
        List<Work> l = workMapper.gives(id);
        for (Work w : l) {
            User user = userMapper.selectById(w.getUserid());
            if(user!=null){
                w.setName1(user.getName());
            }
        }
        return Result.success(l);
    }
    //    商家审核放弃
    @GetMapping("/fangqis2/{id}")
    public Result<?> fsj(@PathVariable int id){
        List<Work> l = workMapper.gives1(id);
        for (Work w : l) {
            User user = userMapper.selectById(w.getUserid());
            if(user!=null){
                w.setName1(user.getName());
            }
        }
        return Result.success(l);
    }
    //    所有放弃
    @GetMapping("/queryf")
    public Result<?> giveup(){
        return Result.success(workMapper.giveup());
    }
    //    用户放弃
    @GetMapping("/queryf2/{id}")
    public Result<?> giveup2(@PathVariable int id){
        return Result.success(workMapper.giveup2(id));
    }
    //    商家放弃
    @GetMapping("/queryf3/{id}")
    public Result<?> giveup3(@PathVariable int id){
        return Result.success(workMapper.giveup3(id));
    }
    @DeleteMapping("/admindels/{id}")
    public Result<?> ddel(@PathVariable Integer id){
        workMapper.deleteById(id);
        return Result.success();
    }
    @PostMapping("/qq/{pid}/{uid}")
    public Result<?> d(@PathVariable int pid,@PathVariable int uid){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pid",pid);
        map.put("uid",uid);
        puserMapper.deleteByMap(map);
        return Result.success();
    }
}

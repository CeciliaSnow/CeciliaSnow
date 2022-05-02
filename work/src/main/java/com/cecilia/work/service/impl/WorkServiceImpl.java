package com.cecilia.work.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.mapper.WorkMapper;
import com.cecilia.work.mapper.WorkMapper2;
import com.cecilia.work.pojo.Position;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.UserWork;
import com.cecilia.work.pojo.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl {
    @Autowired
    UserMapper userMapper;
    @Autowired
    WorkMapper workMapper;
    @Autowired
    WorkMapper2 workMapper2;
//    我的雇佣
    public Result<?> bework(int userid){
        List<UserWork> queryall = workMapper.queryall(userid);
        return Result.success(queryall);
    }
//    雇佣详情页
        public Result<?> bework2(int id){
            Work work = workMapper.selectById(id);
            if(work.getUser() != 0){
                User user = userMapper.selectById(work.getUser());

                return Result.success(work,user);
            }else {
                return Result.success(-1);
            }

        }
//    工作管理
    public Result<?> work(int id){
        List<Work> queryall = workMapper.querywork(id);
        return Result.success(queryall);
    }
//    工作详情页
public Result<?> work2(int id){
    Work work = workMapper.selectById(id);
    if(work.getUser() != 0){
        User user = userMapper.selectById(work.getUserid());

        return Result.success(work,user);
    }else {
        return Result.success(-1);
    }

}
//    我的完成
    public Result<?> complete(int id){
        List<Work> queryall = workMapper.complete(id);
        return Result.success(queryall);
    }
}

package com.cecilia.work.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.PositionMapper;
import com.cecilia.work.mapper.TempsMapper;
import com.cecilia.work.mapper.TempsMapper2;
import com.cecilia.work.pojo.Position;
import com.cecilia.work.pojo.Temps;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.UserTemp;
import com.cecilia.work.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempServiceImpl implements TempService {
    @Autowired
    TempsMapper tempsMapper;
    @Autowired
    TempsMapper2 tempsMapper2;
    @Autowired
    PositionMapper positionMapper;
//    临时审核表添加
    public Result<?> addtemps(Temps temps){
        Temps temps1 = tempsMapper.selectOne(Wrappers.<Temps>lambdaQuery().eq(Temps::getTitle, temps.getTitle()));
        if(temps1 != null){
            return Result.error("-1","标题重复");
        }
        System.out.println(temps);
        tempsMapper.insert(temps);
        return Result.success();
    }
    public  Result<?> querytemp(int userid){
        List<Temps> temps = tempsMapper.selectList(Wrappers.<Temps>lambdaQuery().eq(Temps::getUserid, userid));
        return Result.success(temps);
    }
//    联表查询temp
    public  Result<?> getAllTemp(Integer pageNum,Integer pageSize,String search){
        LambdaQueryWrapper<Temps> wrapper = Wrappers.<Temps>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Temps::getTitle,search);
        }
        Page<Temps> userPage = tempsMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(userPage);
    }
//    审核通过插入真实表数据
    public Result<?> yes(UserTemp userTemp){
        Position position = Position.builder().
                userid(userTemp.getUserid()).
                title(userTemp.getTitle()).
                content(userTemp.getContent()).
                category(userTemp.getCategory()).
                money(userTemp.getMoney()).
                time(userTemp.getTime()).
                name(userTemp.getName()).
                people(userTemp.getPeople()).
                phone(userTemp.getPhone()).
                email(userTemp.getEmail()).
                build();
        positionMapper.insert(position);
        return Result.success();
    }
//    审核拒绝
    public Result<?> no(int id){
        tempsMapper.deleteById(id);
        return Result.success();
    }
}

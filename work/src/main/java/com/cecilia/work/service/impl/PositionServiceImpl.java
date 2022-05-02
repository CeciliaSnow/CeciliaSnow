package com.cecilia.work.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.*;
import com.cecilia.work.pojo.*;
import com.cecilia.work.service.PositionService;
import com.cecilia.work.untils.Aslist;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Aslist aslist;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    PositionMapper2 positionMapper2;
    @Autowired
    WorkMapper workMapper;
    @Autowired
    PuserMapper puserMapper;
//    查询单个
public  Result<?> querypositionbyid(int id){
    Position position = positionMapper.selectById(id);
    return Result.success(position);
}
//    查询个人
    public  Result<?> queryposition(int userid){
        LambdaQueryWrapper<Position> wrapper = Wrappers.<Position>lambdaQuery();
        wrapper.eq(Position::getUserid, userid);
        List<Position> temps = positionMapper.selectList(wrapper);
        return Result.success(temps);
    }
//    分页查询position
    public  Result<?> getAllposition(Integer pageNum, Integer pageSize, String search){
        LambdaQueryWrapper<Position> wrapper = Wrappers.<Position>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Position::getTitle,search);
        }
        Page<Position> userPage = positionMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        long size = userPage.getSize();
        return Result.success(userPage,size);
    }
//    删除职业
    public Result<?> no(int id){
        puserMapper.delall(id);
        positionMapper.deleteById(id);
        return Result.success();
    }
//    投递申请(用户)
    public Result<?> shenqing(Position position, User user){
        Position p = positionMapper.selectById(position.getId());
        User u = userMapper.selectById(user.getId());
////        把用户的ID插入职位表
////        空值判断，防止注入null值
//        if(p.getUser()=="" || p.getUser()==null){
//            p.setNum(p.getNum()+1);
//            p.setUser(String.valueOf(p.getId()));
//            positionMapper.updateById(p);
//        }else {
//            p.setNum(p.getNum()+1);
//            String s2 = aslist.getstring(p.getUser(), u.getId());
//            p.setUser(s2);
//            positionMapper.updateById(p);
//
//        }
////        把职位的ID插入到用户表
//        if(u.getPosition()=="" || u.getPosition()==null){
//            u.setPosition(String.valueOf(p.getId()));
//            userMapper.updateById(u);
//        }else {
//            String s2 = aslist.getstring(u.getPosition(), p.getId());
//            u.setPosition(s2);
//            userMapper.updateById(u);
//        }
//        return Result.success();
        Puser puser = Puser.builder().
                        pid(p.getId()).
                        uid(u.getId()).
                        aid(p.getUserid()).
                        build();
        int i = puserMapper.insert(puser);
        if (i>0){
            p.setNum(p.getNum()+1);

            positionMapper.updateById(p);

            return Result.success();
        }else {
            return Result.error("400","数据异常");
        }
    }
//    个人被接取(列表页)
    public Result<?> beaccepted(int userid){
//        List<Position> b = positionMapper.beaccepted(userid);
        List<Puser> b = puserMapper.beaccepteds(userid);
        ArrayList<Position> l = new ArrayList<>();
        for (Puser p : b) {
            Position pd = positionMapper.selectById(p.getPid());
            if(pd!=null){
            l.add(pd);
            }

        }
        return Result.success(l);
    }
//    接取详情页
    public Result<?> beaccepted2(int id){
        Position position = positionMapper.selectById(id);
        List<Puser> p = puserMapper.beaccepted2(id);
        ArrayList<User> users = new ArrayList<>();
        for (Puser pu : p) {
            users.add(userMapper.selectById(pu.getUid()));
        }
        return Result.success(users,position);
    }
//    删除接取的人
    public Result<?> nobe(int id,int userid){
        if(puserMapper.num(userid,id)>0){
            Position p = positionMapper.selectById(id);
            puserMapper.del(id, userid);
            p.setNum(p.getNum()-1);
            positionMapper.updateById(p);
            return Result.success();
        }else {
            return Result.error("400","数据异常");
        }
    }
//    同意接取的人
    public Result<?> yesbe(int id,int userid){
//    对user表进行删除
        Position position = positionMapper.selectById(id);
//        对work表插入
        Work work = Work.builder().
                userid(position.getUserid()).
                title(position.getTitle()).
                content(position.getContent()).
                category(position.getCategory()).
                money(position.getMoney()).
                time(position.getTime()).
                people(position.getPeople()).
                email(position.getEmail()).
                phone(position.getPhone()).
                user(userid).
                build();
        int i = workMapper.insert(work);
        if(i>0){
            //        删除关系
            puserMapper.delall(id);
//            User u = userMapper.selectById(userid);
//            String s = aslist.getstring2(u.getPosition(), id);
//            u.setPosition(s);
//            userMapper.updateById(u);
            positionMapper.deleteById(id);
            return Result.success();
        }else
            return Result.error("400","数据异常！");
    }
//    个人接取
    public Result<?> accepted(int id){
        List<Puser> s = puserMapper.seluid(id);
        ArrayList<Position> position = new ArrayList<>();
        for (Puser p : s) {
            position.add(positionMapper.selectById(p.getPid()));
        }
        return Result.success(position);
    }

}

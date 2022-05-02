package com.cecilia.work.controller;

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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    PositionService positionService;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    PositionMapper2 positionMapper2;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TempsMapper tempsMapper;
    @Autowired
    Aslist aslist;
    @Autowired
    PuserMapper puserMapper;
    @PostMapping("/queryposition/{userid}")
    public Result<?> query(@PathVariable("userid") int userid){
        return positionService.queryposition(userid);
    }
    @PostMapping("/querypositionbyid/{id}")
    public Result<?> querybyid(@PathVariable("id") int id){
        return positionService.querypositionbyid(id);
    }
    @GetMapping("/length")
    public Result<?> length(@RequestParam(defaultValue = "") String search){
        if(StrUtil.isNotBlank(search)){
            LambdaQueryWrapper<Position> w = Wrappers.<Position>lambdaQuery();
            w.eq(Position::getTitle,search);
            List<Position> positions = positionMapper.selectList(w);
            return Result.success(positions);
        }else {
        List<Position> positions = positionMapper.selectList(null);
        return Result.success(positions);
        }
    }

    @GetMapping("/queryallposition")
    public Result<?> query(@RequestParam(defaultValue="1") Integer pageNum,
                           @RequestParam(defaultValue="8") Integer pageSize,
                           @RequestParam(defaultValue="") String search){
        ValueOperations<String, Object> redis = redisTemplate.opsForValue();

        if (StrUtil.isNotBlank(search)){
            Result<?> pages1 = positionService.getAllposition(pageNum, pageSize, search);
            return pages1;
        }else {
            Result<?> pages2;
            if(redis.get("pages")!=null||redis.get("pages")!=""){
                pages2 = positionService.getAllposition(pageNum, pageSize, search);
                redis.set("pages",pages2);
            }else{
                Result<?> p = positionService.getAllposition(pageNum, pageSize, search);
                Result<?> p2 = (Result<?>)redis.get("pages");
                if(p.getData2().equals(p2.getData2())){
                    pages2 = (Result<?>)redis.get("pages");
                }else{
                    pages2 = p;
                }
            }
            return pages2;
        }
    }
//    分类接口
    @GetMapping("/fenlei/{key}")
    public Result<?> fenlei(@PathVariable("key") Integer key,
                            @RequestParam(defaultValue="1") Integer pageNum,
                            @RequestParam(defaultValue="8") Integer pageSize){
        String category;
//        通过数值判断哪个分类
        switch (key){
            case 1:
                category="设计";break;
            case 2:
                category="文案写作";break;
            case 3:
                category="技术";break;
            case 4:
                category="音视频";break;
            case 5:
                category="运营";break;
            case 6:
                category="市场营销";break;
            default:
                category="0";break;
        }
//        排除空值和0值
        if(StringUtils.isNotEmpty(category)){
            if(category!="0"){
//                通过mybatisplus的wrapper匹配搜索
                LambdaQueryWrapper<Position> wrapper = Wrappers.<Position>lambdaQuery();
                if(StrUtil.isNotBlank(category)){
                    wrapper.like(Position::getCategory,category);
                }
//                分页查询
                Page<Position> userPage = positionMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
                return Result.success(userPage);
            }else{
                return Result.error("400","数据错误");
            }
        }else {
            return Result.error("-1","数据异常");
        }
    }
//    更改position
    @PostMapping("/update")
    public Result<?> update(@RequestBody Position position){
        Position p = positionMapper.selectById(position.getId());
//        插入临时表
        Temps temps = Temps.builder().
                userid(position.getUserid()).
                title(position.getTitle()).
                category(position.getCategory()).
                content(position.getContent()).
                money(position.getMoney()).
                time(position.getTime()).
                name(position.getName()).
                people(position.getPeople()).
                phone(position.getPhone()).
                email(position.getEmail()).
                build();
        int i = tempsMapper.insert(temps);
        if(i>0) {
//        删除关系
            puserMapper.delall(position.getId());
//        删除职位表
            positionMapper.deleteById(position.getId());
            return Result.success();
        }else {
            return Result.error("400", "数据异常！");
        }
    }
//    申请任务
    @RequestMapping("/shenqing")
    public Result<?> shenqing(@RequestBody PP p){
        return positionService.shenqing(p.getPosition(),p.getUser());
    }
//    已经被接取
    @RequestMapping("/beaccepted/{id}")
    public Result<?> beaccepted(@PathVariable("id") int id){
        return positionService.beaccepted(id);
    }
    @RequestMapping("/beaccepted2/{id}")
    public Result<?> beaccepted2(@PathVariable("id") int id){
        return positionService.beaccepted2(id);
    }
    @GetMapping("/nobe/{id}/{userid}")
    public Result<?> nobe(@PathVariable("id") int id,@PathVariable("userid") int userid){
        return positionService.nobe(id,userid);
    }
    @GetMapping("/yesbe/{id}/{userid}")
    public Result<?> yesbe(@PathVariable("id") int id,@PathVariable("userid") int userid){
        return positionService.yesbe(id,userid);
    }
//    我接取的
    @PostMapping("/accepted/{id}")
    public Result<?> accepted(@PathVariable("id") int id){
        return positionService.accepted(id);
    }
//    详情页
    @PostMapping("/accbyid/{id}")
    public Result<?> accbyid(@PathVariable("id") int id){return positionService.querypositionbyid(id);}
    @GetMapping("/name")
    public Result<?> names(int id){
        UserPosition u = positionMapper2.selectById(id);
        return Result.success(u.getName());
    }
    @GetMapping("/no/{id}")
    public Result<?> del(@PathVariable int id){
        return positionService.no(id);
    }
//    结束招聘
    @PostMapping("/end/{id}")
    public Result<?> end(@PathVariable int id){
        puserMapper.delall(id);
        positionMapper.deleteById(id);
        return Result.success();
    }
}

package com.cecilia.work.controller;

import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.PositionMapper;
import com.cecilia.work.mapper.TempsMapper;
import com.cecilia.work.mapper.TempsMapper2;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.Temps;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.UserTemp;
import com.cecilia.work.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp")
public class TempController {
    @Autowired
    TempsMapper2 tempsMapper2;
    @Autowired
    TempService tempService;
    @Autowired
    TempsMapper tempsMapper;
    @RequestMapping("/addtemp")
    public Result<?> addtemp(@RequestBody Temps temps){
        Result<?> addtemps = tempService.addtemps(temps);
        return addtemps;
    }
    @RequestMapping("/querytemp/{userid}")
    public Result<?> query(@PathVariable("userid") int userid){
        return tempService.querytemp(userid);
    }
    @RequestMapping("/queryalltemps")
    public Result<?> query(@RequestParam(defaultValue="1") Integer pageNum,
                           @RequestParam(defaultValue="10") Integer pageSize,
                           @RequestParam(defaultValue="") String search){
        Result<?> pages = tempService.getAllTemp(pageNum, pageSize, search);
        return pages;
    }
    @RequestMapping("/yes")
    public Result<?> yes(@RequestBody UserTemp userTemp){
        Result<?> yes = tempService.yes(userTemp);
        tempService.no(userTemp.getId());
        return yes;
    }
    @RequestMapping("/no/{id}")
    public Result<?> no(@PathVariable("id") int id){
        return tempService.no(id);
    }
//    更新审核表
    @PutMapping("/update")
    public Result<?> update(@RequestBody Temps temps){
        int i = tempsMapper.updateById(temps);
        return Result.success();
    }
}

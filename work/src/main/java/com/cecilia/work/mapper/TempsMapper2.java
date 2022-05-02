package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.UserTemp;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TempsMapper2 extends BaseMapper<UserTemp> {
    @Select("SELECT temps.id,temps.userid,NAME,title,content,category,money,TIME,temps.createtime FROM temps,USER WHERE temps.userid = user.id")
    List<UserTemp> getAllTemps();
}

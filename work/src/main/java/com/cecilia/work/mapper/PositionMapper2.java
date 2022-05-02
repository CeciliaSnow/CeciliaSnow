package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.UserPosition;
import com.cecilia.work.pojo.UserTemp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PositionMapper2 extends BaseMapper<UserPosition> {
    @Select("SELECT position.id,position.userid,NAME,title,content,category,money,TIME,position.createtime FROM position,USER WHERE position.userid = user.id")
    List<UserPosition> getAllposition();
    @Select("SELECT position.id,position.userid,user.name,title,content,category,money,TIME,position.createtime FROM position,USER WHERE position.userid = user.id and position.id = #{id}")
    UserPosition getAllpositionbyid(int id);
}

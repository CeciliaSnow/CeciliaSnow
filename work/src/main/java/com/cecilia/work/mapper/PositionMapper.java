package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.Position;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionMapper extends BaseMapper<Position> {
    @Select("select * from position where userid = #{userid} and user is not null")
    List<Position> beaccepted(int userid);
    @Select("select * from position where category = #{category}")
    List<Position> fenlei(String category);
}

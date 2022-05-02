package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.Renzheng;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RenzhengMapper extends BaseMapper<Renzheng> {
    @Select("select * from renzheng where qid=#{qid}")
    Renzheng byqid(int qid);
}

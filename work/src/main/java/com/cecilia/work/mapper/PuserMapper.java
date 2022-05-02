package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.Puser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface PuserMapper extends BaseMapper<Puser> {
    @Select("select count(*) from puser where uid=#{userid} and pid=#{id}")
    Integer num(int userid,int id);
    @Select("select * from puser where aid = #{userid}")
    List<Puser> beaccepteds(int userid);
    @Select("select * from puser where pid = #{id}")
    List<Puser> beaccepted2(int id);
//    删除单个关系
    @Select("delete from puser where pid=#{id} and uid=#{userid}")
    Integer del(int id,int userid);
//    删除所有关系
    @Select("delete from puser where pid=#{id}")
    Integer delall(int id);
//    我接取的
    @Select("select * from puser where uid=#{id}")
    List<Puser> seluid(int id);
}

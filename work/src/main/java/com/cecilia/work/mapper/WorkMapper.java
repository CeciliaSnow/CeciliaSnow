package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.UserWork;
import com.cecilia.work.pojo.Work;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkMapper extends BaseMapper<Work> {
    @Select("select * from work where work.user is not null and userid = #{userid} and work.flag<>1 and flag2<>1 and fdown<>1")
    List<UserWork> queryall(int userid);
    @Select("select * from work where  user = #{id} and flag != 1 and udown!=1 and flag2!=1")
    List<Work> querywork(int id);
    @Select("select * from work where flag = 1 and user = #{id}")
    List<Work> complete(int id);
    @Select("select * from work where flag = 1 and userid = #{id}")
    List<Work> complete2(int id);
//    所有放弃
    @Select("select * from work where flag2=1 and (udown = 1 or fdown = 1)")
    List<Work> giveup();
//    模糊查询
    @Select("select * from work where flag2=1 and (udown = 1 or fdown = 1) and title like '%${search}%'")
    List<Work> giveup0(String search);
    //    用户查询审核
    @Select("select * from work where user=#{id} and flag2=0 and udown = 1 and flag2<>1")
    List<Work> gives(int id);
    //    商家查询审核
    @Select("select * from work where userid=#{id} and flag2=0 and fdown = 1 and flag2<>1")
    List<Work> gives1(int id);
    //    所有查询审核
    @Select("select * from work where (udown = 1 or fdown = 1) and flag2=0")
    List<Work> gives20();
    @Select("select * from work where (udown = 1 or fdown = 1) and flag2=0 and title like '%${search}%'")
    List<Work> gives2(String search);
//    用户放弃
    @Select("select * from work where user=#{id} and flag2=1 and (udown = 1 or fdown = 1)")
    List<Work> giveup2(int id);
    //    商家放弃
    @Select("select * from work where userid=#{id} and flag2=1 and (udown = 1 or fdown = 1)")
    List<Work> giveup3(int id);
}

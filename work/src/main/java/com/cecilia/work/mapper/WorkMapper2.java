package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.UserWork;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WorkMapper2 extends BaseMapper<UserWork> {
    @Select("select * from work where work.user is not null and userid = #{userid}")
    List<UserWork> queryall(int userid);
    @Select("select * from work where  user = #{id}")
    List<UserWork> querywork(int id);
    @Select("select * from work where flag = 1 and user = #{id}")
    List<UserWork> complete(int id);

}

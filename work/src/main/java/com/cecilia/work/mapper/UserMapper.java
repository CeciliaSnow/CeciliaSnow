package com.cecilia.work.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cecilia.work.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select password,username,role from user where username=#{username}")
    User selectByUserName(String username);
//    改role
    @Update("update user set role = #{role} where id = #{id}")
    int updaterole(int id,String role);

}

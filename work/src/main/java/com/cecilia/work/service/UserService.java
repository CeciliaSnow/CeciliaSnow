package com.cecilia.work.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cecilia.work.handle.Result;
import com.cecilia.work.pojo.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
public interface UserService {
    public Result<?> register(User user) throws ParseException;
    public Result<?> findPages(Integer pageNum, Integer pageSize, String search);
    public Result<?> update(User user);
    public Result<?> del(int id);
    public Result<?> querybyid(int id);
    public Result<?> login(User user);
}

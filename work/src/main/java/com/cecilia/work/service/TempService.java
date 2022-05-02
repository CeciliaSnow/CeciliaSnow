package com.cecilia.work.service;

import com.cecilia.work.handle.Result;
import com.cecilia.work.pojo.Temps;
import com.cecilia.work.pojo.UserTemp;

public interface TempService {
    public Result<?> addtemps(Temps temps);
    public Result<?> querytemp(int userid);
    public  Result<?> getAllTemp(Integer pageNum,Integer pageSize,String search);
    public Result<?> yes(UserTemp userTemp);
    public Result<?> no(int id);
}

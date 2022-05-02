package com.cecilia.work.service;

import com.cecilia.work.handle.Result;
import com.cecilia.work.pojo.Position;
import com.cecilia.work.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface PositionService {
    public  Result<?> querypositionbyid(int id);
    public Result<?> queryposition(int userid);
    public Result<?> getAllposition(Integer pageNum, Integer pageSize, String search);
    public Result<?> no(int id);
    public Result<?> shenqing(Position position, User user);
    public Result<?> beaccepted(int id);
    public Result<?> beaccepted2(int id);
    public Result<?> nobe(int id,int userid);
    public Result<?> yesbe(int id,int userid);
    public Result<?> accepted(int id);
}

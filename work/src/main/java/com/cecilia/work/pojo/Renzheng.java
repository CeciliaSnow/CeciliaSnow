package com.cecilia.work.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Renzheng {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int uid;
    private String name;
    private String user;
    private String phone;
    private String email;
    private String zhao;
    private int flag;
}

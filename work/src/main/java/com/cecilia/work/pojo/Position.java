package com.cecilia.work.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userid;
    private String name;
    private String title;
    private String content;
    private String category;
    private int money;
    private int time;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    private int num;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String user;
    @TableField(exist = false)
    private int flag;
    private String people;
    private String phone;
    private String email;
}

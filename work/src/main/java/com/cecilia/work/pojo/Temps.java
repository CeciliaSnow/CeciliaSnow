package com.cecilia.work.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Temps {
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
    private String people;
    private String phone;
    private String email;


}

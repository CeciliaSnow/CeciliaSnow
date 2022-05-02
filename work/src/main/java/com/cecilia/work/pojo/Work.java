package com.cecilia.work.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userid;

    private String title;
    private String content;
    private String category;
    private int money;
    private int time;
    private int user;
//    发布人
    @TableField(exist = false)
    private String name1;
//    接收人
    @TableField(exist = false)
    private String name2;
    private int flag;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    private String people;
    private String email;
    private String phone;
    private int udown;
    private int fdown;
    private String msg;
    private int flag2;
}

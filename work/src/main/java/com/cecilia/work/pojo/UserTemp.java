package com.cecilia.work.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTemp extends User{
    private Integer id;
    private int userid;
    private String name;
    private String title;
    private String content;
    private String category;
    private int money;
    private int time;
    private Date createtime;
    private String people;
    private String phone;
    private String email;
}

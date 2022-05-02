package com.cecilia.work.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWork extends Work{
    private Integer id;
    private String name;
    private String title;
    private String content;
    private String category;
    private int money;
    private int time;
    private String people;
    private String email;
    private String phone;
    private int udown;
    private int fdown;
}

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
@AllArgsConstructor
@NoArgsConstructor
public class Puser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int pid;
    private int uid;
    private int qid;
    private int aid;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;
}

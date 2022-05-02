package com.cecilia.work.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pass {
    private Integer id;
    private String pass1;
    private String pass2;
}

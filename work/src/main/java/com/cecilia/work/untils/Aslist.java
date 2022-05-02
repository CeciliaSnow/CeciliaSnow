package com.cecilia.work.untils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class Aslist {
//    添加数据
    public String getstring(String s,int i){
//        List<String> l = Arrays.asList(s.split(","));
//        l.add(String.valueOf(i));
//        return StringUtils.join(l,",");
        return s+","+i;
    }
//    去除字符串中的单个数据
    public String getstring2(String s,int j){
        List<String> l = Arrays.asList(s.split(","));
        ArrayList<Integer> l2= new ArrayList<Integer>();
        for (String l1 : l) {
            if(!l1.equals(String.valueOf(j))){
                l2.add(Integer.valueOf(l1));
            }
        }
        String s2 = StringUtils.join(l2,",");
        return s2;
    }
//   把字符串转换为整型列表数据
    public List<Integer> getlist(String s){
        List<String> l = Arrays.asList(s.split(","));
        ArrayList<Integer> l2= new ArrayList<Integer>();
        for (String l1 : l) {
            l2.add(Integer.valueOf(l1));
        }
        return l2;
    }
}

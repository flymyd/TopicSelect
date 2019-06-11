package com.tjrac.topicselect;

import com.alibaba.fastjson.JSON;


import java.util.Date;
public class TimeCheckTest {
    public static void main(String[] args){
        Long upsta=new Long("1545667200000");
        System.out.println(new Date().getTime());
        System.out.println(upsta>new Date().getTime());
    }
}

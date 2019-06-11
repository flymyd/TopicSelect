package com.tjrac.topicselect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;


/**
 * @author myd
 */
@MapperScan("com.tjrac.topicselect.mapper")
@Controller
@SpringBootApplication
public class TopicselectApplication {
    /**
     * flymyd@foxmail.com
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TopicselectApplication.class, args);
    }
}

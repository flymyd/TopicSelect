package com.tjrac.topicselect.bean;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class CreateUserFromExcel {
    /**
     * 真实姓名
     */
    public String truename;
    /**
     * 用户名
     */
    public String username;
    /**
     * 权限 0管理员 1教师 2学生
     */
    public String permission;
}

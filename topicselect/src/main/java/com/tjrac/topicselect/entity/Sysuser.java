package com.tjrac.topicselect.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author myd
 */
@Data
@Entity
public class Sysuser implements Serializable {
    private static final long serialVersionUID = 161711348029799178L;
    @Id
    private int id;
    /**
     * 用户名（学号）
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态 0可用 1禁用
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 真实姓名
     */
    private String truename;
    /**
     * 0管理员 1教师 2学生（默认）
     */
    private int permission;
    /**
     * 最后登录时间
     */
    private Date lastlogin;
    /**
     * 登录令牌
     */
    private String token;
}

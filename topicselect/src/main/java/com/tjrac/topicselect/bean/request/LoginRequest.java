package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class LoginRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码（未加密）
     */
    private String password;
}

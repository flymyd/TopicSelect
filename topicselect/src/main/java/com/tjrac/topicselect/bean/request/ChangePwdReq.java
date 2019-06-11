package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class ChangePwdReq {
    /**
     * 旧密码
     */
    public String oldPassword;
    /**
     * 新密码
     */
    public String newPassword;
}

package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class ManualAddUserReq {
    private String username;
    private String password;
    private String truename;
    private String permission;
    private String mobile;
    private String email;
}

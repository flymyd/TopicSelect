package com.tjrac.topicselect.bean.response;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class AppLoginResp {
    public String authToken;
    public int userPermission;
}

package com.tjrac.topicselect.bean.response;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class GetTopicSelSelectedResp {
    public String truename;
    public String username;
    public Integer topicid;
    public Integer userid;
    public String selecttime;
    public String topictitle;
}

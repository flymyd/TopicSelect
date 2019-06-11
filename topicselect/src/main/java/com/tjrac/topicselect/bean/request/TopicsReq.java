package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class TopicsReq {
    /**
     * 课题ID
     */
    private int id;
    /**
     * 申请人的用户ID
     */
    private Integer authorid;
    /**
     * 课题标题
     */
    private String topictitle;
    /**
     * 课题人数
     */
    private Integer sumnum;
    /**
     * 可选人数
     */
    private Integer availablenum;
    /**
     * 难易度
     */
    private String level1;
    /**
     * 实习地点（默认：不限）
     */
    private String address;
    /**
     * 实现技术（默认：不限）
     */
    private String technology;
    /**
     * 课题内容
     */
    private String topicdetail;
    /**
     * 状态 0未审核 1已审核 2失效
     */
    private int status;
}

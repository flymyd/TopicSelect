package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class ApplyTopicReq {
    /**
     * 学生用户ID
     */
    private Integer studentId;
    /**
     * 课题ID
     */
    private Integer topicId;
}

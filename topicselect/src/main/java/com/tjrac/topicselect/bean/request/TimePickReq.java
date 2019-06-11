package com.tjrac.topicselect.bean.request;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class TimePickReq {
    /**
     * 上传、审核课题时间
     */
    private String uploadstart;
    private String uploadend;
    /**
     * 选题时间
     */
    private String choosestart;
    private String chooseend;
}

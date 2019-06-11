package com.tjrac.topicselect.bean.response;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class StuFinalCheckResp {
    /**
     * 课题标题
     */
    public String topictitle;
    /**
     * 导师姓名
     */
    public String teachername;
    /**
     * 难度
     */
    public String level1;
    /**
     * 实习地点
     */
    public String topicaddress;
    /**
     * 所用技术
     */
    public String technology;
    /**
     * 课题详情
     */
    public String topicdetail;

}

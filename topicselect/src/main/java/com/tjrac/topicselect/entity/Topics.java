package com.tjrac.topicselect.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author myd
 */
@Entity
@Data
public class Topics implements Serializable {
    private static final long serialVersionUID = -826844604548222625L;
    @Id
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
    private Integer status;
    /**
     * 已选人数
     */
    private Integer selectednum;
}

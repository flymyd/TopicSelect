package com.tjrac.topicselect.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author myd
 */
@Data
@Entity
public class Topicsel implements Serializable {
    private static final long serialVersionUID = -4544066373251904637L;
    @Id
    private int id;
    /**
     * 选题学生ID，唯一值
     */
    private Integer selectuserid;
    /**
     * 课题ID
     */
    private Integer topicid;
    /**
     * 申请时间
     */
    private String createtime;
    /**
     * 状态 0待确认 1确认完毕 2打回
     */
    private Integer status;
}

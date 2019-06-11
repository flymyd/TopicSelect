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
public class Sysopentime implements Serializable{
    private static final long serialVersionUID = 1204026163093619263L;
    @Id
    private int id;
    /**
     * 上传、审核课题时间
     */
    private Date uploadstart;
    private Date uploadend;
    /**
     * 选题时间
     */
    private Date choosestart;
    private Date chooseend;
}

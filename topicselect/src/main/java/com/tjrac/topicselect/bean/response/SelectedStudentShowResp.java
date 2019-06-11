package com.tjrac.topicselect.bean.response;

import lombok.Data;

/**
 * @author myd
 */
@Data
public class SelectedStudentShowResp {
    //学生姓名
    public String stutruename;
    //学生账号
    public String stuusername;
    //课题名称
    public String topictitle;
    //申请时间
    public String createtime;
    //课题内容
    public String topicdetail;
    //课题ID
    public Integer topicid;
    //学生ID
    public Integer stuid;
    //教师姓名
    public String teachername;
}

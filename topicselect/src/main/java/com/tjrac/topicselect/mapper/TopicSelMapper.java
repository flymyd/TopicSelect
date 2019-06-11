package com.tjrac.topicselect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.entity.Topicsel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author myd
 */
public interface TopicSelMapper extends BaseMapper<Topicsel> {
    @Select("SELECT topicsel.id,topicsel.selectuserid,topicsel.topicid,topicsel.createtime,topicsel.status\n" +
            "FROM topicsel \n" +
            "INNER JOIN topics\n" +
            "ON topics.id=topicsel.topicid\n" +
            "WHERE topics.authorid=#{authorId}\n" +
            "AND topicsel.status=1")
    List<Topicsel> selectedStudentsShowByTeacherId(int authorId);

    @Select("SELECT sysuser.truename FROM sysuser\n" +
            "INNER JOIN topics\n" +
            "ON topics.authorid=sysuser.id\n" +
            "WHERE topics.id=#{topicId}")
    Sysuser selectUserByTopicId(int topicId);

    @Select("SELECT DISTINCT selectuserid FROM `topicsel` WHERE `status`<>1")
    List<Topicsel> selectUserIdNe1();

    @Select("SELECT DISTINCT sysuser.* FROM sysuser \n" +
            "INNER JOIN topicsel\n" +
            "ON topicsel.selectuserid=sysuser.id\n" +
            "WHERE selectuserid=#{selectuserid}")
    Sysuser unChoseSysuser(int selectuserid);


}

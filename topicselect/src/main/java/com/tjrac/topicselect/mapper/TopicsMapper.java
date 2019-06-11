package com.tjrac.topicselect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjrac.topicselect.bean.response.UnChoseTopicsResp;
import com.tjrac.topicselect.entity.Topics;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author myd
 */
public interface TopicsMapper extends BaseMapper<Topics> {
    @Select("SELECT topics.*,sysuser.truename AS teachername \n" +
            "FROM topics\n" +
            "INNER JOIN sysuser\n" +
            "ON topics.authorid=sysuser.id\n" +
            "WHERE topics.status=1")
    List<UnChoseTopicsResp> selectUnChoseTopics();
}

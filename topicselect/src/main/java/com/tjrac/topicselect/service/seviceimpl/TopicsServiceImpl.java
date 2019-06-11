package com.tjrac.topicselect.service.seviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjrac.topicselect.entity.Topics;
import com.tjrac.topicselect.mapper.TopicsMapper;
import com.tjrac.topicselect.service.TopicsService;
import org.springframework.stereotype.Service;

/**
 * @author myd
 */
@Service
public class TopicsServiceImpl extends ServiceImpl<TopicsMapper, Topics> implements TopicsService {
}

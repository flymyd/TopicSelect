package com.tjrac.topicselect.service.seviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjrac.topicselect.entity.Topicsel;
import com.tjrac.topicselect.mapper.TopicSelMapper;
import com.tjrac.topicselect.service.TopicSelService;
import org.springframework.stereotype.Service;

/**
 * @author myd
 */
@Service
public class TopicSelServiceImpl extends ServiceImpl<TopicSelMapper, Topicsel> implements TopicSelService {
}

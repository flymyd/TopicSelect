package com.tjrac.topicselect.service.seviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.mapper.SysuserMapper;
import com.tjrac.topicselect.service.SysuserService;
import org.springframework.stereotype.Service;

/**
 * @author myd
 */
@Service
public class SysuserServiceImpl extends ServiceImpl<SysuserMapper, Sysuser> implements SysuserService {
}

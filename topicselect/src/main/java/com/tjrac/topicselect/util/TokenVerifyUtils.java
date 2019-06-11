package com.tjrac.topicselect.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.service.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author myd
 */
@Deprecated
public class TokenVerifyUtils {
    @Autowired
    SysuserService sysuserService;
    public Boolean tokenVerify(String token){
        Sysuser sysuser=new Sysuser();
        QueryWrapper<Sysuser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("token",token);
        Sysuser sysuser1 = sysuserService.getOne(queryWrapper);
        if (sysuser1!=null){
            return true;
        }else {
            return false;
        }
    }
}

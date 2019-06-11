package com.tjrac.topicselect;

import com.tjrac.topicselect.config.Const;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.util.PasswordUtils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;


public class PwdGenTest {
    public static void main(String[] args){
        System.out.println(Hex.encodeToString(new SimpleHash("MD5","admin",Const.MD5_PWD_SALT).getBytes()));
        System.out.println(new PasswordUtils("admin").getEncryptedPwd());
    }
}

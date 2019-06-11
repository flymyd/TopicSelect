package com.tjrac.topicselect.util;

import com.tjrac.topicselect.config.Const;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author myd
 */
public class PasswordUtils {
    private String pwdstr;
    public PasswordUtils(String pwdstr){
        this.pwdstr=pwdstr;
    }
    public String getEncryptedPwd() {
        return Hex.encodeToString(new SimpleHash("MD5", this.pwdstr, Const.MD5_PWD_SALT).getBytes());
    }
}

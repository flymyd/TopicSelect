//package com.tjrac.topicselect.util.shiro;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.tjrac.topicselect.config.Const;
//import com.tjrac.topicselect.entity.Sysuser;
//import com.tjrac.topicselect.service.SysuserService;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Author: XiaoBingBy
// * Email: XiaoBingBy@qq.com
// * Date: 2017/9/1
// * Time: 00:00
// * Describe: 自定义ShiroRealm 认证 授权
// */
//public class MyShiroRealm extends AuthorizingRealm {
//
//    private static final transient Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);
//
//    @Autowired
//    private SysuserService iUserService;
//
//    /**
//     * 认证信息.(身份验证)
//     * @param token
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
//
//        // 获取用户的输入的账号.
//        String username = (String)token.getPrincipal();
//
//        //查询用户信息
//        QueryWrapper<Sysuser> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        Sysuser user = iUserService.getOne(queryWrapper);
//        if (user == null) {
//            return null;
//        }
//        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
//                ByteSource.Util.bytes(Const.MD5_PWD_SALT),"MyShiroRealm");
//        System.out.println(simpleAuthenticationInfo.toString());
//        return simpleAuthenticationInfo;
//    }
//
//    /**
//     * 权限信息.(授权):
//     * @param principals
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        logger.info("MyShiroRealm.doGetAuthorizationInfo()");
//
//        // 获取用户信息
//        Sysuser user = (Sysuser) principals.getPrimaryPrincipal();
//
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//
//        // 查询用户拥有那些权限
//        String permission = String.valueOf(user.getPermission());
//
//        // 添加权限代码
//        authorizationInfo.addStringPermissions(Collections.singleton(permission));
//
//        return authorizationInfo;
//    }
//
//}

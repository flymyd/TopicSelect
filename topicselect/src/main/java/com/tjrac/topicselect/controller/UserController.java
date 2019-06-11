package com.tjrac.topicselect.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjrac.topicselect.bean.request.ChangeInfoReq;
import com.tjrac.topicselect.bean.request.ChangePwdReq;
import com.tjrac.topicselect.bean.request.LoginRequest;
import com.tjrac.topicselect.bean.response.AppLoginResp;
import com.tjrac.topicselect.config.Const;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.service.SysuserService;
import com.tjrac.topicselect.util.PasswordUtils;
import com.tjrac.topicselect.util.shiro.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author myd
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SysuserService sysuserService;

    /**
     * 验证码
     */
    @GetMapping(value = {"/getCaptcha","/api/users/getCaptcha"})
    public R getCaptchaResp(){
        String currentSystemTime=String.valueOf(System.currentTimeMillis());
        Integer answer=new Integer(currentSystemTime.substring(currentSystemTime.length()-2));
        int a=0;
        int b=0;
        //加法
        if (answer%2==0){
            a=new Random().nextInt(answer/2);
        }else {
            a=new Random().nextInt((answer-1)/2);
        }
        b=answer-a;
        Map<String,String> captchaMap=new HashMap<>();
        captchaMap.put("question",a+"+"+b+"=?");
        captchaMap.put("answer",answer.toString());
        return R.ok(captchaMap);
    }

    /**
     * 登录
     * @param loginReq
     * {
     *     username: 姓名,
     *     password: 密码（未加密）
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping(value = {"/loginAction"})
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Void> loginAction(@RequestBody LoginRequest loginReq, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date=new Date();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginReq.getUsername(), loginReq.getPassword());
            subject.login(token);
            Sysuser user = (Sysuser) subject.getPrincipal();
            String newToken = JWTUtils.sign(user.getUsername(), Const.MD5_PWD_SALT, 3600);
            user.setToken(newToken);
            user.setLastlogin(date);
            sysuserService.updateById(user);
            logger.info("User Token&Date Updated {} ", user.getUsername());
            response.setHeader("x-auth-token", newToken);
            System.out.println("x-auth-token:"+newToken);
            response.setHeader("permission",user.getPermission()+"");
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            logger.error("User {} login fail, Reason:{}", loginReq.getUsername(), e.getMessage());
            System.out.println(JSON.toJSON(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * APP登录
     * @param loginReq
     * {
     *     username: 姓名,
     *     password: 密码（未加密）
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping(value = {"/appLoginAction","/api/users/appLoginAction"})
    @Transactional(rollbackFor = Exception.class)
    public R appLoginAction(@RequestBody LoginRequest loginReq, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date date=new Date();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginReq.getUsername(), loginReq.getPassword());
            subject.login(token);
            Sysuser user = (Sysuser) subject.getPrincipal();
            String newToken = JWTUtils.sign(user.getUsername(), Const.MD5_PWD_SALT, 3600);
            user.setToken(newToken);
            user.setLastlogin(date);
            sysuserService.updateById(user);
            AppLoginResp appLoginResp = new AppLoginResp();
            appLoginResp.authToken = newToken;
            appLoginResp.userPermission = user.getPermission();
            return R.ok(appLoginResp);
        } catch (AuthenticationException e) {
            return R.failed("用户名或密码错误！");
        } catch (Exception e) {
            return R.failed("登录失败，未知异常");
        }
    }

    /**
     * 注销
     * @param response
     * @return ResponseEntity
     */
    @GetMapping(value = "/logout")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
            Sysuser user = (Sysuser)subject.getPrincipals().getPrimaryPrincipal();
            user.setToken("");
            logger.info("User {} Token Removed ", user.getUsername());
            sysuserService.updateById(user);
        }
        SecurityUtils.getSubject().logout();
        response.setHeader("x-auth-token", "");
        return ResponseEntity.ok().build();
    }

    /**
     * 查询个人信息
     * return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping(value = "/api/user/showUserInfo")
    public R showUserInfo(){
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        return R.ok(sysuser);
    }

    /**
     * 修改个人信息
     * @param changeInfoReq
     * {
     *     email: 电子邮箱,
     *     mobile: 手机号
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping(value = "/api/user/changeInfoAction")
    @Transactional(rollbackFor = Exception.class)
    public R changeInfoAction(@RequestBody ChangeInfoReq changeInfoReq){
        Subject subject = SecurityUtils.getSubject();
        Sysuser currentsysuser = (Sysuser) subject.getPrincipal();
        if (!"".equals(changeInfoReq.getEmail())&&changeInfoReq.getEmail()!=null){
            currentsysuser.setEmail(changeInfoReq.getEmail());
        }
        currentsysuser.setMobile(changeInfoReq.getMobile());
        sysuserService.updateById(currentsysuser);
        return R.ok("修改成功！");
    }

    /**
     * 修改密码
     * @param changePwdReq
     * {
     *     newPassword: 新密码,
     *     oldPassword: 原密码
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping(value = "/api/user/changePasswordAction")
    @Transactional(rollbackFor = Exception.class)
    public R changePasswordAction(@RequestBody ChangePwdReq changePwdReq){
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (!new PasswordUtils(changePwdReq.getOldPassword()).getEncryptedPwd().equals(sysuser.getPassword())){
            return R.failed("原密码不正确！");
        }
        sysuser.setPassword(new PasswordUtils(changePwdReq.getNewPassword()).getEncryptedPwd());
        sysuserService.updateById(sysuser);
        return R.ok("修改密码成功！");
    }



}

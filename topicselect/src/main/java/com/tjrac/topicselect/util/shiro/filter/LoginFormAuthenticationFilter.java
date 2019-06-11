/**
 *  Shiro 登录跳转管理
 *  2018/12/17
 * @return
 */

package com.tjrac.topicselect.util.shiro.filter;

import com.tjrac.topicselect.config.Const;
import com.tjrac.topicselect.entity.Sysuser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(LoginFormAuthenticationFilter.class);

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {

        Sysuser user = (Sysuser) SecurityUtils.getSubject().getPrincipal();

        if(user != null) {
            HttpServletRequest req = (HttpServletRequest) request;
//                String clientType = (String) req.getParameter("clienttype");
            logger.debug("[LOGIN]login success(user:" + user.getUsername() + ")");
            // 请求方为Admin，执行原方法
            if(Const.UserPermissionAdmin.equals(user.getPermission()+"")) {
                return super.onLoginSuccess(token, subject, request, response);
                // 其他，根据类型跳转指定页面
            } else if(Const.UserPermissionTeacher.equals(user.getPermission()+"")) {
                // 清除登录前请求路径
                WebUtils.getAndClearSavedRequest(request);
                String fallbackUrl = null;
                // 跳转教师界面
                fallbackUrl = "/teacher";
                WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
                // 其他，根据类型跳转指定页面
            } else if(Const.UserPermissionStudent.equals(user.getPermission()+"")) {
                // 清除登录前请求路径
                WebUtils.getAndClearSavedRequest(request);
                String fallbackUrl = null;
                // 跳转学生界面
                fallbackUrl = "/student";
                WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
            }
        }
        return false;
    }
}
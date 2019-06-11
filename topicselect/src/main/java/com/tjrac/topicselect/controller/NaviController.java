package com.tjrac.topicselect.controller;

import com.tjrac.topicselect.entity.Sysuser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author myd
 */
@Controller
public class NaviController {
    //============公用区域============
    /**
     * 主页
     * @return java.lang.String
     */
    @RequestMapping(value = {"/index"})
    public String indexNavi(){
        return "index";
    }

    /**
     * 登录
     * @return java.lang.String
     */
    @RequestMapping("/login")
    public String loginNavi(){
        return "login";
    }

    /**
     * 登录分发
     * @return java.lang.String
     */
    @RequestMapping(value = {"/","/dispatch"})
    public String loginDispatch(){
        try {
            Sysuser user = (Sysuser) SecurityUtils.getSubject().getPrincipal();
            if (user != null){
                System.out.println(user.getPermission());
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
                System.out.println(token);
                switch (user.getPermission()){
                    case 0: return "admin";
                    case 1: return "teacher";
                    case 2: return "student";
                    default: return "401";
                }
            }return "login";
        }catch (Exception e) {
            e.printStackTrace();
            return "login";
        }
    }

    /**
     * 修改密码
     * @return java.lang.String
     */
    @RequestMapping("/changepassword")
    public String changePasswordNavi(){
        return "changepassword";
    }

    /**
     * 修改个人信息
     * @return java.lang.String
     */
    @RequestMapping("/changeinfo")
    public String changeInfoNavi(){
        return "changeinfo";
    }

    //============管理员功能导航============

    //测试
    @RequestMapping("/admin/testupload")
    public String testuploadNavi(){
        return "admin/testupload";
    }

    /**
     * 管理员主页
     * @return java.lang.String
     */
    @RequestMapping(value = {"/admin","/admin/index"})
    public String adminIndexNavi(){
        return "admin/adminindex";
    }

    /**
     * 查询系统开放时间
     * @return java.lang.String
     */
    @RequestMapping("/admin/timeshow")
    public String timeShowNavi(){
        return "admin/timeshow";
    }

    /**
     * 修改系统开放时间
     * @return java.lang.String
     */
    @RequestMapping("/admin/timepick")
    public String timePickNavi(){
        return "admin/timepick";
    }

    /**
     * 查询待审核的课题
     * @return java.lang.String
     */
    @RequestMapping("/admin/toaudittopics")
    public String toauditNavi(){
        return "admin/toaudittopics";
    }

    /**
     * 查询已审核的课题
     * @return java.lang.String
     */
    @RequestMapping("/admin/auditedtopics")
    public String auditedNavi(){
        return "admin/auditedtopics";
    }

    /**
     * 上传用户信息Excel文件
     * @return java.lang.String
     */
    @RequestMapping("/admin/adminuploaduser")
    public String adminUploadUserNavi(){
        return "admin/adminuploaduser";
    }

    /**
     * 手动添加用户
     * @return java.lang.String
     */
    @RequestMapping("/admin/adminadduser")
    public String adminAddUserNavi(){
        return "admin/adminadduser";
    }

    /**
     * 管理用户
     * @return java.lang.String
     */
    @RequestMapping("/admin/manageusers")
    public String manageUsersNavi(){
        return "admin/manageusers";
    }

    /**
     * 选题结果查询
     * @return java.lang.String
     */
    @RequestMapping("/admin/selecttopicresults")
    public String selectTopicResults(){
        return "admin/selecttopicresults";
    }

    /**
     * 未被选择的学生查询
     * @return java.lang.String
     */
    @RequestMapping("/admin/unchosestudents")
    public String unChoseStudents(){
        return "admin/unchosestudents";
    }

    /**
     * 未被选择的课题查询
     * @return java.lang.String
     */
    @RequestMapping("/admin/unchosetopics")
    public String unChoseTopics(){
        return "admin/unchosetopics";
    }

    /**
     * 导出选题结果
     * @return java.lang.String
     */
    @RequestMapping("/admin/exporttopicresult")
    public String exportTopicResult(){
        return "admin/exporttopicresult";
    }

    //============教师功能导航============

    /**
     * 教师主页
     * @return java.lang.String
     */
    @RequestMapping(value = {"/teacher","/teacher/index"})
    public String teacherIndexNavi(){
        return "teacher/teacherindex";
    }

    /**
     * 我的课题
     * @return java.lang.String
     */
    @RequestMapping("/teacher/showTopic")
    public String showTopicNavi(){
        return "teacher/showtopic";
    }

    /**
     * 申请课题
     * @return java.lang.String
     */
    @RequestMapping("/teacher/applyTopic")
    public String applyTopicNavi(){
        return "teacher/applytopic";
    }

    /**
     * 一轮选题情况一览
     * @return java.lang.String
     */
    @RequestMapping("/teacher/firstroundoverview")
    public String firstRoundOverviewNavi(){
        return "teacher/firstroundoverview";
    }

    /**
     * 查询已选择课题的学生
     * @return java.lang.String
     */
    @RequestMapping("/teacher/selectedstudents")
    public String selectedStudentsNavi(){
        return "teacher/selectedstudents";
    }

    //============学生功能导航============

    /**
     * 学生主页
     * @return java.lang.String
     */
    @RequestMapping(value = {"/student","/student/index"})
    public String studentIndexNavi(){
        return "student/studentindex";
    }

    /**
     * 查询已审核的课题（复用admin）
     * @return java.lang.String
     */
    @RequestMapping("/student/auditedtopics")
    public String stuauditedNavi(){
        return "student/auditedtopics";
    }

    /**
     * 查询已选择的课题
     * @return java.lang.String
     */
    @RequestMapping("/student/selectedtopics")
    public String selectedtopicsNavi(){
        return "student/selectedtopics";
    }

    /**
     * 最终确认
     * @return java.lang.String
     */
    @RequestMapping("/student/finalcheck")
    public String finalcheckNavi(){
        return "student/finalcheck";
    }

}

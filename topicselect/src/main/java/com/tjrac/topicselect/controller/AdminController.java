package com.tjrac.topicselect.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjrac.topicselect.bean.CreateUserFromExcel;
import com.tjrac.topicselect.bean.ExportTopicResult;
import com.tjrac.topicselect.bean.request.*;
import com.tjrac.topicselect.bean.response.*;
import com.tjrac.topicselect.config.Const;
import com.tjrac.topicselect.entity.Sysopentime;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.entity.Topics;
import com.tjrac.topicselect.entity.Topicsel;
import com.tjrac.topicselect.mapper.TopicSelMapper;
import com.tjrac.topicselect.mapper.TopicsMapper;
import com.tjrac.topicselect.service.SysopentimeService;
import com.tjrac.topicselect.service.SysuserService;
import com.tjrac.topicselect.service.TopicSelService;
import com.tjrac.topicselect.service.TopicsService;
import com.tjrac.topicselect.util.ExportTopicResultUtil;
import com.tjrac.topicselect.util.PasswordUtils;
import com.tjrac.topicselect.util.PoiReadUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author myd
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    SysopentimeService sysopentimeService;
    @Autowired
    TopicsService topicsService;
    @Autowired
    SysuserService sysuserService;
    @Autowired
    TopicSelService topicSelService;
    @Autowired
    TopicSelMapper topicSelMapper;
    @Autowired
    TopicsMapper topicsMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * 系统开放时间查询
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/getSysTime")
    public R getSysTimeAction() {
        Sysopentime sysopentime = sysopentimeService.getById(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TimePickReq timePickReq = new TimePickReq();
        timePickReq.setUploadstart(sdf.format(sysopentime.getUploadstart()));
        timePickReq.setUploadend(sdf.format(sysopentime.getUploadend()));
        timePickReq.setChoosestart(sdf.format(sysopentime.getChoosestart()));
        timePickReq.setChooseend(sdf.format(sysopentime.getChooseend()));
        return R.ok(timePickReq);
    }

    /**
     * 系统开放时间修改
     *
     * @param timePickReq {
     *                    uploadstart: 上传、审核课题开始时间,
     *                    uploadend: 上传、审核课题结束时间,
     *                    choosestart: 选题开始时间,
     *                    chooseend: 选题结束时间,
     *                    }
     * @return com.baomidou.mybatisplus.extension.api.R
     * @throws ParseException
     */
    @PostMapping("/postTime")
    @Transactional(rollbackFor = Exception.class)
    public R postTimeAction(@RequestBody TimePickReq timePickReq) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Sysopentime sysopentime = new Sysopentime();
        sysopentime.setId(1);
        sysopentime.setUploadstart(sdf.parse(timePickReq.getUploadstart()));
        sysopentime.setUploadend(sdf.parse(timePickReq.getUploadend()));
        sysopentime.setChoosestart(sdf.parse(timePickReq.getChoosestart()));
        sysopentime.setChooseend(sdf.parse(timePickReq.getChooseend()));
        try {
            sysopentimeService.saveOrUpdate(sysopentime);
            return R.ok("更新系统开放时间成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("更新失败，内部错误");
        }
    }

    /**
     * 审核课题 查询待审核的课题
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/listToAuditTopics")
    public R listToAuditingTopics() {
        QueryWrapper<Topics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        List<TopicsResp> topicsRespList = new ArrayList<>();
        List<Topics> topicsList = topicsService.list(queryWrapper);
        for (int i = 0; i < topicsList.size(); i++) {
            topicsRespList.add(new TopicsResp());
            BeanUtils.copyProperties(topicsList.get(i), topicsRespList.get(i));
        }
        for (int i = 0; i < topicsRespList.size(); i++) {
            topicsRespList.get(i).setTruename(sysuserService.getById(topicsRespList.get(i).getAuthorid()).getTruename());
        }
        if (topicsRespList.size() == 0) {
            return R.failed("查询结果为空！");
        } else {
            return R.ok(topicsRespList);
        }
    }

    /**
     * 审核课题 查询已审核的课题
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/listAuditedTopics")
    public R listAuditedTopics() {
        QueryWrapper<Topics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<Topics> topicsList = topicsService.list(queryWrapper);
        List<TopicsResp> topicsRespList = new ArrayList<>();
        for (int i = 0; i < topicsList.size(); i++) {
            topicsRespList.add(new TopicsResp());
            BeanUtils.copyProperties(topicsList.get(i), topicsRespList.get(i));
        }
        for (int i = 0; i < topicsRespList.size(); i++) {
            topicsRespList.get(i).setTruename(sysuserService.getById(topicsRespList.get(i).getAuthorid()).getTruename());
        }
        if (topicsRespList.size() == 0) {
            return R.failed("查询结果为空！");
        } else {
            return R.ok(topicsRespList);
        }
    }

    /**
     * 审核课题 审核动作
     *
     * @param topicsReq {
     *                  id: 课题ID
     *                  }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/doAudit")
    @Transactional(rollbackFor = Exception.class)
    public R doAuditAction(@RequestBody TopicsReq topicsReq) {
        Topics topics = new Topics();
        topics.setId(topicsReq.getId());
        topics.setStatus(1);
        try {
            topicsService.updateById(topics);
            return R.ok("审核成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("审核失败！");
        }
    }

    /**
     * 审核课题 打回审核动作
     *
     * @param topicsReq {
     *                  id: 课题ID
     *                  }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/redoAudit")
    @Transactional(rollbackFor = Exception.class)
    public R rdoAuditAction(@RequestBody TopicsReq topicsReq) {
        Topics topics = new Topics();
        topics.setId(topicsReq.getId());
        /**
         * 如果topicsel表有status=0或1的课题记录那么该课题禁止被打回
         */
        QueryWrapper<Topicsel> topicselQueryWrapper = new QueryWrapper<>();
        topicselQueryWrapper.eq("topicid", topicsReq.getId())
                .eq("status", 0)
                .or()
                .eq("status", 1);
        Topicsel topicselchecker = topicSelService.getOne(topicselQueryWrapper);
        if (topicselchecker != null) {
            return R.failed("禁止删除！当前题目已经被学生选中或已经确认完毕！请将学生退选后重试。");
        }
        topics.setStatus(0);
        try {
            topicsService.updateById(topics);
            return R.ok("打回审核成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("打回审核失败！");
        }
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadUserExcel")
    public void uploadUserExcel(MultipartFile file, HttpServletResponse response) throws IOException {
        try {
            String filename = file.getOriginalFilename();
            File serverfile = new File(Const.UPLOADED_FOLDER + UUID.randomUUID() + filename);
            file.transferTo(serverfile);
            PoiReadUtils poiReadUtils = new PoiReadUtils();
            CreateUserFromExcel[] createUserFromExcels = poiReadUtils.readExcel(serverfile);
            StringBuffer sbHtml = new StringBuffer();
            //构建HTML元素，向返回页面输出
            PostExcelResp postExcelResp = new PostExcelResp(JSON.toJSON(createUserFromExcels).toString());
            sbHtml.append(postExcelResp.getPostExcelHtmlResp());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(sbHtml.toString());
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer sbHtml = new StringBuffer();
            PostExcelResp postExcelResp = new PostExcelResp("null");
            sbHtml.append(postExcelResp.getPostExcelHtmlResp());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(sbHtml.toString());
        }
    }

    /**
     * 向数据库写入Excel中的用户信息
     *
     * @param addUsersFromExcelReq
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/addUsersFromExcelAction")
    @Transactional(rollbackFor = Exception.class)
    public R addUsersFromExcelAction(@RequestBody AddUsersFromExcelReq addUsersFromExcelReq) {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (!sysuser.getPassword().equals(new PasswordUtils(addUsersFromExcelReq.getAdminPwd()).getEncryptedPwd())) {
            return R.failed("密码不正确！");
        }
        if (sysuser.getPermission() != 0) {
            return R.failed("你的权限不是管理员，你想做什么？已经记录到数据库！");
        }
        CreateUserFromExcel[] userArr = addUsersFromExcelReq.getExcelJson();
        //用户名与密码相同
        for (int i = 0; i < userArr.length; i++) {
            Sysuser tempuser = new Sysuser();
            tempuser.setUsername(userArr[i].getUsername());
            tempuser.setTruename(userArr[i].getTruename());
            tempuser.setPermission(new Integer(userArr[i].getPermission()));
            tempuser.setPassword(new PasswordUtils(userArr[i].getUsername()).getEncryptedPwd());
            tempuser.setCreateTime(new Date());
            tempuser.setStatus(0);
            sysuserService.saveOrUpdate(tempuser);
        }
        return R.ok("添加用户成功！");
    }

    /**
     * 手动添加用户
     *
     * @param manualAddUserReq
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/manualAddUserAction")
    @Transactional(rollbackFor = Exception.class)
    public R manualAddUserAction(@RequestBody ManualAddUserReq manualAddUserReq) {
        Sysuser sysuser = new Sysuser();
        if (manualAddUserReq.getUsername() != null & !("").equals(manualAddUserReq.getUsername())) {
            sysuser.setUsername(manualAddUserReq.getUsername());
        } else {
            return R.failed("请输入用户名！");
        }
        if (manualAddUserReq.getTruename() != null & !("").equals(manualAddUserReq.getTruename())) {
            sysuser.setTruename(manualAddUserReq.getTruename());
        } else {
            return R.failed("请输入真实姓名！");
        }
        if (manualAddUserReq.getPassword() != null & !("").equals(manualAddUserReq.getPassword())) {
            sysuser.setPassword(new PasswordUtils(manualAddUserReq.getPassword()).getEncryptedPwd());
        } else {
            return R.failed("请输入密码！");
        }
        if (manualAddUserReq.getMobile() != null & !("").equals(manualAddUserReq.getMobile())) {
            sysuser.setMobile(manualAddUserReq.getMobile());
        } else {
            return R.failed("请输入手机号！");
        }
        if (manualAddUserReq.getEmail() != null & !("").equals(manualAddUserReq.getEmail())) {
            sysuser.setEmail(manualAddUserReq.getEmail());
        }
        if (manualAddUserReq.getPermission() != null & !("").equals(manualAddUserReq.getPermission())) {
            sysuser.setPermission(new Integer(manualAddUserReq.getPermission()));
        }
        sysuser.setCreateTime(new Date());
        sysuserService.save(sysuser);
        return R.ok("添加成功！");
    }

    /**
     * 查询所有用户列表（除了当前用户）
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/selectAllUsers")
    public R selectAllUsers() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        QueryWrapper<Sysuser> sysuserQueryWrapper = new QueryWrapper<>();
        sysuserQueryWrapper.ne("id", sysuser.getId());
        List<Sysuser> sysuserList = sysuserService.list(sysuserQueryWrapper);
        return R.ok(sysuserList);
    }

    /**
     * 删除指定用户
     *
     * @param deleteUserReq {
     *                      id: 用户ID
     *                      }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/deleteUser")
    @Transactional(rollbackFor = Exception.class)
    public R deleteUser(@RequestBody DeleteUserReq deleteUserReq) {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 0) {
            return R.failed("你的权限不是管理员，你想做什么？已经记录到数据库！");
        }
        sysuserService.removeById(deleteUserReq.getId());
        return R.ok("删除成功！");
    }

    /**
     * 选题结果 查询 公用方法
     *
     * @return java.util.ArrayList
     */
    public ArrayList<SelectedStudentShowResp> selectTopicResultsMethod() {
        //查询被选中的学生
        QueryWrapper<Topicsel> topicselQueryWrapper = new QueryWrapper<>();
        topicselQueryWrapper.eq("status", 1);
        List<Topicsel> topicselList = topicSelService.list(topicselQueryWrapper);
        ArrayList<SelectedStudentShowResp> resultList = new ArrayList<>();
        for (int i = 0; i < topicselList.size(); i++) {
            SelectedStudentShowResp selectedStudentShowResp = new SelectedStudentShowResp();
            Sysuser sysuser = sysuserService.getById(topicselList.get(i).getSelectuserid());
            Topics topics = topicsService.getById(topicselList.get(i).getTopicid());
            selectedStudentShowResp.setTeachername(topicSelMapper.selectUserByTopicId(topicselList.get(i).getTopicid()).getTruename());
            selectedStudentShowResp.setStutruename(sysuser.getTruename());
            selectedStudentShowResp.setStuusername(sysuser.getUsername());
            selectedStudentShowResp.setCreatetime(topicselList.get(i).getCreatetime());
            selectedStudentShowResp.setTopictitle(topics.getTopictitle());
            selectedStudentShowResp.setTopicdetail(topics.getTopicdetail());
            selectedStudentShowResp.setTopicid(topics.getId());
            selectedStudentShowResp.setStuid(sysuser.getId());
            resultList.add(selectedStudentShowResp);
        }
        return resultList;
    }

    /**
     * 选题结果 查询
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/selectTopicResults")
    public R selectTopicResults() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuserchk = (Sysuser) subject.getPrincipal();
        if (sysuserchk.getPermission() != 0) {
            return R.failed("你的权限不是管理员，你想做什么？已经记录到数据库！");
        }
        ArrayList<SelectedStudentShowResp> resultList = selectTopicResultsMethod();
        return R.ok(resultList);
    }

    /**
     * 选题结果 查询未被选中的学生
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/selectUnChoseStudents")
    public R selectUnChoseStudents() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuserchk = (Sysuser) subject.getPrincipal();
        if (sysuserchk.getPermission() != 0) {
            return R.failed("你的权限不是管理员，你想做什么？已经记录到数据库！");
        }
        List<Topicsel> ue1List = topicSelMapper.selectUserIdNe1();
        List<Sysuser> resultList = new ArrayList<>();
        for (int i = 0; i < ue1List.size(); i++) {
            QueryWrapper<Topicsel> statusQueryWrapper = new QueryWrapper<>();
            statusQueryWrapper.eq("status", 1)
                    .eq("selectuserid", ue1List.get(i).getSelectuserid());
            List<Topicsel> resulteq1 = topicSelService.list(statusQueryWrapper);
            if (resulteq1.size() == 0) {
                resultList.add(topicSelMapper.unChoseSysuser(ue1List.get(i).getSelectuserid()));
            }
        }
        return R.ok(resultList);
    }

    /**
     * 选题结果 查询未被选中的课题
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/selectUnChoseTopics")
    public R selectUnChoseTopics() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuserchk = (Sysuser) subject.getPrincipal();
        if (sysuserchk.getPermission() != 0) {
            return R.failed("你的权限不是管理员，你想做什么？已经记录到数据库！");
        }
        List<UnChoseTopicsResp> unChoseTopicsResps = topicsMapper.selectUnChoseTopics();
        return R.ok(unChoseTopicsResps);
    }

    /**
     * 数据导出 导出选题结果
     */
    @GetMapping("/exporttopicresult")
    public void exportTopicResultMethod(HttpServletResponse response) throws IOException {
        ArrayList<SelectedStudentShowResp> resultList = selectTopicResultsMethod();
        List<ExportTopicResult> exportTopicResultList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            exportTopicResultList.add(new ExportTopicResult());
            BeanUtils.copyProperties(resultList.get(i), exportTopicResultList.get(i));
        }
        Workbook wb = ExportTopicResultUtil.outWorkBook(exportTopicResultList);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=results.xls");
        wb.write(os);
        os.flush();
        os.close();
    }



}

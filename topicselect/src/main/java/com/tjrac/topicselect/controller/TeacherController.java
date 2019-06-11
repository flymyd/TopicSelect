package com.tjrac.topicselect.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjrac.topicselect.bean.request.ChooseStuReq;
import com.tjrac.topicselect.bean.request.RejectStudentReq;
import com.tjrac.topicselect.bean.response.SelectedStudentShowResp;
import com.tjrac.topicselect.bean.request.TopicsReq;
import com.tjrac.topicselect.bean.response.GetTopicSelSelectedResp;
import com.tjrac.topicselect.bean.response.GetTopicSelSelectedResultResp;
import com.tjrac.topicselect.entity.Topicsel;
import com.tjrac.topicselect.entity.Sysopentime;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.entity.Topics;
import com.tjrac.topicselect.mapper.TopicSelMapper;
import com.tjrac.topicselect.service.TopicSelService;
import com.tjrac.topicselect.service.SysopentimeService;
import com.tjrac.topicselect.service.SysuserService;
import com.tjrac.topicselect.service.TopicsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author myd
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TopicsService topicsService;
    @Autowired
    TopicSelService topicSelService;
    @Autowired
    TopicSelMapper topicSelMapper;
    @Autowired
    SysuserService sysuserService;
    @Autowired
    SysopentimeService sysopentimeService;

    public Boolean currentTimePeriod(int flag) {
        Sysopentime sysopentime = sysopentimeService.getById(1);
        Date date = new Date();
        //0上传课题 1选题
        if (flag == 0) {
            if (sysopentime.getUploadstart().getTime() < date.getTime() && sysopentime.getUploadend().getTime() > date.getTime()) {
                return true;
            } else {
                return false;
            }
        } else if (flag == 1) {
            if (sysopentime.getChoosestart().getTime() < date.getTime() && sysopentime.getChooseend().getTime() > date.getTime()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 查询名下所有课题
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/getMyTopic")
    public R getMyTopic() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        QueryWrapper<Topics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authorid", sysuser.getId());
        List<Topics> topicsList = topicsService.list(queryWrapper);
        if (topicsList == null) {
            return R.failed("查询结果为空！");
        } else {
            return R.ok(topicsList);
        }
    }

    /**
     * 申请课题
     *
     * @param topicsReq
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/applyTopicAction")
    @Transactional(rollbackFor = Exception.class)
    public R applyForProfessor(@RequestBody TopicsReq topicsReq) {
        if (!currentTimePeriod(0)) {
            return R.failed("当前不在上传课题期间！");
        }
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        Topics topics = new Topics();
        topics.setAuthorid(sysuser.getId());
        topics.setTopictitle(topicsReq.getTopictitle());
        topics.setSumnum(topicsReq.getSumnum());
        topics.setAvailablenum(topicsReq.getAvailablenum());
        /**
         * level1: 0简单，1一般，2困难
         */
        if (topicsReq.getLevel1() == null || topicsReq.getLevel1().equals("")) {
            topics.setLevel1("简单");
        } else {
            switch (new Integer(topicsReq.getLevel1())) {
                case 0:
                    topics.setLevel1("简单");
                    break;
                case 1:
                    topics.setLevel1("一般");
                    break;
                case 2:
                    topics.setLevel1("困难");
                    break;
                default:
                    topics.setLevel1("简单");
                    break;
            }
        }
        if (topicsReq.getAddress() != null && !topicsReq.getAddress().equals("")) {
            topics.setAddress(topicsReq.getAddress());
        }
        if (topicsReq.getTechnology() != null && !topicsReq.getTechnology().equals("")) {
            topics.setTechnology(topicsReq.getTechnology());
        }
        topics.setTopicdetail(topicsReq.getTopicdetail());
        topics.setStatus(0);
        try {
            topicsService.save(topics);
            return R.ok("申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("申请失败，内部错误");
        }
    }

    /**
     * 删除指定课题
     *
     * @param topicsReq {
     *                  id: 课题ID
     *                  }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/deleteMyTopic")
    @Transactional(rollbackFor = Exception.class)
    public R deleteMyTopicAction(@RequestBody TopicsReq topicsReq) {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        /**
         * 如果topicsel表有status=0或1的课题记录那么该课题禁止被删除
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
        try {
            topicsService.removeById(topicsReq.getId());
            return R.ok("取消课题成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("取消课题失败，未知异常");
        }
    }

    /**
     * 查看课题被选状况
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/getFirstRoundSelected")
//    需要优化
    public R getFirstRoundSelected() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        QueryWrapper<Topics> topicsQueryWrapper = new QueryWrapper<>();
        //获取当前教师名下的课题
        topicsQueryWrapper.eq("authorid", sysuser.getId());
        List<Topics> topicsList = topicsService.list(topicsQueryWrapper);
        //遍历课题ID，根据topicid检索topicsel表
        ArrayList<GetTopicSelSelectedResp> topicsSelDetail = new ArrayList<>();
        Set topictitleset = new HashSet();
        for (int i = 0; i < topicsList.size(); i++) {
            QueryWrapper<Topicsel> firstroundQueryWrapper = new QueryWrapper<>();
            firstroundQueryWrapper.eq("topicid", topicsList.get(i).getId())
                    .eq("status", 0);
            try {
                List<Topicsel> topicselList = topicSelService.list(firstroundQueryWrapper);
                for (int j = 0; j < topicselList.size(); j++) {
                    GetTopicSelSelectedResp getTopicSelSelectedResp = new GetTopicSelSelectedResp();
                    Sysuser student = sysuserService.getById(topicselList.get(j).getSelectuserid());
                    Topics topics = topicsService.getById(topicselList.get(j).getTopicid());
                    getTopicSelSelectedResp.setSelecttime(topicselList.get(j).getCreatetime());
                    getTopicSelSelectedResp.setUserid(topicselList.get(j).getSelectuserid());
                    getTopicSelSelectedResp.setTopicid(topicselList.get(j).getTopicid());
                    getTopicSelSelectedResp.setTruename(student.getTruename());
                    getTopicSelSelectedResp.setUsername(student.getUsername());
                    getTopicSelSelectedResp.setTopictitle(topics.getTopictitle());
                    //保存课题标题结果集
                    topictitleset.add(topics.getTopictitle());
                    topicsSelDetail.add(getTopicSelSelectedResp);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return R.failed("未知异常！");
            }
        }
        //获取课题标题结果集
        Object[] topictitleObj = topictitleset.toArray();
        String[] topictitleList = new String[topictitleObj.length];
        for (int i = 0; i < topictitleList.length; i++) {
            topictitleList[i] = (String) topictitleObj[i];
        }
        //构造HashMap，存储最终结果集
        try {
            Hashtable<String, ArrayList> resultTable = new Hashtable<>();
            //遍历标题结果集，作HashTable的Key
            for (int i = 0; i < topictitleList.length; i++) {
                resultTable.put(topictitleList[i], new ArrayList<>());
            }
            //遍历结果集，把同课题的信息添加到Value的List中
            for (int i = 0; i < topicsSelDetail.size(); i++) {
                String topictitle = topicsSelDetail.get(i).getTopictitle();
                ArrayList arrayList = resultTable.get(topictitle);
                arrayList.add(topicsSelDetail.get(i));
                resultTable.put(topictitle, arrayList);
            }
            GetTopicSelSelectedResultResp getTopicSelSelectedResultResp = new GetTopicSelSelectedResultResp();
            getTopicSelSelectedResultResp.setResultTable(resultTable);
            getTopicSelSelectedResultResp.setTitleList(topictitleList);
            return R.ok(getTopicSelSelectedResultResp);
        } catch (Exception e) {
            e.printStackTrace();
            return R.failed("未知异常！");
        }
    }

    /**
     * 选题 选中学生
     *
     * @param chooseStuReq {
     *                     userid: 学生用户id,
     *                     topicid: 课题id
     *                     }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/chooseStudentFirstRound")
    @Transactional(rollbackFor = Exception.class)
    public R chooseStudentFirstRound(@RequestBody ChooseStuReq chooseStuReq) {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        if (!currentTimePeriod(1)) {
            return R.failed("当前不在选题时间段！");
        }
        //如果该学生已被选中课题则退出
        QueryWrapper<Topicsel> checkQueryWrapper = new QueryWrapper<>();
        checkQueryWrapper.eq("status", 1)
                .eq("selectuserid", chooseStuReq.getUserid());
        if (topicSelService.list(checkQueryWrapper).size() != 0) {
            return R.failed("该学生已被选中其它课题！");
        }
        //把指定学生的status设置为1（已选中）
        QueryWrapper<Topicsel> topicselQueryWrapper = new QueryWrapper<>();
        topicselQueryWrapper.eq("selectuserid", chooseStuReq.getUserid())
                .eq("topicid", chooseStuReq.getTopicid())
                .eq("status", 0);
        Topicsel selectStudentTopicSel = new Topicsel();
        selectStudentTopicSel.setStatus(1);
        topicSelService.update(selectStudentTopicSel, topicselQueryWrapper);
        //把其余落选学生的status设置为2（打回）
        QueryWrapper<Topicsel> topicSelOutQueryWrapper = new QueryWrapper<>();
        topicSelOutQueryWrapper.eq("topicid", chooseStuReq.getTopicid())
                .eq("status", 0);
        Topicsel outStudentTopicSel = new Topicsel();
        outStudentTopicSel.setStatus(2);
        topicSelService.update(outStudentTopicSel, topicSelOutQueryWrapper);
        //把该课题的状态改为2（失效）
        Topics topics = new Topics();
        topics.setId(chooseStuReq.getTopicid());
        topics.setStatus(2);
        topicsService.updateById(topics);
        return R.ok("选择成功！");
    }

    /**
     * 已选题学生查询
     *
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/selectedStudentsShow")
    public R selectedStudentsShow() {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuserchk = (Sysuser) subject.getPrincipal();
        if (sysuserchk.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        //查询被选中的学生
        List<Topicsel> topicselList = topicSelMapper.selectedStudentsShowByTeacherId(sysuserchk.getId());
        ArrayList<SelectedStudentShowResp> resultList = new ArrayList<>();
        for (int i = 0; i < topicselList.size(); i++) {
            SelectedStudentShowResp selectedStudentShowResp = new SelectedStudentShowResp();
            Sysuser sysuser = sysuserService.getById(topicselList.get(i).getSelectuserid());
            Topics topics = topicsService.getById(topicselList.get(i).getTopicid());
            selectedStudentShowResp.setStutruename(sysuser.getTruename());
            selectedStudentShowResp.setStuusername(sysuser.getUsername());
            selectedStudentShowResp.setCreatetime(topicselList.get(i).getCreatetime());
            selectedStudentShowResp.setTopictitle(topics.getTopictitle());
            selectedStudentShowResp.setTopicdetail(topics.getTopicdetail());
            selectedStudentShowResp.setTopicid(topics.getId());
            selectedStudentShowResp.setStuid(sysuser.getId());
            resultList.add(selectedStudentShowResp);
        }
        return R.ok(resultList);
    }

    /**
     * 将已被选中的学生打回重选
     *
     * @param rejectStudentReq {
     *                         studentId: 学生用户ID,
     *                         topicId: 课题ID
     *                         }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/rejectStudentFirstRound")
    @Transactional(rollbackFor = Exception.class)
    public R rejectStudentFirstRound(@RequestBody RejectStudentReq rejectStudentReq) {
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        if (sysuser.getPermission() != 1) {
            return R.failed("你的权限不是教师，你想做什么？已经记录到数据库！");
        }
        //把课题被选数-1，再把状态改回1（已选）
        Topics topics = topicsService.getById(rejectStudentReq.getTopicId());
        topics.setSelectednum(topics.getSelectednum() - 1);
        topics.setStatus(1);
        topicsService.updateById(topics);
        //在选题记录表中删除学生ID&课题ID&status==1的记录，并把其它课题ID&status==2的记录置为status=0
        QueryWrapper<Topicsel> topicselQueryWrapper = new QueryWrapper<>();
        topicselQueryWrapper.eq("selectuserid", rejectStudentReq.getStudentId())
                .eq("topicid", rejectStudentReq.getTopicId())
                .eq("status", 1);
        topicSelService.remove(topicselQueryWrapper);
        QueryWrapper<Topicsel> topicselQueryWrapper1 = new QueryWrapper<>();
        topicselQueryWrapper1.eq("topicid", rejectStudentReq.getTopicId())
                .eq("status", 2);
        Topicsel topicsel = new Topicsel();
        topicsel.setStatus(0);
        topicSelService.update(topicsel, topicselQueryWrapper1);
        return R.ok("打回成功！");
    }

}

package com.tjrac.topicselect.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjrac.topicselect.bean.request.ApplyTopicReq;
import com.tjrac.topicselect.bean.response.StuFinalCheckResp;
import com.tjrac.topicselect.bean.response.TopicsResp;
import com.tjrac.topicselect.entity.Sysopentime;
import com.tjrac.topicselect.entity.Topicsel;
import com.tjrac.topicselect.entity.Sysuser;
import com.tjrac.topicselect.entity.Topics;
import com.tjrac.topicselect.service.TopicSelService;
import com.tjrac.topicselect.service.SysopentimeService;
import com.tjrac.topicselect.service.SysuserService;
import com.tjrac.topicselect.service.TopicsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author myd
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    SysopentimeService sysopentimeService;
    @Autowired
    TopicsService topicsService;
    @Autowired
    SysuserService sysuserService;
    @Autowired
    TopicSelService topicSelService;

    public Boolean currentSelectTime(){
        Sysopentime sysopentime =sysopentimeService.getById(1);
        Date date=new Date();
        if (sysopentime.getChoosestart().getTime()<date.getTime() && sysopentime.getChooseend().getTime()>date.getTime()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询待选的课题
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/listAuditedTopics")
    public R listAuditedTopics() {
        if (!currentSelectTime()){
            return R.failed("当前不在选择课题期间！");
        }
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
     * 查询学生本人已选的课题
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/listChoosedTopics")
    public R listChoosedTopics() {
        if (!currentSelectTime()){
            return R.failed("当前不在选择课题期间！");
        }
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        try {
            List<Topicsel> topicsel = topicSelService.list(new QueryWrapper<Topicsel>().eq("selectuserid", sysuser.getId()));
            List<TopicsResp> topicsRespList = new ArrayList<>();
            for (int i = 0; i < topicsel.size(); i++) {
                Topics topics = topicsService.getById(topicsel.get(i).getTopicid());
                TopicsResp topicsResp = new TopicsResp();
                BeanUtils.copyProperties(topics, topicsResp);
                topicsResp.setTruename(sysuserService.getById(topicsResp.getAuthorid()).getTruename());
                topicsRespList.add(topicsResp);
            }
            return R.ok(topicsRespList);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return R.failed("没有已选课题！");
        }
    }

    /**
     * 选题动作
     *
     * @param applyTopicReq
     * {
     *     topicId: 课题ID
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/stuApplyTopicAction")
    @Transactional(rollbackFor = Exception.class)
    public R stuApplyTopicAction(@RequestBody ApplyTopicReq applyTopicReq) {
        if (!currentSelectTime()){
            return R.failed("当前不在选择课题期间！");
        }
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Topics topics = topicsService.getById(applyTopicReq.getTopicId());
        List<Topicsel> confirmStu = topicSelService.list(new QueryWrapper<Topicsel>()
                .eq("selectuserid", sysuser.getId())
                .eq("status",1));
        if (confirmStu.size()>0){
            return R.failed("你已经被导师选中，无法继续选题！");
        }
        List<Topicsel> studentsList = topicSelService.list(new QueryWrapper<Topicsel>()
                .eq("selectuserid", sysuser.getId())
                .eq("status",0));
        if (studentsList.size() > 2) {
            return R.failed("你已完成选题操作，无法继续选题！");
        }
        Topicsel topicsel = new Topicsel();
        topicsel.setSelectuserid(sysuser.getId());
        topicsel.setTopicid(applyTopicReq.getTopicId());
        topicsel.setStatus(0);
        topicsel.setCreatetime(sdf.format(new Date()));
        topics.setSelectednum(topics.getSelectednum() + 1);
        try {
            List<Topicsel> stuandtopic = topicSelService.list(new QueryWrapper<Topicsel>().eq("selectuserid", sysuser.getId()).eq("topicid", applyTopicReq.getTopicId()));
            if (stuandtopic.size() > 0) {
                return R.failed("你已选过此课题！");
            }
        } catch (Exception e) {

        }
        topicsService.updateById(topics);
        topicSelService.save(topicsel);
        return R.ok("选择课题成功！");
    }

    /**
     * 最终确认查询
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    @PostMapping("/stuFinalCheck")
    public R stuFinalCheck(){
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        try {
            QueryWrapper<Topicsel> topicselQueryWrapper=new QueryWrapper<>();
            topicselQueryWrapper.eq("selectuserid",sysuser.getId())
                    .eq("status",1);
            Topicsel topicsel = topicSelService.getOne(topicselQueryWrapper);
            if (topicsel !=null){
                Topics topics=topicsService.getById(topicsel.getTopicid());
                StuFinalCheckResp stuFinalCheckResp=new StuFinalCheckResp();
                stuFinalCheckResp.setLevel1(topics.getLevel1());
                stuFinalCheckResp.setTeachername(sysuserService.getById(topics.getAuthorid()).getTruename());
                stuFinalCheckResp.setTechnology(topics.getTechnology());
                stuFinalCheckResp.setTopicaddress(topics.getAddress());
                stuFinalCheckResp.setTopicdetail(topics.getTopicdetail());
                stuFinalCheckResp.setTopictitle(topics.getTopictitle());
                return R.ok(stuFinalCheckResp);
            }else {
                return R.failed("查询失败！你目前没有被导师选中！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.failed("查询失败！");
        }
    }

    /**
     * 退选动作（为安全起见暂时关闭）
     *
     * @param applyTopicReq
     * {
     *     topicId: 课题ID
     * }
     * @return com.baomidou.mybatisplus.extension.api.R
     */
    /*
    @PostMapping("/stuReturnTopicAction")
    @Transactional(rollbackFor = Exception.class)
    public R stuReturnTopicAction(@RequestBody ApplyTopicReq applyTopicReq) {
        if (!currentSelectTime()){
            return R.failed("当前不在选择课题期间！");
        }
        Subject subject = SecurityUtils.getSubject();
        Sysuser sysuser = (Sysuser) subject.getPrincipal();
        try {
            List<Topicsel> studentsList = topicSelService.list(new QueryWrapper<Topicsel>().eq("selectuserid", sysuser.getId()));
            if (studentsList.size() == 0 || studentsList == null) {
                return R.failed("没有选题记录！你无法进行退选！");
            }
        } catch (NullPointerException e) {
            return R.failed("没有选题记录！你无法进行退选！");
        }
        Topics topics = topicsService.getById(applyTopicReq.getTopicId());
        topics.setSelectednum(topics.getSelectednum() - 1);
        topicsService.updateById(topics);
        QueryWrapper<Topicsel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("selectuserid", sysuser.getId())
                .eq("topicid", applyTopicReq.getTopicId());
        topicSelService.remove(queryWrapper);
        return R.ok("退选成功！");
    }
    */
}

package com.gnol.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.plugins.core.context.IdService;
import com.gnol.plugins.tools.json.SFJsonUtil;
import com.gnol.springboot.client.daos.sys.ExtSysScheduleTriggerDao;
import com.gnol.springboot.client.services.sys.SysScheduleTriggerService;
import com.gnol.springboot.common.daos.sys.BaseSysScheduleTriggerDao;
import com.gnol.springboot.common.dos.sys.SysScheduleTrigger;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysScheduleTriggerServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:36:23
 * @Description: gnol系统_任务调度 Service 实现
 */
@Service(value = "sysScheduleTriggerServiceImpl")
public class SysScheduleTriggerServiceImpl extends
        AbstractBaseService<BaseSysScheduleTriggerDao, SysScheduleTrigger, Long> implements SysScheduleTriggerService {
    /**
     * gnol系统_任务调度扩展 Dao
     */
    @Resource(name = "extSysScheduleTriggerDao")
    private ExtSysScheduleTriggerDao sysScheduleTriggerDao;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysScheduleTriggerDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertScheduleTrigger(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }
        String jobGroup = pd.getString("jobGroup");
        if (StringUtil.isBlank(jobGroup)) {
            return PageResult.error("jobGroup 不能为空！");
        }
        String jobName = pd.getString("jobName");
        if (StringUtil.isBlank(jobName)) {
            return PageResult.error("jobName 不能为空！");
        }
        String cron = pd.getString("cron");
        if (StringUtil.isBlank(jobGroup)) {
            return PageResult.error("cron 不能为空！");
        }
        String params = pd.getString("params");
        if (StringUtil.isNotBlank(params)) {
            if (!SFJsonUtil.isJson(params)) {
                return PageResult.error("params 不是 json 格式！");
            }
        } else {
            params = null;
        }
        SysScheduleTrigger sysScheduleTrigger = new SysScheduleTrigger();
        sysScheduleTrigger.setScheduleTriggerId(idService.getLong(SysScheduleTrigger.M.TABLE_NAME));
        sysScheduleTrigger.setJobIp(pd.getString("jobIp"));
        sysScheduleTrigger.setJobGroup(jobGroup);
        sysScheduleTrigger.setJobName(jobName);
        sysScheduleTrigger.setCron(cron);
        sysScheduleTrigger.setParams(params);
        sysScheduleTrigger.setRemark(pd.getString("remark"));
        sysScheduleTrigger.setAddUser(addUser);
        sysScheduleTrigger.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(sysScheduleTrigger);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateScheduleTrigger(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long scheduleTriggerId = pd.getLong("scheduleTriggerId");
        if (scheduleTriggerId == null || scheduleTriggerId.compareTo(0L) != 1) {
            return PageResult.error("gnol系统_任务调度编号不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        String jobGroup = pd.getString("jobGroup");
        if (StringUtil.isBlank(jobGroup)) {
            return PageResult.error("jobGroup 不能为空！");
        }
        String jobName = pd.getString("jobName");
        if (StringUtil.isBlank(jobName)) {
            return PageResult.error("jobName 不能为空！");
        }
        String cron = pd.getString("cron");
        if (StringUtil.isBlank(jobGroup)) {
            return PageResult.error("cron 不能为空！");
        }
        String params = pd.getString("params");
        if (StringUtil.isNotBlank(params)) {
            if (!SFJsonUtil.isJson(params)) {
                return PageResult.error("params 不是 json 格式！");
            }
        } else {
            params = null;
        }
        SysScheduleTrigger sysScheduleTrigger = new SysScheduleTrigger();
        sysScheduleTrigger.setScheduleTriggerId(scheduleTriggerId);
        sysScheduleTrigger.setJobIp(pd.getString("jobIp"));
        sysScheduleTrigger.setJobGroup(jobGroup);
        sysScheduleTrigger.setJobName(jobName);
        sysScheduleTrigger.setCron(cron);
        sysScheduleTrigger.setParams(params);
        sysScheduleTrigger.setRemark(pd.getString("remark"));
        sysScheduleTrigger.setModifyUser(modifyUser);
        sysScheduleTrigger.setCheckValue(checkValue);
        int update = dao.update(sysScheduleTrigger);
        if (update == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long scheduleTriggerId = pd.getLong("scheduleTriggerId");
        if (scheduleTriggerId == null || scheduleTriggerId.compareTo(0L) != 1) {
            return PageResult.error("gnol系统_任务调度编号不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysScheduleTriggerDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("gnol系统_任务调度删除失败！");
    }

}
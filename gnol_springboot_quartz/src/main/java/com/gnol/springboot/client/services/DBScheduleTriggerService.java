package com.gnol.springboot.client.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.tools.date.DateUtil;
import com.gnol.plugins.tools.json.SFJsonUtil;
import com.gnol.springboot.common.daos.sys.BaseSysScheduleTriggerDao;
import com.gnol.springboot.common.dos.sys.SysScheduleTrigger;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: DBScheduleTriggerService
 * @Package: com.gnol.springboot.client.services
 * @author: 吴佳隆
 * @date: 2020年5月8日 下午7:50:23
 * @Description: 该类从 gnol 库中读取任务并添加到 quartz 中执行
 */
@Service(value = "dBScheduleTriggerService")
public class DBScheduleTriggerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;
    /**
     * gnol系统_任务调度基础 Dao
     */
    @Resource(name = "baseSysScheduleTriggerDao")
    private BaseSysScheduleTriggerDao baseSysScheduleTriggerDao;

    // 每 10 分钟执行一次
    @Scheduled(cron = "0 10 * * * ?")
    public void refreshScheduler() {
        logger.info("DBScheduleTriggerService=======>刷新定时任务的时间为{}...", DateUtil.getDateSecond());
        Page<SysScheduleTrigger> page = new Page<SysScheduleTrigger>();
        page.setPageSize(100);
        do {
            List<SysScheduleTrigger> sysScheduleTriggers = baseSysScheduleTriggerDao.listPage(page);
            refreshScheduler(sysScheduleTriggers);
            page.setCurrentPage(page.getCurrentPage() + 1);
        } while (page.getTotalPage() >= page.getCurrentPage());
    }

    @SuppressWarnings("unchecked")
    private void refreshScheduler(List<SysScheduleTrigger> sysScheduleTriggers) {
        if (CollectionUtils.isEmpty(sysScheduleTriggers)) {
            return;
        }
        for (SysScheduleTrigger sysScheduleTrigger : sysScheduleTriggers) {
            try {
                // 任务分组
                String jobGroup = sysScheduleTrigger.getJobGroup();
                // 任务名称
                String jobName = sysScheduleTrigger.getJobName();
                TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                // 根据 TriggerKey 到 Scheduler 调度器中获取触发器
                CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                // 任务状态
                Integer status = sysScheduleTrigger.getStatus();
                if (cronTrigger == null) { // 本任务还没有添加到 quartz 中
                    if (StatusEnum.equalValue(StatusEnum.DELETE, status)) {
                        continue;
                    }
                    // 创建任务详情
                    JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(jobName))
                            .withIdentity(jobName, jobGroup).build();

                    // 往 Job 任务中传递参数
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    Map<String, Object> params = SFJsonUtil.jsonToMap(sysScheduleTrigger.getParams());
                    if (params != null) {
                        jobDataMap.putAll(params);
                    }

                    // 创建表达式调度器
                    CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(sysScheduleTrigger.getCron());

                    // 创建 Trigger
                    cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(cronSchedule)
                            .build();

                    // 将 jobDetail 和 Trigger 注入到 scheduler 调度器中
                    scheduler.scheduleJob(jobDetail, cronTrigger);
                } else {
                    if (StatusEnum.equalValue(StatusEnum.DELETE, status)) {
                        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
                        scheduler.deleteJob(jobKey);
                        continue;
                    }
                    // 调度器中的表达式
                    String cronExpression = cronTrigger.getCronExpression();
                    String cron = sysScheduleTrigger.getCron();
                    if (cron.equals(cronExpression)) {
                        continue;
                    }
                    // 创建表达式调度器
                    CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cron);

                    // 重构
                    cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronSchedule)
                            .build();

                    // 刷新调度器
                    scheduler.rescheduleJob(triggerKey, cronTrigger);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
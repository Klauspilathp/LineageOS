package com.d7c.springboot.client.services.demo;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.d7c.plugins.tools.date.DateUtil;

/**
 * @Title: JobDemo2
 * @Package: com.d7c.springboot.client.services.demo
 * @author: 吴佳隆
 * @date: 2020年5月8日 下午7:55:35
 * @Description: quartz 的任务
 */
@Component
public class JobDemo2 implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("JobDemo2=======>执行时间：{}...", DateUtil.getDateSecond());
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        logger.info("JobDemo2=======>参数：{}，参数个数：{}...", jobDataMap.toString(), jobDataMap.size());
    }

}
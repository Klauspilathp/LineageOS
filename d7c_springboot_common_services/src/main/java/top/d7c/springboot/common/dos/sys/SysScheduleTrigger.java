package top.d7c.springboot.common.dos.sys;

import top.d7c.springboot.common.dos.BasePojo;

/**
 * @Title: SysScheduleTrigger
 * @Package: top.d7c.springboot.common.dos.sys
 * @author: 吴佳隆
 * @date: 2020年05月09日 14:02:58
 * @Description: d7c系统_任务调度 pojo
 */
public class SysScheduleTrigger extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long scheduleTriggerId;
    /**
     * 执行任务的服务器 IP
     */
    private String jobIp;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * 任务名、类名
     */
    private String jobName;
    /**
     * cron 表达式
     */
    private String cron;
    /**
     * 参数
     */
    private String params;
    /**
     * 备注
     */
    private String remark;

    public Long getScheduleTriggerId() {
        return scheduleTriggerId;
    }

    public void setScheduleTriggerId(Long scheduleTriggerId) {
        this.scheduleTriggerId = scheduleTriggerId;
    }

    public String getJobIp() {
        return jobIp;
    }

    public void setJobIp(String jobIp) {
        this.jobIp = jobIp;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_schedule_trigger";
        /**
         * 主键
         */
        public final String scheduleTriggerId = "scheduleTriggerId";
        /**
         * 执行任务的服务器 IP
         */
        public final String jobIp = "jobIp";
        /**
         * 任务组
         */
        public final String jobGroup = "jobGroup";
        /**
         * 任务名、类名
         */
        public final String jobName = "jobName";
        /**
         * cron 表达式
         */
        public final String cron = "cron";
        /**
         * 参数
         */
        public final String params = "params";
        /**
         * 备注
         */
        public final String remark = "remark";
        /**
         * 添加时间
         */
        public final String addTime = "addTime";
        /**
         * 添加人
         */
        public final String addUser = "addUser";
        /**
         * 修改时间
         */
        public final String modifyTime = "modifyTime";
        /**
         * 修改人
         */
        public final String modifyUser = "modifyUser";
        /**
         * 数据状态，0：删除，1：正常，2：冻结
         */
        public final String status = "status";
        /**
         * 数据当前版本
         */
        public final String checkValue = "checkValue";
    }

}
package com.d7c.springboot.client.services.evection.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Title: GMListener
 * @Package: com.d7c.springboot.client.services.evection.listener
 * @author: 吴佳隆
 * @date: 2021年1月16日 下午3:41:46
 * @Description: 指定总经理监听器
 */
public class GMListener implements TaskListener {
    private static final long serialVersionUID = -834004802558494724L;

    @Override
    public void notify(DelegateTask delegateTask) {
        /**
         * 监听事件 {@link org.activiti.engine.delegate.BaseTaskListener}
         */
        if (delegateTask.getId().equals("usertask3") && EVENTNAME_CREATE.equals(delegateTask.getEventName())) {
            // 出差申请人的总项目经理拾取任务
            delegateTask.setAssignee(EVENTNAME_ASSIGNMENT);
            // delegateTask.addCandidateUser("组任务任务接收人1");
            // delegateTask.addCandidateUser("组任务任务接收人2");
        }
    }

}
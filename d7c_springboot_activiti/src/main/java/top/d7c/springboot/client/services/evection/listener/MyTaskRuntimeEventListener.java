package top.d7c.springboot.client.services.evection.listener;

import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskActivatedEvent;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCancelledEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.TaskSuspendedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Title: MyTaskRuntimeEventListener
 * @Package: top.d7c.springboot.client.services.evection.listener
 * @author: 吴佳隆
 * @date: 2021年4月21日 下午4:52:12
 * @Description: 任务侦听器
 */
@SuppressWarnings("rawtypes")
@Service
public class MyTaskRuntimeEventListener implements TaskRuntimeEventListener {
    private static final Logger logger = LoggerFactory.getLogger(MyTaskRuntimeEventListener.class);

    @Override
    public void onEvent(RuntimeEvent runtimeEvent) {
        if (runtimeEvent instanceof TaskActivatedEvent)
            logger.info("Do something, task is activated: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof TaskAssignedEvent) {
            TaskAssignedEvent taskEvent = (TaskAssignedEvent) runtimeEvent;
            Task task = taskEvent.getEntity();
            logger.info("Do something, task is assigned: " + task.toString());
        } else if (runtimeEvent instanceof TaskCancelledEvent)
            logger.info("Do something, task is cancelled: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof TaskCompletedEvent)
            logger.info("Do something, task is completed: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof TaskCreatedEvent)
            logger.info("Do something, task is created: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof TaskSuspendedEvent)
            logger.info("Do something, task is suspended: " + runtimeEvent.toString());
        else
            logger.info("Unknown event: " + runtimeEvent.toString());
    }

}

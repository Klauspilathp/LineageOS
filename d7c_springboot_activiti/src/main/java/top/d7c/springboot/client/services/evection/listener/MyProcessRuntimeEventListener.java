package top.d7c.springboot.client.services.evection.listener;

import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.process.runtime.events.ProcessResumedEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.process.runtime.events.ProcessSuspendedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiSequenceFlowTakenEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Title: MyProcessRuntimeEventListener
 * @Package: top.d7c.springboot.client.services.evection.listener
 * @author: 吴佳隆
 * @date: 2021年4月21日 下午4:44:18
 * @Description: 进程监听器，进程侦听器自动连接到 ProcessRuntime， 监听器收到一个 RuntimeEvent，其中包含有关该事件的所有信息。 
 * 我们可以查看子类来确定事件的内容，例如 ProcessCompletedEvent。
 */
@SuppressWarnings("rawtypes")
@Service
public class MyProcessRuntimeEventListener implements ProcessRuntimeEventListener {
    private static final Logger logger = LoggerFactory.getLogger(MyProcessRuntimeEventListener.class);

    @Override
    public void onEvent(RuntimeEvent runtimeEvent) {
        if (runtimeEvent instanceof ProcessStartedEvent)
            logger.info("Do something, process is started: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ProcessCompletedEvent)
            logger.info("Do something, process is completed: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ProcessCancelledEvent)
            logger.info("Do something, process is cancelled: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ProcessSuspendedEvent)
            logger.info("Do something, process is suspended: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ProcessResumedEvent)
            logger.info("Do something, process is resumed: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ProcessCreatedEvent)
            logger.info("Do something, process is created: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof ActivitiSequenceFlowTakenEvent)
            logger.info("Do something, sequence flow is taken: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof VariableCreatedEvent)
            logger.info("Do something, variable was created: " + runtimeEvent.toString());
        else
            logger.info("Unknown event: " + runtimeEvent.toString());
    }

}

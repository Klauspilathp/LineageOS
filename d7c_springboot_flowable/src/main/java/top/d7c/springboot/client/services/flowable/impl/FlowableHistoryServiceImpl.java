package top.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.client.services.flowable.FlowableHistoryService;

/**
 * @Title: FlowableHistoryServiceImpl
 * @Package: top.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:54:50
 * @Description: flowable 流程历史记录的操作服务实现
 */
@Service(value = "flowableHistoryServiceImpl")
public class FlowableHistoryServiceImpl implements FlowableHistoryService {
    /**
     * 查询历史表数据服务
     */
    @Autowired
    private HistoryService historyService;

    @Override
    public PageResult listHistoryTask(String processInstanceId) {
        if (StringUtil.isBlank(processInstanceId)) {
            return PageResult.error("processInstanceId 不能为空！");
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByTaskCreateTime().desc().list();

        List<PageData> pds = new ArrayList<PageData>();
        historicTaskInstances.stream().forEach(historicTask -> {
            pds.add(PageData.build().set("taskId", historicTask.getId()) // 历史任务 ID
                    .set("taskName", historicTask.getName()) // 历史任务名称
                    .set("assignee", historicTask.getAssignee()) // 任务接收人
                    .set("claimTime", historicTask.getClaimTime()) // 完成时间
                    .set("createTime", historicTask.getCreateTime()) // 创建时间
                    .set("processDefinitionId", historicTask.getProcessDefinitionId()) // 流程部署 ID
                    .set("processInstanceId", historicTask.getProcessInstanceId()) // 流程实例 ID 
            );
        });

        return PageResult.ok(pds);
    }

}

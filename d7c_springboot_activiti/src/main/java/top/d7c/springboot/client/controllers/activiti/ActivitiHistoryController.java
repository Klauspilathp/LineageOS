package top.d7c.springboot.client.controllers.activiti;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.services.activiti.ActivitiHistoryService;

/**
 * @Title: ActivitiHistoryController
 * @Package: top.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程历史记录的操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/history")
public class ActivitiHistoryController extends WebBaseController {
    /**
     * activiti 流程历史记录的操作服务
     */
    @Resource(name = "activitiHistoryServiceImpl")
    private ActivitiHistoryService activitiHistoryService;

    /**
     * @Title: listHistoricActivityInstanceById
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午8:47:31
     * @Description: 根据流程实例 ID 或流程定义 ID 查询历史信息。
     * @param processInstanceId
     * @param processDefinitionId
     * @return PageResult
     */
    @GetMapping(value = "/listHistoricActivityInstanceById")
    public PageResult listHistoricActivityInstanceById(@RequestParam("processInstanceId") String processInstanceId,
            @RequestParam("processDefinitionId") String processDefinitionId) {
        if (StringUtil.isBlank(processInstanceId) && StringUtil.isBlank(processDefinitionId)) {
            return PageResult.error("请传入流程实例 ID 或流程定义 ID！");
        }
        return PageResult
                .ok(activitiHistoryService.listHistoricActivityInstanceById(processInstanceId, processDefinitionId));
    }

    /**
     * @Title: listHistoricTaskInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午3:10:58
     * @Description: 分页查询任务代理人的历史代理任务实例
     * @param page
     * @param assignee      代理人，如果不传，默认查当前登录人所完成的历史任务实例。
     * @return PageResult
     */
    @GetMapping(value = "/listHistoricTaskInstanceByAssignee")
    public PageResult listHistoricTaskInstanceByAssignee(Page<PageData> page) {
        PageData pd = this.getPageData();
        page.setArgs(pd);
        return activitiHistoryService.listHistoricTaskInstanceByAssignee(page);
    }

}
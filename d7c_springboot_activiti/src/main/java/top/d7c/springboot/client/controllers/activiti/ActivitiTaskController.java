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
import top.d7c.springboot.client.services.activiti.ActivitiTaskService;

/**
 * @Title: ActivitiTaskController
 * @Package: top.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程任务操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/task")
public class ActivitiTaskController extends WebBaseController {
    /**
     * activiti 流程任务操作服务
     */
    @Resource(name = "activitiTaskServiceImpl")
    private ActivitiTaskService activitiTaskService;

    /**
     * @Title: deleteCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 下午12:04:52
     * @Description: 从组任务中删除候选人
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/deleteCandidateUser")
    public PageResult deleteCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        return activitiTaskService.deleteCandidateUser(taskId, candidateUser);
    }

    /**
     * @Title: addCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 下午12:03:28
     * @Description: 向组任务中添加任务候选人
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/addCandidateUser")
    public PageResult addCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        return activitiTaskService.addCandidateUser(taskId, candidateUser);
    }

    /**
     * @Title: releaseByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午11:58:10
     * @Description: 组任务的候选人退回任务
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/releaseByCandidateUser")
    public PageResult releaseByCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        return activitiTaskService.releaseByCandidateUser(taskId, candidateUser);
    }

    /**
     * @Title: claimByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午10:29:27
     * @Description: 组任务的候选人拾取任务
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/claimByCandidateUser")
    public PageResult claimByCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        return activitiTaskService.claimByCandidateUser(taskId, candidateUser);
    }

    /**
     * @Title: listTaskByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午10:23:12
     * @Description: 查询候选人任务列表
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/listTaskByCandidateUser")
    public PageResult listTaskByCandidateUser(@RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        return PageResult.ok(activitiTaskService.listTaskByCandidateUser(candidateUser));
    }

    /**
     * @Title: listTaskByKey
     * @author: 吴佳隆
     * @data: 2021年1月19日 上午9:49:49
     * @Description: 根据给定的 taskDefinitionKey（UserTask 的 id） 查询任务列表。
     * @param taskDefinitionKey 任务定义键
     * @return PageResult
     */
    @GetMapping(value = "/listTaskByKey")
    public PageResult listTaskByKey(@RequestParam("taskDefinitionKey") String taskDefinitionKey) {
        if (StringUtil.isBlank(taskDefinitionKey)) {
            return PageResult.error("taskDefinitionKey 不能为空！");
        }
        return PageResult.ok(activitiTaskService.listTaskByKey(taskDefinitionKey));
    }

    /**
     * @Title: listTasks
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:53:34
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    @GetMapping(value = "/listTasks")
    public PageResult listTasks(Page<PageData> page) {
        return activitiTaskService.listTasks(page);
    }

    /**
     * @Title: completeTask
     * @author: 吴佳隆
     * @data: 2021年1月20日 下午5:44:45
     * @Description: 执行个人任务
     * @param taskId        任务 ID
     * @return PageResult
     */
    @GetMapping(value = "/completeTask")
    public PageResult completeTask(@RequestParam("taskId") String taskId) {
        return activitiTaskService.completeTask(taskId);
    }

}
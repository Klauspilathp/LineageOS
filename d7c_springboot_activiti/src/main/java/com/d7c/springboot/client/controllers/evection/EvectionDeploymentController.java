package com.d7c.springboot.client.controllers.evection;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: EvectionDeploymentController
 * @Package: com.d7c.springboot.client.controllers.evection
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: 出差流程部署控制类
 */
@RestController
@RequestMapping(value = "/evection/deployment")
public class EvectionDeploymentController extends WebBaseController {
    /**
     * 操作流程实例服务
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 提供对流程定义和部署存储库的访问的服务
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * @Title: deleteDeployment
     * @author: 吴佳隆
     * @data: 2021年1月18日 上午9:51:32
     * @Description: 删除给定的部署和对流程实例、历史流程实例和作业的级联删除。
     * @param deploymentId  部署id，不能为空。
     * @return PageResult
     */
    @GetMapping(value = "/deleteDeployment")
    public PageResult deleteDeployment(@RequestParam("deploymentId") String deploymentId) {
        if (StringUtil.isBlank(deploymentId)) {
            return PageResult.error("deploymentId 不能为空！");
        }
        repositoryService.deleteDeployment(deploymentId, true // 级联删除
        );
        return PageResult.ok(deploymentId + "流程部署已删除！");
    }

}
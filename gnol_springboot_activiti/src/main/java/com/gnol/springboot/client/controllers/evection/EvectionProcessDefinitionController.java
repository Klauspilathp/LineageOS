package com.gnol.springboot.client.controllers.evection;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.tools.idfactory.uuid.UUID;
import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: EvectionFlowController
 * @Package: com.gnol.springboot.client.controllers.evection
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: 出差流程控制类
 */
@RestController
@RequestMapping(value = "/evection/processDefinition")
public class EvectionProcessDefinitionController extends WebBaseController {
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
     * @Title: deploymentUploadProcess
     * @author: 吴佳隆
     * @data: 2021年1月16日 下午5:22:56
     * @Description: 部署上传的流程文件
     * @param request
     * @param multipartFile
     * @return PageResult
     */
    @PostMapping(value = "/deploymentUploadProcess")
    @ResponseBody
    public PageResult deploymentUploadProcess(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return PageResult.error("文件为空");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String extensionName = FilenameUtils.getExtension(originalFilename);
        if (StringUtil.isBlank(extensionName) || !(extensionName.equals("bpmn") || extensionName.equals("zip"))) {
            return PageResult.error("文件后缀名必须以 .bpmn 或 .zip 结尾。");
        }

        // 流程部署名称
        String deploymentName = request.getParameter("deploymentName");
        if (StringUtil.isBlank(deploymentName)) {
            deploymentName = originalFilename.replace(extensionName, StringUtil.EMPTY);
        }

        // 流程部署成功返回的部署对象
        Deployment deployment = null;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            if (extensionName.equals("bpmn")) {
                deployment = repositoryService.createDeployment().addInputStream(originalFilename, inputStream)
                        .name(deploymentName) // 初始化流程
                        .deploy();
            } else {
                ZipInputStream zip = new ZipInputStream(inputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).name(deploymentName) // 初始化流程
                        .deploy();
            }
            return PageResult.ok(deployment.getId());
        } catch (IOException e) {
            return PageResult.error(e.getMessage());
        }
    }

    @PostMapping(value = "/deploymentLocalProcess")
    @ResponseBody
    public PageResult deploymentLocalProcess() {
        PageData pd = this.getPageData();
        // 流程部署名称
        String deploymentName = pd.getString("deploymentName");
        if (StringUtil.isBlank(deploymentName)) {
            deploymentName = UUID.getUUID().nextStr();
        }
        // 流程部署成功返回的部署对象
        Deployment deployment = null;

        // 部署资源的文件名，该文件放在 resources/processess/ 目录下
        String deploymentFileName = pd.getString("deploymentFileName");
        if (StringUtil.isNotBlank(deploymentFileName)) {
            deployment = repositoryService.createDeployment()
                    .addClasspathResource("resources/processess/" + deploymentFileName).name(deploymentName) // 初始化流程
                    .deploy();
            return PageResult.ok(deployment.getId());
        }

        // 在线部署
        String deploymentObj = pd.getString("deploymentObj");
        if (StringUtil.isNotBlank(deploymentObj)) {
            deployment = repositoryService.createDeployment().addString(deploymentName + ".bpmn", deploymentObj)
                    .name(deploymentName) // 初始化流程
                    .deploy();
            return PageResult.ok(deployment.getId());
        }

        return PageResult.error("部署对象为空");
    }

}
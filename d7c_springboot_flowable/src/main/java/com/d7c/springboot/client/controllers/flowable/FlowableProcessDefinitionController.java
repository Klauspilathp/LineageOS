package com.d7c.springboot.client.controllers.flowable;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.flowable.FlowableProcessDefinitionService;

/**
 * @Title: FlowableProcessDefinitionController
 * @Package: com.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:57:55
 * @Description: flowable 流程定义操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/processDefinition")
public class FlowableProcessDefinitionController extends WebBaseController {
    /**
     * flowable 流程定义操作服务
     */
    @Resource(name = "flowableProcessDefinitionServiceImpl")
    private FlowableProcessDefinitionService flowableProcessDefinitionService;
    /**
     * 提供对流程定义和部署存储库的访问服务
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

}

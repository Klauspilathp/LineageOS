package com.d7c.springboot.client.controllers.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.enums.HttpStatus;
import com.d7c.plugins.tools.idfactory.uuid.UUID;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: ActivitiProcessDefinitionController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程定义操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/processDefinition")
public class ActivitiProcessDefinitionController extends WebBaseController {
    /**
     * 操作流程实例服务
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * @Title: getProcessDefinitionXML
     * @author: 吴佳隆
     * @data: 2021年1月18日 上午9:26:28
     * @Description: 获取流程定义 XML
     * @param response
     * @param deploymentId  部署 id，不能为空。
     * @param resourceName  资源的名称，不能为空。
     * @throws IOException 
     */
    @GetMapping(value = "/getProcessDefinitionXML")
    public void getProcessDefinitionXML(HttpServletResponse response, @RequestParam("deploymentId") String deploymentId,
            @RequestParam("resourceName") String resourceName) throws IOException {
        if (StringUtil.isBlank(deploymentId)) {
            response(response, HttpStatus.HS_270.getKey(), "deploymentId 不能为空！");
            return;
        }
        if (StringUtil.isBlank(resourceName)) {
            response(response, HttpStatus.HS_270.getKey(), "resourceName 不能为空！");
            return;
        }
        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        int length = inputStream.available();
        byte[] bytes = new byte[length];
        response.setContentType("text/xml");
        OutputStream outputStream = response.getOutputStream();
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }
        inputStream.close();
        response.flushBuffer();
    }

    /**
     * @Title: listProcessDefinition
     * @author: 吴佳隆
     * @data: 2021年1月18日 上午9:11:15
     * @Description: 分页查询流程定义列表
     * @return PageResult
     */
    @GetMapping(value = "/listProcessDefinition")
    public PageResult listProcessDefinition(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<ProcessDefinition> processDefinitions = processRuntime
                .processDefinitions(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(processDefinitions.getTotalItems());
        return PageResult.ok(processDefinitions.getContent()).setPage(page);
    }

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

    /**
     * @Title: deploymentLocalProcess
     * @author: 吴佳隆
     * @data: 2021年1月18日 上午8:57:52
     * @Description: 部署 resources/processess/ 目录下的资源文件或部署在线编辑流程
     * @return PageResult
     */
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

        // 部署 resources/processess/ 目录下的资源文件
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
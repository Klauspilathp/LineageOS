package com.d7c.springboot.client.controllers.flowable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.enums.HttpStatus;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.flowable.FlowableProcessInstanceService;

/**
 * @Title: FlowableProcessInstanceController
 * @Package: com.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:58:18
 * @Description: flowable 流程实例操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/processInstance")
public class FlowableProcessInstanceController extends WebBaseController {
    /**
     * flowable 流程实例操作服务
     */
    @Resource(name = "flowableProcessInstanceServiceImpl")
    private FlowableProcessInstanceService flowableProcessInstanceService;

    /**
     * @Title: getProcessDiagram
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午3:26:48
     * @Description: 获取流程图
     * @param response
     * @param processInstanceId 流程实例 ID
     * @throws IOException
     */
    @GetMapping(value = "/getProcessDiagram")
    public void getProcessDiagram(HttpServletResponse response,
            @RequestParam(value = "processInstanceId") String processInstanceId) throws IOException {
        if (StringUtil.isBlank(processInstanceId)) {
            response(response, HttpStatus.HS_270.getKey(), "processInstanceId 不能为空！");
            return;
        }

        InputStream inputStream = flowableProcessInstanceService.getProcessDiagramInputStream(processInstanceId);
        OutputStream outputStream = response.getOutputStream();

        /*try {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }*/

        int length = inputStream.available();
        byte[] bytes = new byte[length];
        response.setContentType("text/xml");
        while (inputStream.read(bytes) != -1) {
            outputStream.write(bytes);
        }
        inputStream.close();
        response.flushBuffer();
    }

}

package com.d7c.springboot.client.controllers.sys;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.d7c.fastDFS.spring.boot.autoconfigure.FastDFService;
import com.d7c.plugins.core.PageResult;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: CommonController
 * @Package: com.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:45
 * @Description: d7c 系统_公共 Controller
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController extends WebBaseController {
    /**
     * fastDFS 服务实现
     */
    @Resource(name = "fastDFServiceImpl")
    private FastDFService fastDFService;

    /**
     * @Title: getTime
     * @author: 吴佳隆
     * @data: 2020年5月1日 上午9:43:02
     * @Description: 获取服务器时间
     * @return PageResult
     */
    @RequestMapping(value = "/getTime")
    @ResponseBody
    public PageResult getTime() {
        return PageResult.ok(new Date());
    }

    /**
     * @Title: uploadFile
     * @author: 吴佳隆
     * @data: 2020年5月1日 上午9:44:43
     * @Description: 上传文件，返回图片路径
     * @param multipartFile
     * @return PageResult
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public PageResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return PageResult.error("图片为空");
        }
        byte[] bytes = null;
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            return PageResult.error(e.getMessage());
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String extensionName = FilenameUtils.getExtension(originalFilename);
        String imgPath = fastDFService.uploadFile(bytes, extensionName, null);
        return PageResult.ok(imgPath);
    }

}
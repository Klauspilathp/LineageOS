package top.d7c.springboot.client.controlles.swagger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.tools.idfactory.IdFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @Title: UserTestController
 * @Package: top.d7c.springboot.client.controlles.swagger
 * @author: 吴佳隆
 * @date: 2019年7月29日 下午7:10:10
 * @Description: swagger 测试 Controller
 */
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "UserTest")
public class UserTestController {

    /**
     * @Title: deleteFile
     * @author: 吴佳隆
     * @data: 2020年5月21日 下午7:14:08
     * @Description: 删除文件
     * @param request
     * @return PageResult
     */
    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public PageResult deleteFile() {
        PageData pd = PageData.build().set("a", "aa");
        PageResult result = PageResult.ok(pd);
        return result;
    }

    /**
     * @Title: uploadFile
     * @author: 吴佳隆
     * @data: 2020年5月21日 下午7:14:08
     * @Description: 上传文件
     * @param request
     * @return PageResult
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public PageResult uploadFile(MultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multiFileMap = request.getMultiFileMap();
        if (multiFileMap == null || multiFileMap.isEmpty()) {
            return PageResult.error("保存图片为空");
        }
        Set<Entry<String, List<MultipartFile>>> MultipartFileSet = multiFileMap.entrySet();
        for (Entry<String, List<MultipartFile>> entry : MultipartFileSet) {
            List<MultipartFile> multipartFiles = entry.getValue();
            if (multipartFiles == null || multipartFiles.isEmpty()) {
                break;
            }
            MultipartFile multipartFile = multipartFiles.get(0);
            String originalFilename = multipartFile.getOriginalFilename();
            String extensionName = FilenameUtils.getExtension(originalFilename);
            File file = new File("E:\\Trash\\temp\\" + originalFilename + "." + extensionName);
            try {
                multipartFile.transferTo(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", IdFactory.nextLong());
        PageResult result = PageResult.ok(map);
        return result;
    }

    @GetMapping(value = "selectById")
    @ApiOperation(httpMethod = "GET", value = "根据用户 userId 查询用户详细信息", notes = "根据用户 userId 查询用户详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户 userId", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户昵称", required = false, paramType = "query"),})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "0 成功"), @ApiResponse(code = 500, message = "系统错误")})
    public Object selectById(@RequestParam(value = "userId") Long userId,
            @RequestParam(value = "username") String username) {
        return PageResult.ok(new UserTest(111111111111L, "吴佳隆", 1));
    }

    @GetMapping(value = "deleteById")
    @ApiOperation(value = "根据用户 userId 删除用户信息", notes = "根据用户 userId 删除用户信息")
    public Object deleteById(@ApiParam(required = true, name = "userId", value = "用户主键") Long userId) {
        return PageResult.ok();
    }

}
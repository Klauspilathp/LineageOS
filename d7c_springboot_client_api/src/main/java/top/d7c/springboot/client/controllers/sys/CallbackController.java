package top.d7c.springboot.client.controllers.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: CallbackController
 * @Package: top.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年8月2日 上午9:41:50
 * @Description: 请求回调处理类
 */
@RestController
@RequestMapping(value = "/callback")
public class CallbackController extends WebBaseController {
    /**
     * oauth2 rest 服务客户端
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 认证服务器属性配置
     */
    @Autowired
    private OAuth2ProtectedResourceDetails resourceDetails;

    /**
     * @Title: getToken
     * @author: 吴佳隆
     * @data: 2020年8月2日 上午9:43:45
     * @Description: 获取 token
     * @param code
     * @return PageResult
     */
    @GetMapping(value = "/getToken")
    public PageResult getToken(String code) {
        if (StringUtil.isBlank(code)) {
            return PageResult.error("code 不能为空");
        }
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", resourceDetails.getClientId());
        map.add("client_secret", resourceDetails.getClientSecret());
        map.add("code", code);
        HttpHeaders headers = new HttpHeaders();
        // 以表单表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> postForEntity = restTemplate.postForEntity(resourceDetails.getAccessTokenUri(), request,
                Map.class);
        return PageResult.ok(postForEntity.getBody());
    }

}
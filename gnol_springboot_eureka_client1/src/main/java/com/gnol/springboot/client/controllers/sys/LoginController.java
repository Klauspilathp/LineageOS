package com.gnol.springboot.client.controllers.sys;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.client.config.ClientResources;
import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: LoginController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年8月2日 下午5:02:24
 * @Description: 登录控制器
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends WebBaseController {
    /**
     * eureka client1 资源属性
     */
    @Resource(name = "gnolEurekaClient1ClientResources")
    private ClientResources gnolEurekaClient1ClientResources;
    /**
     * github 资源属性
     */
    @Resource(name = "githubClientResources")
    private ClientResources githubClientResources;
    /**
     * 没有负载均衡能力的 RestTemplate
     */
    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    /**
     * @Title: eureka_client1
     * @author: 吴佳隆
     * @data: 2020年8月2日 下午5:27:02
     * @Description: eureka client1 登录，http://127.0.0.1:8092/login/eureka_client1
     * @return String
     */
    @GetMapping(value = "/eureka_client1")
    public String eureka_client1() {
        StringBuilder sb = new StringBuilder();
        return sb.append("redirect:")
                .append(gnolEurekaClient1ClientResources.getCodeResourceDetails().getUserAuthorizationUri())
                .append("?client_id=").append(gnolEurekaClient1ClientResources.getCodeResourceDetails().getClientId())
                .append("&response_type=code&state=eureka_client1").toString();
    }

    /**
     * @Title: github
     * @author: 吴佳隆
     * @data: 2020年8月2日 下午5:27:20
     * @Description: github 授权登录，http://127.0.0.1:8092/login/github
     * @return String
     */
    @GetMapping(value = "/github")
    public String github() {
        StringBuilder sb = new StringBuilder();
        // https://github.com/login/oauth/authorize?client_id=ecd6d721d292aabf3437&response_type=code&state=github
        return sb.append("redirect:").append(githubClientResources.getCodeResourceDetails().getUserAuthorizationUri())
                .append("?client_id=").append(githubClientResources.getCodeResourceDetails().getClientId())
                .append("&response_type=code&state=github").toString();
    }

    /**
     * @Title: callback
     * @author: 吴佳隆
     * @data: 2020年8月2日 下午5:36:57
     * @Description: 登录回调
     * @return PageResult
     */
    @GetMapping(value = "/callback")
    @ResponseBody
    public PageResult callback() {
        PageData pd = this.getPageData();
        String code = pd.getString("code");
        if (StringUtil.isBlank(code)) {
            return PageResult.error("授权码为空");
        }
        String state = pd.getString("state");
        if (StringUtil.equals("github", state)) {
            return github(code);
        }
        return eureka_client1(code);
    }

    private PageResult eureka_client1(String code) {
        StringBuilder url = new StringBuilder();
        url.append(gnolEurekaClient1ClientResources.getCodeResourceDetails().getAccessTokenUri()).append("?client_id=")
                .append(gnolEurekaClient1ClientResources.getCodeResourceDetails().getClientId())
                .append("&client_secret=")
                .append(gnolEurekaClient1ClientResources.getCodeResourceDetails().getClientSecret()).append("&code=")
                .append(code).append("&grant_type=authorization_code");
        // 构建请求头
        HttpHeaders token_httpHeaders = new HttpHeaders();
        // 指定响应返回 json 格式
        token_httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 构建请求实体
        HttpEntity<String> token_httpEntity = new HttpEntity<>(token_httpHeaders);
        ResponseEntity<JSONObject> token_responseEntity = restTemplate.postForEntity(url.toString(), token_httpEntity,
                JSONObject.class);
        JSONObject token_json = token_responseEntity.getBody();
        String accessToken = token_json.getString("access_token");

        // get 请求获取用户信息
        String user_info_url = gnolEurekaClient1ClientResources.getResourceServer().getUserInfoUri() + "?access_token="
                + accessToken;
        JSONObject user_info_json = restTemplate.getForObject(user_info_url, JSONObject.class);
        return PageResult.ok(user_info_json);
    }

    private PageResult github(String code) {
        StringBuilder url = new StringBuilder();
        url.append(githubClientResources.getCodeResourceDetails().getAccessTokenUri()).append("?client_id=")
                .append(githubClientResources.getCodeResourceDetails().getClientId()).append("&client_secret=")
                .append(githubClientResources.getCodeResourceDetails().getClientSecret()).append("&code=").append(code)
                .append("&grant_type=authorization_code");
        // 构建请求头
        HttpHeaders token_httpHeaders = new HttpHeaders();
        // 指定响应返回 json 格式
        token_httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 构建请求实体
        HttpEntity<String> token_httpEntity = new HttpEntity<>(token_httpHeaders);
        ResponseEntity<JSONObject> token_responseEntity = restTemplate.postForEntity(url.toString(), token_httpEntity,
                JSONObject.class);
        JSONObject token_json = token_responseEntity.getBody();
        String accessToken = token_json.getString("access_token");

        /*// get 请求获取用户信息
        String user_info_url = githubClientResources.getResourceServer().getUserInfoUri() + "?access_token="
                + accessToken;
        // https://api.github.com/user?access_token=7763d0ae6c9e1963605933baba4867d5ab416aa9
        ResponseEntity<JSONObject> user_info_responseEntity = restTemplate.getForEntity(user_info_url, JSONObject.class);
        JSONObject user_info_json = user_info_responseEntity.getBody();*/
        return PageResult.ok(accessToken);
    }

}
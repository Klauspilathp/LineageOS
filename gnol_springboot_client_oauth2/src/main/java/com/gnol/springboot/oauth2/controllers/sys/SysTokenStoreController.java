package com.gnol.springboot.oauth2.controllers.sys;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.oauth2.controllers.WebBaseController;

/**
 * @Title: SysTokenStoreController
 * @Package: com.gnol.springboot.oauth2.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午7:19:15
 * @Description: token 管理
 */
@Controller
@RequestMapping(value = "/common")
public class SysTokenStoreController extends WebBaseController {
    /**
     * token 持久化策略
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * @Title: findTokensByClientId
     * @author: 吴佳隆
     * @data: 2020年7月22日 下午7:23:58
     * @Description: 根据客户端标识查询 token 集合
     * @param clientId
     * @return PageResult
     */
    @GetMapping("/findTokensByClientId/{clientId}")
    public PageResult findTokensByClientId(String clientId) {
        Collection<OAuth2AccessToken> oAuth2AccessTokens = tokenStore.findTokensByClientId(clientId);
        return PageResult.ok(oAuth2AccessTokens);
    }

    /**
     * @Title: findTokensByClientIdAndUserName
     * @author: 吴佳隆
     * @data: 2020年7月22日 下午7:26:36
     * @Description: 根据客户端标识和用户名查询 token 结合
     * @param clientId
     * @param userName
     * @return PageResult
     */
    @GetMapping("/findTokensByClientId/{clientId}/{userName}")
    public PageResult findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<OAuth2AccessToken> oAuth2AccessTokens = tokenStore.findTokensByClientIdAndUserName(clientId,
                userName);
        return PageResult.ok(oAuth2AccessTokens);
    }

    /**
     * @Title: removeAccessTokenByClientId
     * @author: 吴佳隆
     * @data: 2020年7月22日 下午7:30:17
     * @Description: 删除一个客户端标识下的 token
     * @param clientId
     * @return PageResult
     */
    @GetMapping("/removeAccessTokenByClientId/{clientId}")
    public PageResult removeAccessTokenByClientId(String clientId) {
        Collection<OAuth2AccessToken> oAuth2AccessTokens = tokenStore.findTokensByClientId(clientId);
        oAuth2AccessTokens.forEach(token -> tokenStore.removeAccessToken(token));
        return PageResult.ok();
    }

}
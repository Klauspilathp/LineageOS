package com.d7c.springboot.client.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.d7c.jwt.spring.boot.autoconfigure.JwtRsaUtil;
import com.d7c.oauth2.spring.boot.CustomUserDetails;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.enums.HttpStatus;
import com.d7c.springboot.client.daos.security.ExtSecurityKeyDao;
import com.d7c.springboot.common.constant.AuthConstant;
import com.d7c.springboot.common.dos.security.SecurityKey;
import com.d7c.springboot.common.enums.auth.AuthTypeEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: JwtLoginFilter
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午8:53:40
 * @Description: 认证授权过滤器，这里采用 JWT 返回加密 token
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 认证管理对象
     */
    private AuthenticationManager authenticationManager;
    /**
     * d7c 系统安全模块_用户或系统密钥扩展 Dao
     */
    private ExtSecurityKeyDao securityKeyDao;
    /**
     * jwt 采用 RSA 方式加密的 token 工具类
     */
    private JwtRsaUtil JwtRsaUtil;

    public JwtLoginFilter(AuthenticationManager authenticationManager, ExtSecurityKeyDao securityKeyDao,
            com.d7c.jwt.spring.boot.autoconfigure.JwtRsaUtil jwtRsaUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.securityKeyDao = securityKeyDao;
        this.JwtRsaUtil = jwtRsaUtil;
    }

    /**
     * 认证操作
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    request.getParameter(getUsernameParameter()), request.getParameter(getPasswordParameter()));
            return authenticationManager.authenticate(authRequest);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        try {
            response.getWriter().println(PageResult.build(HttpStatus.HS_403.getKey(), "授权失败").toString());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new AuthenticationServiceException("authorization failure !");
    }

    /**
     * 认证成功后操作
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(request.getParameter(D7cConstant.AUTH_TYPE));
        if (authTypeEnum == null) {
            throw new UsernameNotFoundException("auth_type cannot be empty !");
        }
        String appid = request.getParameter(SecurityKey.M.appid);
        appid = StringUtil.isBlank(appid) ? authTypeEnum.getKey() : appid;
        SecurityKey securityKey = securityKeyDao.getPrivateKey(appid);
        if (securityKey == null) {
            throw new UnavailableException("the authorization key does not exist. please contact your administrator !");
        }
        if (!StatusEnum.equalValue(StatusEnum.NORMAL, securityKey.getStatus())) {
            throw new UnavailableException("the authorization key has been deleted. please use the new key !");
        }
        Map<String, Object> claims = new HashMap<String, Object>();
        String jwt_id = null;
        switch (authTypeEnum) {
            case WEB:
                CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
                Map<String, Object> params = (Map<String, Object>) userDetails.getParams();
                claims.put("userId", params.get("userId"));
                claims.put("roleId", params.get("roleId"));
                claims.put("username", userDetails.getUsername());
                // claims.put("authorities", JSONArray.toJSONString(userDetails.getAuthorities()));
                jwt_id = StringUtil.toString(params.get("userId"));
                break;
            case WAP:

                break;
            case MOBILE:

                break;
        }
        String token = JwtRsaUtil.generateToken(claims, jwt_id, appid, securityKey.getPrivateKey(),
                securityKey.getExpiration());
        response.addHeader(AuthConstant.AUTH_HEADER, "Bearer " + token);
        response.addHeader(SecurityKey.M.appid, appid);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().println(PageResult.ok(token).toString());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
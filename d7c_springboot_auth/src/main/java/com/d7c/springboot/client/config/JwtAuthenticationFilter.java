package com.d7c.springboot.client.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.d7c.jwt.springboot.autoconfigure.JwtRsaUtil;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.enums.HttpStatus;
import com.d7c.springboot.client.daos.security.ExtSecurityKeyDao;
import com.d7c.springboot.client.daos.sys.ExtSysMenuDao;
import com.d7c.springboot.common.constant.AuthConstant;
import com.d7c.springboot.common.dos.security.SecurityKey;
import com.d7c.springboot.common.enums.sys.StatusEnum;

import io.jsonwebtoken.Claims;

/**
 * @Title: JwtAuthenticationFilter
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午9:01:22
 * @Description: JWT 验证 token 是否合法过滤器
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    /**
     * d7c 系统安全模块_用户或系统密钥扩展 Dao
     */
    private ExtSecurityKeyDao securityKeyDao;
    /**
     * jwt 采用 RSA 方式加密的 token 工具类
     */
    private JwtRsaUtil JwtRsaUtil;
    /**
     * d7c 系统菜单表扩展 Dao
     */
    private ExtSysMenuDao sysMenuDao;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ExtSecurityKeyDao securityKeyDao,
            JwtRsaUtil JwtRsaUtil, ExtSysMenuDao sysMenuDao) {
        super(authenticationManager);
        this.securityKeyDao = securityKeyDao;
        this.JwtRsaUtil = JwtRsaUtil;
        this.sysMenuDao = sysMenuDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获取请求头中的 appid
        String appid = request.getHeader(SecurityKey.M.appid);
        if (StringUtil.isBlank(appid)) {
            response(response, HttpServletResponse.SC_FORBIDDEN,
                    PageResult.build(HttpStatus.HS_403.getKey(), "访问资源服务器上的接口必须在 header 中携带 appid！"));
            return;
        }

        // 获取请求头或请求参数中的 token
        String token = request.getHeader(AuthConstant.AUTH_HEADER);
        if (StringUtil.isBlank(token)) {
            // 获取 token 请求头中的 token
            token = request.getHeader(AuthConstant.TOKEN);
            if (StringUtil.isBlank(token)) {
                // 从参数中获取 token
                token = request.getParameter(AuthConstant.TOKEN);
            }
        } else {
            token = token.substring("Bearer ".length());
        }

        if (StringUtil.isBlank(token)) {
            response(response, HttpServletResponse.SC_FORBIDDEN,
                    PageResult.build(HttpStatus.HS_403.getKey(), "请先进行授权！"));
            return;
        }

        // 查询公钥
        SecurityKey securityKey = securityKeyDao.getPublicKey(appid);
        if (securityKey == null || !StatusEnum.equalValue(StatusEnum.NORMAL, securityKey.getStatus())) {
            response(response, HttpServletResponse.SC_FORBIDDEN,
                    PageResult.build(HttpStatus.HS_403.getKey(), "您的授权密钥不存在或已过期！"));
            return;
        }

        // token 解析
        Claims claims = JwtRsaUtil.getClaimsByToken(token, securityKey.getPublicKey());
        if (claims == null) {
            response(response, HttpServletResponse.SC_FORBIDDEN,
                    PageResult.build(HttpStatus.HS_403.getKey(), "token 解析错误或已过期！"));
            return;
        }

        // 匹配第一套认证方式
        if (request.getServletPath().equals("/validate")) {
            response(response, HttpServletResponse.SC_OK, PageResult.ok("欢迎您【" + claims.get("username") + "】。"));
            return;
        }

        // 初始化 SecurityContextHolder对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                StringUtil.toString(claims.get("username")), null,
                // JSONArray.parseArray(StringUtil.toString(claims.get("authorities")), SimpleGrantedAuthority.class)
                sysMenuDao.listPermissionsByRoleId(StringUtil.toLong(claims.get("roleId"))));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    protected void response(HttpServletResponse response, int status, PageResult result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(status);
        try {
            response.getWriter().println(result.toString());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.gnol.springboot.client.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.gnol.jwt.spring.boot.autoconfigure.JwtRsaUtil;
import com.gnol.oauth2.spring.boot.CustomUserDetails;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.springboot.client.services.sys.SysMenuService;
import com.gnol.springboot.common.constant.AuthConstant;

import io.jsonwebtoken.Claims;

/**
 * @Title: JwtAuthenticationFilter
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月20日 上午9:01:22
 * @Description: JWT 验证 token 是否合法过滤器
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    /**
     * gnol 系统自定义属性
     */
    private GnolProperties gnolProperties;
    /**
     * jwt 采用 RSA 方式加密的 token 工具类
     */
    private JwtRsaUtil JwtRsaUtil;
    /**
     * gnol 系统菜单表 Service
     */
    private SysMenuService sysMenuService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, GnolProperties gnolProperties,
            JwtRsaUtil JwtRsaUtil, SysMenuService sysMenuService) {
        super(authenticationManager);
        this.gnolProperties = gnolProperties;
        this.JwtRsaUtil = JwtRsaUtil;
        this.sysMenuService = sysMenuService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
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

        // token 解析
        Claims claims = JwtRsaUtil.getClaimsByToken(token, gnolProperties.getRsaPublicKey());
        if (claims == null) {
            response(response, HttpServletResponse.SC_FORBIDDEN,
                    PageResult.build(HttpStatus.HS_403.getKey(), "token 解析错误或已过期！"));
            return;
        }

        // 初始化 SecurityContextHolder对象
        Set<SimpleGrantedAuthority> authorities = sysMenuService
                .listPermissionsByRoleId(StringUtil.toLong(claims.get("roleId")));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(StringUtil.toLong(claims.get("userId")),
                        StringUtil.toString(claims.get("username")), "NO_SESSION_AUTHENTICATION", true, authorities),
                null, authorities);
        // authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
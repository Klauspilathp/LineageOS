package top.d7c.springboot.client.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.client.services.flowable.impl.FlowableSecurityUserImpl;

/**
 * @Title: FlowableOncePerRequestFilter
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午12:57:20
 * @Description: 在前置过滤器中加入认证信息。
 */
@Component
@WebFilter(urlPatterns = {"/app/**", "/api/**"})
public class FlowableOncePerRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(FlowableOncePerRequestFilter.class);
    private static final String PATH = "[A-Za-z0-9]+.((css)|(js)|(html)|(map)|(woff)|(png)|(jpg)|(jpeg)|(tif)|(tiff))$";
    @Autowired
    private FlowableSecurityUserImpl flowableSecurityUserImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().matches(PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtil.isEmpty(SecurityUtils.getCurrentUserId())) {
            RemoteUser user = flowableSecurityUserImpl.getRemoteUser();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getId(),
                    user.getPassword(), flowableSecurityUserImpl.getGrantedAuthoritys());
            SecurityContextHolder.getContext().setAuthentication(token);
            logger.debug("SecurityUtils.getCurrentUserId() is null.");
        }

        filterChain.doFilter(request, response);
    }

}

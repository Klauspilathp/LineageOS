package top.d7c.springboot.client.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import top.d7c.oauth2.springboot.SecurityUtil;
import top.d7c.plugins.net.tools.IPUtil;
import top.d7c.plugins.net.tools.UserAgentUtil;
import top.d7c.plugins.tools.json.SFJsonUtil;
import top.d7c.springboot.client.services.sys.SysLogAmqpService;
import top.d7c.springboot.common.dos.sys.SysLog;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * @Title: SysLogAspect
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月16日 下午3:41:38
 * @Description: 系统日志记录
 */
@Aspect
@Component
public class SysLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    /**
     * amqp 方式实现的日志插入服务实现
     */
    @Resource(name = "sysLogAmqpServiceImpl")
    private SysLogAmqpService sysLogAmqpService;

    @Pointcut("execution(* top.d7c.springboot.client.controllers..*Controller.*(..))")
    // @Pointcut("execution(* top.d7c.springboot.client.controllers.*.*Controller.*(..))")
    public void sysLogAspect() {

    }

    /**
     * 准备执行业务逻辑
     */
    @Before("sysLogAspect()")
    public void before(JoinPoint joinPoint) {
        logger.debug("准备执行业务逻辑...");
    }

    /**
     * 调用核心业务逻辑
     */
    @Around("sysLogAspect()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("开始调用核心业务逻辑...");
        // 被拦截方法的返回值
        return joinPoint.proceed();
    }

    /**
     * 业务逻辑执行结束
     */
    @After("sysLogAspect()")
    public void after(JoinPoint joinPoint) {
        logger.debug("业务逻辑执行结束...");
    }

    /**
     * 核心业务调用正常退出
     */
    @AfterReturning(value = "sysLogAspect()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.debug("核心业务调用正常退出...");
        doLog(joinPoint, null);
    }

    /**
     * 核心业务调用异常退出
     */
    @AfterThrowing(value = "sysLogAspect()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) throws Throwable {
        logger.debug("核心业务调用异常退出...");
        doLog(joinPoint, throwable);
    }

    public void doLog(JoinPoint joinPoint, Throwable throwable) {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) { // 没有登录不记录日志
            return;
        }
        logger.debug("开始插入日志...");
        SysLog log = new SysLog();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        log.setRequestUri(request.getServletPath());
        log.setRequestMethod(request.getMethod());
        log.setRequestParams(SFJsonUtil.beanToJson(request.getParameterMap()));
        log.setExceptionInfo(throwable == null ? null : throwable.getMessage());
        log.setRemoteAddr(IPUtil.getIpAddr(request));
        log.setUserAgent(request.getHeader("user-agent"));
        UserAgent userAgent = UserAgentUtil.getUserAgent(request);
        log.setDeviceName(userAgent.getOperatingSystem().getName());
        log.setBrowserName(userAgent.getBrowser().getName());
        log.setAddUser(userId);
        sysLogAmqpService.insertSysLog(log, joinPoint.getSignature().getName());
        logger.debug("插入日志成功...");
    }

}
package com.gnol.springboot.client.controllers.sys;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnol.plugins.tools.code.VerifyCodeUtil;
import com.gnol.plugins.tools.code.captcha.Captcha;
import com.gnol.plugins.tools.code.captcha.GifCaptcha;
import com.gnol.plugins.tools.code.captcha.SpecCaptcha;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.client.config.GnolConstant;
import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: VerifyCodeController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:42:16
 * @Description: gnol 系统获取验证码 Controller
 */
@Controller
public class VerifyCodeController extends WebBaseController {
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    /**
     * @Title: verifyCode
     * @author: 吴佳隆
     * @data: 2019年6月25日 下午5:34:59
     * @Description: 获取验证码
     * @param response
     * @param request
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public void verifyCode(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpg");
        // 生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 放入 redis 中，过期时间 1 分钟
        redisService.addString(redisService.generateKey(GnolConstant.SESSION_VERIFY_CODE, request.getSession().getId()),
                60, verifyCode.toLowerCase());
        // 生成图片
        int w = 146, h = 33;
        try {
            VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: getGifCode
     * @author: 吴佳隆
     * @data: 2019年6月25日 下午6:32:07
     * @Description: 获取 gif 版验证码
     * @param response
     * @param request
     */
    @RequestMapping(value = "/gifCode", method = RequestMethod.GET)
    public void gifCode(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");
        // gif 格式动画验证码 (宽,高,位数)
        Captcha captcha = new GifCaptcha(146, 42, 4);
        // 输出
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            captcha.out(out);
            out.flush();
            // 放入 redis 中，过期时间 1 分钟
            redisService.addString(
                    redisService.generateKey(GnolConstant.SESSION_VERIFY_CODE, request.getSession().getId()), 60,
                    captcha.text().toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: pngCode
     * @author: 吴佳隆
     * @data: 2019年6月25日 下午6:35:13
     * @Description: 获取 png 版验证码
     * @param response
     * @param request
     */
    @RequestMapping(value = "/pngCode", method = RequestMethod.GET)
    public void pngCode(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpg");
        // png 格式动画验证码 (宽,高,位数)
        Captcha captcha = new SpecCaptcha(146, 33, 4);
        try {
            // 输出
            captcha.out(response.getOutputStream());
            // 放入 redis 中，过期时间 1 分钟
            redisService.addString(
                    redisService.generateKey(GnolConstant.SESSION_VERIFY_CODE, request.getSession().getId()), 60,
                    captcha.text().toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.gnol.springboot.client.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.tools.file.FileUtil;

/**
 * @Title: WebBaseController
 * @Package: com.gnol.springboot.client.controllers
 * @author: 吴佳隆
 * @date: 2019年12月11日 上午8:21:02
 * @Description: WEB 页面基础 Controller
 */
public class WebBaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 操作 KEY
     */
    public static final String OPERATE_KEY = "operate";
    /**
     * 操作结果 KEY
     */
    public static final String OPERATE_RESULT_KEY = "operateResult";
    /**
     * 操作信息 KEY
     */
    public static final String OPERATE_INFO_KEY = "operateInfo";
    /**
     * 成功消息值
     */
    public static final String OPERATE_SUCCEED = "succeed";
    /**
     * 失败消息值
     */
    public static final String OPERATE_FAILED = "failed";

    /**
     * @Title: getModelAndView
     * @author: 吴佳隆
     * @data: 2019年6月16日 下午12:13:40
     * @Description: 获取 ModelAndView
     * @return ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * @Title: getPage
     * @author: 吴佳隆
     * @data: 2019年6月13日 下午10:08:58
     * @Description: 获取页面分页数据对象
     * @return Page<PageData>
     */
    public Page<PageData> getPage() {
        return new Page<PageData>(this.getPageData());
    }

    /**
     * @Title: operateInfo
     * @author: 吴佳隆
     * @data: 2020年4月13日 下午2:41:45
     * @Description: 设置保存结果
     * @param result        com.gnol.plugins.core.PageResult 对象
     * @param operateInfo   操作信息
     * @return PageResult   com.gnol.plugins.core.PageResult 对象
     */
    @SuppressWarnings("unchecked")
    public PageResult operateInfo(PageResult result, String operateInfo) {
        if (result == null) {
            return result;
        }
        Map<String, Object> extData = (Map<String, Object>) result.getExtData();
        if (extData == null) {
            extData = new HashMap<String, Object>();
        }
        if (result.isOk()) {
            extData.put(OPERATE_RESULT_KEY, OPERATE_SUCCEED);
        } else {
            extData.put(OPERATE_RESULT_KEY, OPERATE_FAILED);
        }
        extData.put(OPERATE_INFO_KEY, operateInfo);
        return result.setExtData(extData);

    }

    /**
     * @Title: operateInfo
     * @author: 吴佳隆
     * @data: 2020年4月11日 上午9:48:30
     * @Description: 设置成功操作内容
     * @param mv            org.springframework.web.servlet.ModelAndView 对象
     * @param result        com.gnol.plugins.core.PageResult 对象
     * @return ModelAndView
     */
    public ModelAndView operateInfo(ModelAndView mv, PageResult result) {
        if (mv == null) {
            mv = this.getModelAndView();
        }
        mv.setViewName("common/save_result");
        if (result != null && result.isOk()) {
            return mv.addObject(OPERATE_RESULT_KEY, OPERATE_SUCCEED).addObject(OPERATE_INFO_KEY,
                    result.getMessage() == null ? "操作成功！" : result.getMessage());
        }
        return mv.addObject(OPERATE_RESULT_KEY, OPERATE_FAILED).addObject(OPERATE_INFO_KEY,
                result == null ? "操作失败！" : result.getMessage() == null ? "操作失败！" : result.getMessage());
    }

    /**
     * @Title: operate
     * @author: 吴佳隆
     * @data: 2020年4月11日 上午9:49:20
     * @Description: 设置消息
     * @param mv            org.springframework.web.servlet.ModelAndView 对象
     * @param operate       操作内容
     * @return ModelAndView
     */
    public ModelAndView operate(ModelAndView mv, Object operate) {
        if (mv == null) {
            mv = this.getModelAndView();
        }
        return mv.addObject(OPERATE_KEY, operate);
    }

    /**
     * @Title: operate
     * @author: 吴佳隆
     * @data: 2020年4月11日 上午9:52:56
     * @Description: 设置操作值
     * @param mv                org.springframework.web.servlet.ModelAndView 对象
     * @param operate           操作内容
     * @param default_operate   当操作内容为空时默认的操作内容
     * @return ModelAndView
     */
    public ModelAndView operate(ModelAndView mv, Object operate, Object default_operate) {
        if (mv == null) {
            mv = this.getModelAndView();
        }
        if (StringUtil.isNotBlank(operate)) {
            return mv.addObject(OPERATE_KEY, operate);
        }
        return mv.addObject(OPERATE_KEY, default_operate);
    }

    /**
     * @Title: getEmptyPageData
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午2:57:47
     * @Description: 获取一个新的、空的 PageData 对象
     * @return PageData
     */
    public PageData getEmptyPageData() {
        return new PageData();
    }

    /**
     * @Title: getPageData
     * @author: 吴佳隆
     * @data: 2019年6月13日 下午10:08:51
     * @Description: 获取页面数据对象，此处不能直接使用 WebPageData 进行数据封装，因为 HttpServletRequest 没有实现进行序列化接口
     * @return PageData
     */
    @SuppressWarnings("unchecked")
    public PageData getPageData() {
        PageData pd = new PageData();
        HttpServletRequest request = this.getRequest();
        Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            pd.put(entry.getKey(), entry.getValue().length == 0 ? null
                    : entry.getValue().length == 1 ? entry.getValue()[0] : entry.getValue());
        }
        // 以上两种方式不能获取 POST 请求体中的参数，request.getInputStream() 和 request.getReader() 可以获取，但仅能调用其中一个方法读取一次
        String contentType = request.getContentType();
        if (contentType != null) {
            int semicolon = contentType.indexOf(StringUtil.SEMICOLON);
            if (semicolon >= 0) {
                contentType = contentType.substring(0, semicolon).trim();
            } else {
                contentType = contentType.trim();
            }
            contentType = contentType.toLowerCase();
            if ("multipart/form-data".startsWith(contentType)) {
                /*// 在调用方法上将 HttpServletRequest 转成 MultipartHttpServletRequest 对参数进行处理
                CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
                MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);*/
                return pd;
            } else {
                // String encoding = request.getCharacterEncoding();
                StringBuffer data = new StringBuffer();
                try {
                    String line = null;
                    BufferedReader reader = request.getReader();
                    while (null != (line = reader.readLine())) {
                        data.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return pd;
                }
                if (StringUtil.isBlank(data)) {
                    return pd;
                }
                if ("application/x-www-form-urlencoded".startsWith(contentType)) {
                    String[] args = data.toString().split(StringUtil.AMPERSAND);
                    for (String arg : args) {
                        String[] kvs = StringUtil.splitByWholeSeparator(arg, StringUtil.EQUAL, 2);
                        String value = kvs[1];
                        if (value.startsWith(StringUtil.PRE_BRACKET)) {
                            try {
                                pd.put(kvs[0], JSONObject.parseArray(value).toArray());
                            } catch (Exception e) {
                                pd.put(kvs[0], value);
                            }
                        } else if (value.startsWith(StringUtil.PRE_BRACE)) {
                            try {
                                pd.put(kvs[0], JSONObject.parseObject(value, HashMap.class));
                            } catch (Exception e) {
                                pd.put(kvs[0], value);
                            }
                        } else {
                            pd.put(kvs[0], value);
                        }
                    }
                } else {
                    // Map 没有实现序列化接口，HashMap 实现了序列化接口，序列化的对象才可以实现对象的深拷贝。
                    pd.putAll(JSONObject.parseObject(data.toString(), HashMap.class));
                }
            }
        }
        return pd;
    }

    /**
     * @Title: getRequest
     * @author: 吴佳隆
     * @data: 2019年6月9日 下午5:38:52
     * @Description: 获取 HttpServletRequest 对象
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @Title: response
     * @author: 吴佳隆
     * @data: 2019年6月9日 下午5:52:04
     * @Description: 使用 response 返回数据到前端
     * @param response  javax.servlet.http.HttpServletResponse 对象
     * @param status    输出状态
     * @param message   输出消息
     * @throws IOException
     */
    public void response(HttpServletResponse response, int status, String message) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println(PageResult.build(status, message).toString());
        // response.setStatus(status);
        response.flushBuffer();
    }

    /**
     * @Title: response
     * @author: 吴佳隆
     * @data: 2020年5月23日 上午9:22:48
     * @Description: 使用 response 输出文件到前端
     * @param response      javax.servlet.http.HttpServletResponse 对象
     * @param fileName      输出文件名
     * @param attachment    是否弹出下载框
     * @throws IOException
     */
    public void response(HttpServletResponse response, String fileName, boolean attachment) throws IOException {
        if (StringUtil.isBlank(fileName)) {
            fileName = "template.txt";
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType(FileUtil.getFileContentType(fileName));
        if (attachment) {
            // 指定文件名
            try {
                response.setHeader("content-disposition",
                        "attachment; filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                response.setHeader("content-disposition", "attachment; filename*=UTF-8''" + fileName);
            }
        }
        response.setHeader("access-control-allow-origin", "*");
        response.flushBuffer();
    }

    /**
     * @Title: getSession
     * @author: 吴佳隆
     * @data: 2019年6月9日 下午5:52:40
     * @Description: 获取 HttpSession 对象
     * @return HttpSession
     */
    public HttpSession getSession() {
        return this.getRequest().getSession();
    }

    public void beforeLog(Object msg) {
        logger.info("{} START =======> {}", this.getClass().getName(), msg);
    }

    public void afterLog(Object msg) {
        logger.info("{} END =======> {}", this.getClass().getName(), msg);
    }

}
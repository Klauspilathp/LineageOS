package com.gnol.springboot.client.tools;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.enums.HttpStatus;
import com.gnol.plugins.core.enums.RequestMethodEnum;
import com.gnol.plugins.net.http.HttpRequestConfig;
import com.gnol.plugins.net.tools.RequestUtil;
import com.gnol.plugins.tools.json.SFJsonUtil;
import com.gnol.springboot.client.config.SingleWebSecurityConfiguration;

/**
 * @Title: BaiduShortUrl
 * @Package: com.gnol.springboot.client.tools
 * @author: 吴佳隆
 * @date: 2020年7月24日 下午5:34:21
 * @Description: 百度生成短 url，参考 https://dwz.cn/
 */
public class BaiduShortUrl {
    private static final Logger logger = LoggerFactory.getLogger(SingleWebSecurityConfiguration.class);
    private static String TOKEN = "f0eeb6a946116442114c55f9441d2e4d";

    /**
     * @Title: createShortUrl
     * @author: 吴佳隆
     * @data: 2020年7月24日 下午5:49:13
     * @Description: 生成百度短链接
     * @param LongUrl           长链接
     * @param TermOfValidity    有效期，"long-term"：长期，"1-year"：1年
     * @return String           短链接
     */
    public static String createShortUrl(String LongUrl, String TermOfValidity) {
        HttpRequestConfig request = new HttpRequestConfig();
        request.setRequestMethod(RequestMethodEnum.POST);
        request.setUrl("https://dwz.cn/api/v3/short-urls");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Dwz-Token", TOKEN);
        headers.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.put("Content-Language", "zh");
        request.setHeaders(headers);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("LongUrl", "utf-8");
        params.put("TermOfValidity", "true");
        request.setParams(params);
        PageResult result = RequestUtil.byHttp(request);
        if (HttpStatus.equalValue(HttpStatus.HS_200, result.getStatus())) {

            return null;
        }
        Map<String, Object> data = SFJsonUtil.jsonToMap(result.getData());
        logger.debug("请求失败，Code：{}，ErrMsg：{}", data.get("Code"), data.get("ErrMsg"));
        return StringUtil.EMPTY;
    }

    public static void main(String[] args) {
        System.out.println(createShortUrl("www.baidu.com", "1-year"));
    }

}
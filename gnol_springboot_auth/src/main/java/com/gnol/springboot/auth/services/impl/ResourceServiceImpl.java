package com.gnol.springboot.auth.services.impl;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.springboot.auth.services.ResourceService;

import io.jsonwebtoken.Claims;

/**
 * @Title: ResourceServiceImpl
 * @Package: com.gnol.springboot.auth.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:38:14
 * @Description: 访问资源实现
 */
@Service("resourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {

    @Override
    public boolean isPermitted(PageData pd, Claims claims) {
        String servlet_path = pd.getString("servlet_path");
        return servlet_path == null;
    }

}
package com.gnol.springboot.auth.services.impl;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.springboot.auth.services.ResourceService;

/**
 * @Title: MenuResourceServiceImpl
 * @Package: com.gnol.springboot.auth.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:38:14
 * @Description: 菜单资源实现
 */
@Service("menuResourceServiceImpl")
public class MenuResourceServiceImpl implements ResourceService {

    @Override
    public boolean isPermitted(PageData pd) {
        String servlet_path = pd.getString("servlet_path");
        return servlet_path == null;
    }

}
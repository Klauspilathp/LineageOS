package com.gnol.springboot.auth.services.impl;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.auth.config.GnolConstant;
import com.gnol.springboot.auth.services.ResourceService;
import com.gnol.springboot.common.enums.auth.AuthTypeEnum;

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
        if (StringUtil.isBlank(servlet_path)) {
            return false;
        }
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(claims.get(GnolConstant.AUTH_TYPE));
        if (authTypeEnum == null) {
            return false;
        }
        boolean isAuth = false;
        switch (authTypeEnum) {
            case WEB:

                break;
            case WAP:

                break;
            case MOBILE:

                break;
        }
        return isAuth;
    }

}
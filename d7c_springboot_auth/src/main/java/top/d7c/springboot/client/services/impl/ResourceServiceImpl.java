package top.d7c.springboot.client.services.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.StringUtil;
import top.d7c.springboot.client.config.D7cConstant;
import top.d7c.springboot.client.daos.sys.ExtSysMenuDao;
import top.d7c.springboot.client.services.ResourceService;
import top.d7c.springboot.common.enums.auth.AuthTypeEnum;

import io.jsonwebtoken.Claims;

/**
 * @Title: ResourceServiceImpl
 * @Package: top.d7c.springboot.client.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:38:14
 * @Description: 访问资源实现
 */
@Service("resourceServiceImpl")
public class ResourceServiceImpl implements ResourceService {
    /**
     * d7c 系统菜单表扩展 Dao
     */
    @Resource(name = "extSysMenuDao")
    private ExtSysMenuDao sysMenuDao;

    @Override
    public boolean isPermitted(PageData pd, Claims claims) {
        String servlet_path = pd.getString("servlet_path");
        if (StringUtil.isBlank(servlet_path)) {
            return false;
        }
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(claims.get(D7cConstant.AUTH_TYPE));
        if (authTypeEnum == null) {
            return false;
        }
        boolean isAuth = false;
        switch (authTypeEnum) {
            case WEB:
                Set<String> interfaces = sysMenuDao.listInterfaceByRoleId(StringUtil.toLong(claims.get("resourceId")));
                if (interfaces != null) {
                    isAuth = interfaces.contains(servlet_path);
                }
                break;
            case WAP:

                break;
            case MOBILE:

                break;
        }
        return isAuth;
    }

}
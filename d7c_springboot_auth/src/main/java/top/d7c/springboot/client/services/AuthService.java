package top.d7c.springboot.client.services;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;

/**
 * @Title: AuthService
 * @Package: top.d7c.springboot.client.services
 * @author: 吴佳隆
 * @date: 2020年6月28日 上午11:47:13
 * @Description: 授权服务接口
 */
public interface AuthService {

    /**
     * @Title: login
     * @author: 吴佳隆
     * @data: 2020年6月28日 下午4:33:59
     * @Description: 认证授权
     * @param pd
     * @return PageResult
     */
    PageResult login(PageData pd);

    /**
     * @Title: validate
     * @author: 吴佳隆
     * @data: 2020年6月28日 下午4:33:56
     * @Description: 验证
     * @param pd
     * @return PageResult
     */
    PageResult validate(PageData pd);

    /**
     * @Title: logout
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:20:43
     * @Description: 注销授权
     * @param pd
     * @return PageResult
     */
    PageResult logout(PageData pd);

}
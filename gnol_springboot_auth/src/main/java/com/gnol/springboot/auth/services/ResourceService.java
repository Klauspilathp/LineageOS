package com.gnol.springboot.auth.services;

import com.gnol.plugins.core.PageData;

/**
 * @Title: ResourceService
 * @Package: com.gnol.springboot.auth.services
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:37:42
 * @Description: 资源服务接口
 */
public interface ResourceService {

    /**
     * @Title: isPermitted
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:58:34
     * @Description: 是否有权限
     * @param pd
     * @return boolean
     */
    boolean isPermitted(PageData pd);

}
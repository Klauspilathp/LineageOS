package com.gnol.springboot.client.services.security;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.dos.security.SecurityKey;

/**
 * @Title: SecurityKeyService
 * @Package: com.gnol.springboot.client.services.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: gnol 系统安全模块_用户或系统密钥 Service
 */
public interface SecurityKeyService extends BaseService<SecurityKey, Long> {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 根据条件分页查询gnol 系统安全模块_用户或系统密钥列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertKey
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 新增gnol 系统安全模块_用户或系统密钥
     * @param pd
     * @return PageResult
     */
    PageResult insertKey(PageData pd);

    /**
     * @Title: updateKey
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 修改gnol 系统安全模块_用户或系统密钥
     * @param pd
     * @return PageResult
     */
    PageResult updateKey(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 软删除gnol 系统安全模块_用户或系统密钥
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

}
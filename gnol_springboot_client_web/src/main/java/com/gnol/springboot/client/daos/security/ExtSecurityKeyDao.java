package com.gnol.springboot.client.daos.security;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;

/**
 * @Title: ExtSecurityKeyDao
 * @Package: com.gnol.springboot.client.daos.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: gnol 系统安全模块_用户或系统密钥扩展 Dao
 */
@Repository(value = "extSecurityKeyDao")
public interface ExtSecurityKeyDao {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 根据条件分页查询gnol 系统安全模块_用户或系统密钥列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年07月20日 12:02:41
     * @Description: 根据gnol 系统安全模块_用户或系统密钥编号软删除gnol 系统安全模块_用户或系统密钥
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

}
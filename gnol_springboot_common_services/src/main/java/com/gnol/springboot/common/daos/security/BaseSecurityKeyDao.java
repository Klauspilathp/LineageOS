package com.gnol.springboot.common.daos.security;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.dos.security.SecurityKey;

/**
 * @Title: BaseSecurityKeyDao
 * @Package: com.gnol.springboot.common.daos.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: gnol 系统安全模块_用户或系统密钥基础 Dao
 */
@Repository(value = "baseSecurityKeyDao")
public interface BaseSecurityKeyDao extends BaseDao<SecurityKey, Long> {

}
package top.d7c.springboot.common.daos.security;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.security.SecurityKey;

/**
 * @Title: BaseSecurityKeyDao
 * @Package: top.d7c.springboot.common.daos.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: d7c 系统安全模块_用户或系统密钥基础 Dao
 */
@Repository(value = "baseSecurityKeyDao")
public interface BaseSecurityKeyDao extends BaseDao<SecurityKey, Long> {

}
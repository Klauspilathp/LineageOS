package com.gnol.springboot.client.daos.security;

import org.springframework.stereotype.Repository;

import com.gnol.springboot.common.dos.security.SecurityKey;

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
     * @Title: getPrivateKey
     * @author: 吴佳隆
     * @data: 2020年7月20日 下午12:33:17
     * @Description: 根据 appid 查询私钥
     * @param appid         应用主键
     * @return SecurityKey
     */
    SecurityKey getPrivateKey(String appid);

    /**
     * @Title: getPublicKey
     * @author: 吴佳隆
     * @data: 2020年7月20日 下午12:33:17
     * @Description: 根据 appid 查询公钥
     * @param appid         应用主键
     * @return SecurityKey
     */
    SecurityKey getPublicKey(String appid);

}
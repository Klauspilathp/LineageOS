package top.d7c.springboot.client.daos.sys;

import org.springframework.stereotype.Repository;

import top.d7c.springboot.common.dos.sys.SysUser;

/**
 * @Title: ExtSysUserDao
 * @Package: top.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统_用户表扩展 Dao
 */
@Repository(value = "extSysUserDao")
public interface ExtSysUserDao {

    /**
     * @Title: getSysUserByUserAccount
     * @author: 吴佳隆
     * @data: 2019年6月20日 下午7:11:13
     * @Description: 根据用户名查询用户
     * @param userAccount
     * @return SysUser
     */
    SysUser getSysUserByUserAccount(String userAccount);

    /**
     * @Title: updateByLogin
     * @author: 吴佳隆
     * @data: 2019年7月4日 下午3:25:58
     * @Description: 更新登录状态
     * @param SysUser
     * @return int
     */
    int updateByLogin(SysUser user);

}
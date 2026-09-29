package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.springboot.common.pojos.sys.SysUser;

/**
 * @Title: ExtSysUserDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统_用户表扩展 Dao
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

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询用户列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: getUserInfoByUserId
     * @author: 吴佳隆
     * @data: 2020年4月18日 上午10:37:52
     * @Description: 根据用户编号查询该用户一些信息
     * @param userId
     * @return PageData
     */
    PageData getUserInfoByUserId(Long userId);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月20日 下午4:02:45
     * @Description: 根据用户编号软删除用户
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月13日 上午10:37:34
     * @Description: 判断数据中账号、手机号、邮箱是否已经存在并且不是当前用户
     * @param condition
     * @return int
     */
    int hasExist(String condition);

    /**
     * @Title: updateBatchStatus
     * @author: 吴佳隆
     * @data: 2020年4月20日 下午4:59:22
     * @Description: 批量软删除用户
     * @param pd
     * @return int
     */
    int updateBatchStatus(PageData pd);

    /**
     * @Title: updateAvatar
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午12:20:57
     * @Description: 更新用户头像
     * @param pd
     * @return int
     */
    int updateAvatar(PageData pd);

    /**
     * @Title: updatePassword
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午6:41:26
     * @Description: 更新密码
     * @param pd
     * @return int
     */
    int updatePassword(PageData pd);

}
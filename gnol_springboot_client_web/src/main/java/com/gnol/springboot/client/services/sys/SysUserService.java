package com.gnol.springboot.client.services.sys;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.dos.sys.SysUser;

/**
 * @Title: SysUserService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统_用户表 Service
 */
public interface SysUserService extends BaseService<SysUser, Long> {

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
     * @return PageResult
     */
    PageResult updateByLogin(SysUser user);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询用户列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertUser
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:39:12
     * @Description: 新增用户
     * @param pd
     * @return PageResult
     */
    PageResult insertUser(PageData pd);

    /**
     * @Title: updateUser
     * @author: 吴佳隆
     * @data: 2020年4月12日 上午10:54:14
     * @Description: 修改用户
     * @param pd
     * @return PageResult
     */
    PageResult updateUser(PageData pd);

    /**
     * @Title: saveVerify
     * @author: 吴佳隆
     * @data: 2020年4月18日 上午10:10:07
     * @Description: 用户保存权限验证
     * @param pd
     * @return PageResult
     */
    PageResult saveVerify(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月12日 上午11:34:24
     * @Description: 软删除用户
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

    /**
     * @Title: delVerify
     * @author: 吴佳隆
     * @data: 2020年4月18日 上午10:10:07
     * @Description: 用户删除权限验证
     * @param pd
     * @return PageResult
     */
    PageResult delVerify(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月13日 上午7:54:52
     * @Description: 判断数据中账号、手机号、邮箱是否已经存在并且不是当前用户
     * @param pd
     * @return PageResult
     */
    PageResult hasExist(PageData pd);

    /**
     * @Title: updateBatchStatus
     * @author: 吴佳隆
     * @data: 2020年4月12日 上午11:53:27
     * @Description: 批量软删除用户
     * @param pd
     * @return PageResult
     */
    PageResult updateBatchStatus(PageData pd);

    /**
     * @Title: updateAvatar
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午12:21:52
     * @Description: 更新用户头像
     * @param pd
     * @return PageResult
     */
    PageResult updateAvatar(PageData pd);

    /**
     * @Title: updateMyInfo
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午4:42:56
     * @Description: 编辑自己信息
     * @param pd
     * @return PageResult
     */
    PageResult updateMyInfo(PageData pd);

    /**
     * @Title: updatePassword
     * @author: 吴佳隆
     * @data: 2020年5月1日 下午6:42:06
     * @Description: 更新密码
     * @param pd
     * @return PageResult
     */
    PageResult updatePassword(PageData pd);

}
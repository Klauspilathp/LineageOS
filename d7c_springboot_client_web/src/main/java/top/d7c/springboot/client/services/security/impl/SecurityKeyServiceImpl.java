package top.d7c.springboot.client.services.security.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.context.AbstractBaseService;
import top.d7c.springboot.client.daos.security.ExtSecurityKeyDao;
import top.d7c.springboot.client.services.security.SecurityKeyService;
import top.d7c.springboot.common.daos.security.BaseSecurityKeyDao;
import top.d7c.springboot.common.dos.security.SecurityKey;
import top.d7c.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SecurityKeyServiceImpl
 * @Package: top.d7c.springboot.client.services.security.impl
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: d7c 系统安全模块_用户或系统密钥 Service 实现
 */
@Service(value = "securityKeyServiceImpl")
public class SecurityKeyServiceImpl extends AbstractBaseService<BaseSecurityKeyDao, SecurityKey, Long>
        implements SecurityKeyService {
    /**
     * d7c 系统安全模块_用户或系统密钥扩展 Dao
     */
    @Resource(name = "extSecurityKeyDao")
    private ExtSecurityKeyDao securityKeyDao;

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(securityKeyDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertKey(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        String appid = pd.getString("appid");
        if (StringUtil.isBlank(appid)) {
            return PageResult.error("d7c 系统安全模块_用户或系统密钥编号不能为空！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }
        // TODO
        SecurityKey securityKey = new SecurityKey();
        securityKey.setAppid(appid);
        // TODO
        securityKey.setRemark(pd.getString("remark"));
        securityKey.setAddUser(addUser);
        securityKey.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(securityKey);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateKey(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        String appid = pd.getString("appid");
        if (StringUtil.isBlank(appid)) {
            return PageResult.error("d7c 系统安全模块_用户或系统密钥编号不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        // TODO
        SecurityKey securityKey = new SecurityKey();
        securityKey.setAppid(appid);
        // TODO
        securityKey.setRemark(pd.getString("remark"));
        securityKey.setModifyUser(modifyUser);
        securityKey.setCheckValue(checkValue);
        int update = dao.update(securityKey);
        if (update == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long appid = pd.getLong("appid");
        if (appid == null || appid.compareTo(0L) != 1) {
            return PageResult.error("d7c 系统安全模块_用户或系统密钥编号不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = securityKeyDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("d7c 系统安全模块_用户或系统密钥删除失败！");
    }

}
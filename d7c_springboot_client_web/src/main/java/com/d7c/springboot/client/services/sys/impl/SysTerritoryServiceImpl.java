package com.d7c.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.plugins.tools.lang.VerifyUtil;
import com.d7c.springboot.client.daos.sys.ExtSysTerritoryDao;
import com.d7c.springboot.client.dtos.tree.ZTree;
import com.d7c.springboot.client.dtos.tree.ZTreeUtil;
import com.d7c.springboot.client.services.sys.SysTerritoryService;
import com.d7c.springboot.common.daos.sys.BaseSysTerritoryDao;
import com.d7c.springboot.common.dos.sys.SysTerritory;
import com.d7c.springboot.common.enums.sys.LevelEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysTerritoryServiceImpl
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:30
 * @Description: d7c系统地域 Service 实现
 */
@Service(value = "sysTerritoryServiceImpl")
public class SysTerritoryServiceImpl extends AbstractBaseService<BaseSysTerritoryDao, SysTerritory, Long>
        implements SysTerritoryService {
    /**
     * d7c系统地域扩展 Dao
     */
    @Resource(name = "extSysTerritoryDao")
    private ExtSysTerritoryDao sysTerritoryDao;

    // ZTree 异步实现方式
    @Override
    public List<ZTree> listZTreeFormTreeFrameByParentId_ASYNC(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        return sysTerritoryDao.listZTreeFormTreeFrameByParentId(parentId);
    }

    // ZTree 异步实现方式
    @Override
    public List<ZTree> listZTreeByParentId_ASYNC(Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        return sysTerritoryDao.listZTreeByParentId(parentId);
    }

    // ZTree 同步实现方式
    @Override
    public List<ZTree> listZTreeFormTreeFrameByParentId_SYNC(Long parentId) {
        // 查询出所有地域信息列表
        List<ZTree> allTerritory = sysTerritoryDao.listZTreeFormTreeFrameByParentId(null);
        if (allTerritory != null && !allTerritory.isEmpty()) {
            return new ZTreeUtil().dealZtree2_6(allTerritory, parentId);
        }
        return new ArrayList<ZTree>();
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysTerritoryDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertTerritory(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long parentId = pd.getLong("parentId");
        if (parentId == null || parentId.compareTo(0L) == -1) {
            return PageResult.error("父级地域编号不能为空！");
        }
        String territoryNameCN = pd.getString("territoryNameCN");
        if (StringUtil.isBlank(territoryNameCN)) {
            return PageResult.error("territoryNameCN 不能为空！");
        }
        String territoryCode = pd.getString("territoryCode");
        if (StringUtil.isBlank(territoryCode)) {
            return PageResult.error("territoryCode 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }
        if (!VerifyUtil.isPositiveInteger(territoryCode)) {
            return PageResult.error("地域代码必须为正整数字符串！");
        }

        // 判断地域代码是否唯一
        int hasExist = sysTerritoryDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("地域代码已存在！");
        }

        SysTerritory sysTerritory = null;
        Integer level = null; // 地域级别
        if (parentId.compareTo(0L) != 0) {
            SysTerritory parentTerritory = dao.getByKey(parentId);
            if (parentTerritory == null) {
                return PageResult.error("父级地域信息为空！");
            }
            sysTerritory = new SysTerritory();
            sysTerritory.setTerritoryId(StringUtil.toLong(territoryCode));
            Integer parentLevel = parentTerritory.getLevel();
            if (parentLevel.compareTo(LevelEnum.STATE.getKey()) != -1) {
                sysTerritory.setStateId(parentTerritory.getStateId());
                sysTerritory.setStateNameCN(parentTerritory.getStateNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.PROVINCE.getKey()) != -1) {
                sysTerritory.setProvinceId(parentTerritory.getProvinceId());
                sysTerritory.setProvinceNameCN(parentTerritory.getProvinceNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.CITY.getKey()) != -1) {
                sysTerritory.setCityId(parentTerritory.getCityId());
                sysTerritory.setCityNameCN(parentTerritory.getCityNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.COUNTY.getKey()) != -1) {
                sysTerritory.setCountyId(parentTerritory.getCountyId());
                sysTerritory.setCountyNameCN(parentTerritory.getCountyNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.STREET.getKey()) != -1) {
                sysTerritory.setStreetId(parentTerritory.getStreetId());
                sysTerritory.setStreetNameCN(parentTerritory.getStreetNameCN());
            }
            level = parentLevel.compareTo(LevelEnum.STATE.getKey()) == 0 ? LevelEnum.PROVINCE.getKey()
                    : parentLevel + 10;
            switch (LevelEnum.forKey(level)) {
                case PROVINCE:
                    sysTerritory.setProvinceId(sysTerritory.getTerritoryId());
                    sysTerritory.setProvinceNameCN(territoryNameCN);
                    break;
                case CITY:
                    sysTerritory.setCityId(sysTerritory.getTerritoryId());
                    sysTerritory.setCityNameCN(territoryNameCN);
                    break;
                case COUNTY:
                    sysTerritory.setCountyId(sysTerritory.getTerritoryId());
                    sysTerritory.setCountyNameCN(territoryNameCN);
                    break;
                case STREET:
                    sysTerritory.setStreetId(sysTerritory.getTerritoryId());
                    sysTerritory.setStreetNameCN(territoryNameCN);
                    break;
                default:
                    break;
            }
        } else {
            level = LevelEnum.STATE.getKey();
            sysTerritory = new SysTerritory();
            sysTerritory.setTerritoryId(StringUtil.toLong(territoryCode));
            sysTerritory.setStateId(sysTerritory.getTerritoryId());
            sysTerritory.setStateNameCN(territoryNameCN);
        }
        sysTerritory.setTerritoryNameCN(territoryNameCN);
        sysTerritory.setTerritoryNameEN(pd.getString("territoryNameEN"));
        sysTerritory.setTerritoryAlias(pd.getString("territoryAlias"));
        sysTerritory.setTerritoryCode(territoryCode);
        sysTerritory.setLevel(level);
        sysTerritory.setSort(sort);
        sysTerritory.setParentId(parentId);
        sysTerritory.setRemark(pd.getString("remark"));
        sysTerritory.setAddUser(addUser);
        sysTerritory.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(sysTerritory);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateTerritory(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long territoryId = pd.getLong("territoryId");
        if (territoryId == null || territoryId.compareTo(0L) != 1) {
            return PageResult.error("地域编号不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        String territoryNameCN = pd.getString("territoryNameCN");
        if (StringUtil.isBlank(territoryNameCN)) {
            return PageResult.error("territoryNameCN 不能为空！");
        }
        String territoryCode = pd.getString("territoryCode");
        if (StringUtil.isBlank(territoryCode)) {
            return PageResult.error("territoryCode 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        SysTerritory sysTerritory = new SysTerritory();
        sysTerritory.setTerritoryId(territoryId);
        sysTerritory.setTerritoryNameCN(territoryNameCN);
        sysTerritory.setTerritoryNameEN(pd.getString("territoryNameEN"));
        sysTerritory.setTerritoryAlias(pd.getString("territoryAlias"));
        sysTerritory.setTerritoryCode(territoryCode);
        sysTerritory.setSort(sort);
        sysTerritory.setRemark(pd.getString("remark"));
        sysTerritory.setModifyUser(modifyUser);
        sysTerritory.setCheckValue(checkValue);
        int update = dao.update(sysTerritory);
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
        Long territoryId = pd.getLong("territoryId");
        if (territoryId == null || territoryId.compareTo(0L) != 1) {
            return PageResult.error("地域编号不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        pd.put("parentId", territoryId);
        pd.put("status", StatusEnum.NORMAL.getKey());
        if (sysTerritoryDao.hasSon(pd) > 0) {
            return PageResult.error("请先删除子节点！");
        }
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysTerritoryDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("组织机构删除失败！");
    }

    @Override
    public PageResult hasExist(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("territoryCode") == null) {
            return PageResult.ok();
        }
        int hasExist = sysTerritoryDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("地域代码已存在！");
        }
        return PageResult.ok();
    }

    @Override
    public String getTerritoryNameCNById(Long territoryId) {
        if (territoryId == null || territoryId.compareTo(0L) != 1) {
            return null;
        }
        return sysTerritoryDao.getTerritoryNameCNById(territoryId);
    }

}
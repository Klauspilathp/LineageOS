package top.d7c.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.context.AbstractBaseService;
import top.d7c.springboot.client.daos.sys.ExtSysDictDao;
import top.d7c.springboot.client.dtos.tree.ZTree;
import top.d7c.springboot.client.services.sys.SysDictService;
import top.d7c.springboot.common.daos.sys.BaseSysDictDao;
import top.d7c.springboot.common.dos.sys.SysDict;
import top.d7c.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysDictServiceImpl
 * @Package: top.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: d7c 系统字典 Service 实现
 */
@Service(value = "sysDictServiceImpl")
public class SysDictServiceImpl extends AbstractBaseService<BaseSysDictDao, SysDict, Long> implements SysDictService {
    /**
     * d7c 系统字典扩展 Dao
     */
    @Resource(name = "extSysDictDao")
    private ExtSysDictDao sysDictDao;

    @Override
    public List<ZTree> listZTreeFormTreeFrameByParentId_ASYNC(PageData pd) {
        if (pd == null) {
            pd = new PageData();
        }
        if (pd.getLong("parentId") == null) {
            pd.put("parentId", 0L);
        }
        Locale locale = Locale.getDefault();
        pd.put("language", locale.getCountry());
        return sysDictDao.listZTreeFormTreeFrameByParentId(pd);
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(sysDictDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertDict(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long parentId = pd.getLong("parentId");
        if (parentId == null || parentId.compareTo(0L) == -1) {
            return PageResult.error("父级字典编号不能为空！");
        }
        String dictType = pd.getString("dictType");
        if (StringUtil.isBlank(dictType)) {
            return PageResult.error("dictType 不能为空！");
        }
        String dictKey = pd.getString("dictKey");
        if (StringUtil.isBlank(dictKey)) {
            return PageResult.error("dictKey 不能为空！");
        }
        String dictCNValue = pd.getString("dictCNValue");
        if (StringUtil.isBlank(dictCNValue)) {
            return PageResult.error("dictCNValue 不能为空！");
        }
        String dictENValue = pd.getString("dictENValue");
        if (StringUtil.isBlank(dictENValue)) {
            return PageResult.error("dictENValue 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }

        // 验证 dictType、dictKey 是否重复
        int hasExist = sysDictDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("字典类型已存在！");
        }

        SysDict sysDict = new SysDict();
        sysDict.setParentId(parentId);
        sysDict.setDictType(dictType);
        sysDict.setDictKey(dictKey);
        sysDict.setDictCNValue(dictCNValue);
        sysDict.setDictENValue(dictENValue);
        sysDict.setSort(sort);
        sysDict.setRemark(pd.getString("remark"));
        sysDict.setAddUser(addUser);
        sysDict.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(sysDict);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateDict(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long dictId = pd.getLong("dictId");
        if (dictId == null || dictId.compareTo(0L) == -1) {
            return PageResult.error("字典编号不能为空！");
        }
        String dictType = pd.getString("dictType");
        if (StringUtil.isBlank(dictType)) {
            return PageResult.error("dictType 不能为空！");
        }
        String dictKey = pd.getString("dictKey");
        if (StringUtil.isBlank(dictKey)) {
            return PageResult.error("dictKey 不能为空！");
        }
        String dictCNValue = pd.getString("dictCNValue");
        if (StringUtil.isBlank(dictCNValue)) {
            return PageResult.error("dictCNValue 不能为空！");
        }
        String dictENValue = pd.getString("dictENValue");
        if (StringUtil.isBlank(dictENValue)) {
            return PageResult.error("dictENValue 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }

        // 验证 dictType、dictKey 是否重复
        int hasExist = sysDictDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("字典类型已存在！");
        }

        SysDict sysDict = new SysDict();
        sysDict.setDictId(dictId);
        sysDict.setDictKey(dictKey);
        sysDict.setDictCNValue(dictCNValue);
        sysDict.setDictENValue(dictENValue);
        sysDict.setSort(sort);
        sysDict.setRemark(pd.getString("remark"));
        sysDict.setModifyUser(modifyUser);
        sysDict.setCheckValue(checkValue);
        int update = dao.update(sysDict);
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
        Long dictId = pd.getLong("dictId");
        if (dictId == null || dictId.compareTo(0L) != 1) {
            return PageResult.error("字典编号不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        pd.put("parentId", dictId);
        pd.put("status", StatusEnum.NORMAL.getKey());
        if (sysDictDao.hasSon(pd) > 0) {
            return PageResult.error("请先删除子节点！");
        }
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysDictDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("字典删除失败！");
    }

    @Override
    public PageResult hasExist(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("dictType") == null || pd.get("dictKey") == null) {
            return PageResult.ok();
        }
        int hasExist = sysDictDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("字典类型已存在！");
        }
        return PageResult.ok();
    }

    @Override
    public List<PageData> listDictByDictType(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("dictType") == null) {
            return new ArrayList<PageData>();
        }
        Locale locale = Locale.getDefault();
        pd.put("language", locale.getCountry());
        return sysDictDao.listDictByDictType(pd);
    }

}
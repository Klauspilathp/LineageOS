package com.gnol.springboot.client.controllers.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.oauth2.spring.boot.SecurityUtil;
import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.client.services.sys.SysDictService;
import com.gnol.springboot.common.dos.sys.SysDict;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysDictController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月19日 下午5:04:11
 * @Description: gnol 系统字典 Controller
 */
@Controller
@RequestMapping(value = "/sys/dict")
public class SysDictController extends WebBaseController {
    /**
     * gnol 系统字典 Service
     */
    @Resource(name = "sysDictServiceImpl")
    private SysDictService sysDictService;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:06:40
     * @Description: 去字典管理首页
     * @return String
     */
    @GetMapping(value = "/index")
    @RolesAllowed("sys_dict:index")
    public String index() {
        return "sys/dict/dict_ztree";
    }

    /**
     * @Title: listZTreeFormTreeFrameByParentId_ASYNC
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:07:49
     * @Description: 同步字典数查询
     * @return PageResult
     */
    @RequestMapping(value = "/index")
    @RolesAllowed("sys_dict:index")
    public PageResult listZTreeFormTreeFrameByParentId_ASYNC() {
        PageData pd = this.getPageData();
        List<ZTree> zTrees = sysDictService.listZTreeFormTreeFrameByParentId_ASYNC(pd);
        return PageResult.ok(zTrees).setExtData(pd);
    }

    /**
     * @Title: listZTreeFormTreeFrameByParentId
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:08:12
     * @Description: 根据父级字典查询它的子级字典列表
     * @return PageResult
     */
    @RequestMapping(value = "/listZTreeFormTreeFrameByParentId")
    @ResponseBody
    public PageResult listZTreeFormTreeFrameByParentId() {
        PageData pd = this.getPageData();
        List<ZTree> zTrees = sysDictService.listZTreeFormTreeFrameByParentId_ASYNC(pd);
        return PageResult.ok(zTrees);
    }

    /**
     * @Title: dict_list
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:08:59
     * @Description: 去字典列表页
     * @return String
     */
    @GetMapping(value = "/listByParentIdPage")
    @RolesAllowed("sys_dict:listByParentIdPage")
    public String dict_list() {
        return "sys/dict/dict_list";
    }

    /**
     * @Title: listByParentIdPage
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:10:52
     * @Description: 根据父级字典编号分页查询子级字典列表
     * @param page
     * @return PageResult
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/listByParentIdPage")
    @RolesAllowed("sys_dict:listByParentIdPage")
    @ResponseBody
    public PageResult listByParentIdPage(Page<PageData> page) {
        PageData pd = this.getPageData();
        // 检索条件
        Object keywords = pd.remove("keywords");
        if (StringUtil.isNotBlank(keywords)) {
            pd.put("keywords", keywords.toString().trim());
        }
        Long dictId = pd.getLong("dictId");
        if (dictId == null) {
            dictId = 0L;
        }
        pd.put("parentId", dictId);
        pd.put("status", StatusEnum.NORMAL.getKey());
        page.setArgs(pd);
        // 分页列出 dictId 的所有子级字典列表
        PageResult result = sysDictService.listPDPage(page);
        if (dictId.compareTo(0L) == 1) {
            pd.putAll(sysDictService.getPDByKey(dictId));
        }
        return result;
    }

    /**
     * @Title: goAdd
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:11:18
     * @Description: 去新增字典页面
     * @return String
     */
    @GetMapping(value = "/goAdd")
    @RolesAllowed("sys_dict:goAdd")
    public String goAdd() {
        return "sys/dict/dict_edit";
    }

    /**
     * @Title: getParentDictName
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:12:16
     * @Description: 新增字典是查询父级字典信息
     * @return PageResult
     */
    @RequestMapping(value = "/getParentDictName")
    @RolesAllowed("sys_dict:goAdd")
    @ResponseBody
    public PageResult getParentDictName() {
        PageData pd = this.getPageData();
        Long parentId = pd.getLong("parentId");
        if (parentId == null || parentId.compareTo(0L) != 1) {
            parentId = 0L;
        } else {
            SysDict sysDict = sysDictService.getByKey(parentId);
            if (sysDict != null) {
                pd.put("parentDictCNValue", sysDict.getDictCNValue());
            }
        }
        pd.put("parentId", parentId);
        return PageResult.ok(pd);
    }

    /**
     * @Title: add
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:14:00
     * @Description: 新增字典
     * @return PageResult
     */
    @RequestMapping(value = "/add")
    @RolesAllowed("sys_dict:add")
    @ResponseBody
    public PageResult add() {
        PageData pd = this.getPageData();
        pd.put("addUser", SecurityUtil.getUserId());
        return sysDictService.insertDict(pd);
    }

    /**
     * @Title: goEditMenu
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:15:16
     * @Description: 去编辑字典页面
     * @return String
     */
    @GetMapping(value = "/goEdit")
    @RolesAllowed("sys_dict:goEdit")
    public String goEditMenu() {
        return "sys/dict/dict_edit";
    }

    /**
     * @Title: getParentDict
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:16:29
     * @Description: 编辑字典时查询父级字典信息
     * @return PageResult
     */
    @RequestMapping(value = "/getParentDict")
    @RolesAllowed("sys_dict:goEdit")
    @ResponseBody
    public PageResult getParentDict() {
        PageData pd = this.getPageData();
        pd = sysDictService.getPDByKey(pd.getLong("dictId"));
        if (pd != null) {
            Long parentId = pd.getLong("parentId");
            if (parentId != null && parentId.compareTo(0L) != 1) {
                SysDict sysDict = sysDictService.getByKey(parentId);
                if (sysDict != null) {
                    pd.put("parentDictCNValue", sysDict.getDictCNValue());
                }
            }
        }
        return PageResult.ok(pd);
    }

    /**
     * @Title: edit
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:17:48
     * @Description: 编辑字典信息
     * @return PageResult
     */
    @RequestMapping(value = "/edit")
    @RolesAllowed("sys_dict:edit")
    @ResponseBody
    public PageResult edit() {
        PageData pd = this.getPageData();
        pd.put("modifyUser", SecurityUtil.getUserId());
        return sysDictService.updateDict(pd);
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2020年4月12日 上午11:32:56
     * @Description: 软删除字典
     * @return PageResult
     */
    @RequestMapping(value = "/del")
    @RolesAllowed("sys_dict:del")
    @ResponseBody
    public PageResult del() {
        PageData pd = this.getPageData();
        pd.put("modifyUser", SecurityUtil.getUserId());
        PageResult result = sysDictService.updateStatus(pd);
        return operateInfo(result, result.getMessage());
    }

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月13日 上午7:55:25
     * @Description: 判断数据库中 dictType、dictKey 是否已经存在并且不是当前字典
     * @return PageResult
     */
    @RequestMapping(value = "/hasExist")
    @ResponseBody
    public PageResult hasExist() {
        PageData pd = this.getPageData();
        PageResult result = sysDictService.hasExist(pd);
        return operateInfo(result, result.getMessage());
    }

}
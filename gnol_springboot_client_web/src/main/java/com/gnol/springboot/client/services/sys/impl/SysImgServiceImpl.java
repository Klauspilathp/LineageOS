package com.gnol.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.fastDFS.spring.boot.autoconfigure.FastDFService;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.springboot.client.daos.sys.ExtSysImgDao;
import com.gnol.springboot.client.services.sys.SysImgService;
import com.gnol.springboot.common.daos.sys.BaseSysImgDao;
import com.gnol.springboot.common.dos.sys.SysImg;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysImgServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: gnol 系统_图片管理 Service 实现
 */
@Service(value = "sysImgServiceImpl")
public class SysImgServiceImpl extends AbstractBaseService<BaseSysImgDao, SysImg, Long> implements SysImgService {
    /**
     * gnol 系统_图片管理扩展 Dao
     */
    @Resource(name = "extSysImgDao")
    private ExtSysImgDao sysImgDao;
    /**
     * fastDFS 服务实现
     */
    @Resource(name = "fastDFServiceImpl")
    private FastDFService fastDFService;

    @Override
    public List<PageData> listByImgType(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("imgType") == null) {
            return new ArrayList<PageData>();
        }
        pd.put(SysImg.M.status, StatusEnum.NORMAL.getKey());
        return sysImgDao.listByImgType(pd);
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        pd.put(SysImg.M.status, StatusEnum.DELETE.getKey());
        int count = sysImgDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("图片删除失败！");
    }

    @Override
    public PageResult delBatch(PageData pd) {
        // 先查出要删除的图片列表
        // 再去图片服务器删除图片
        fastDFService.deleteFile(null);
        return PageResult.ok();
    }

}
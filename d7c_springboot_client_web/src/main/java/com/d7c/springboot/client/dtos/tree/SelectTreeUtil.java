package com.d7c.springboot.client.dtos.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: SelectTreeUtil
 * @Package: com.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月18日 下午12:05:31
 * @Description: selectTree 工具类
 */
public class SelectTreeUtil {

    /**
     * @Title: dealSelectTree
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午6:10:03
     * @Description: 根据拥有的下拉树顶级编号获取以顶级编号为顶级树的下拉树对象，列表中所有数据为同级关系
     * @param allSelectTree     全部下拉树列表
     * @param maxId             拥有的下拉树顶级编号
     * @return List<SelectTree>
     */
    public List<SelectTree> dealSelectTree(List<SelectTree> allSelectTree, Long maxId) {
        if (allSelectTree == null || allSelectTree.isEmpty()) {
            return new ArrayList<SelectTree>();
        }
        if (maxId == null || maxId.equals(0L)) {
            return allSelectTree;
        }
        List<SelectTree> result = new ArrayList<SelectTree>();
        // 先根据父编号获取顶级树对象及顶级树对象编号
        Long id = null;
        for (SelectTree selectTree : allSelectTree) {
            if (selectTree.getId().compareTo(maxId) == 0) {
                result.add(selectTree);
                id = selectTree.getId();
            }
        }
        if (id == null || id.compareTo(0L) != 1) {
            return result;
        }
        return this.recursionDealSelectTree(result, allSelectTree, id);
    }

    // 获取顶级树对象的所有子对象
    public List<SelectTree> recursionDealSelectTree(List<SelectTree> result, List<SelectTree> allSelectTree, Long id) {
        for (SelectTree selectTree : allSelectTree) {
            if (selectTree.getpId().compareTo(id) == 0) {
                result.add(selectTree);
                this.recursionDealSelectTree(result, allSelectTree, selectTree.getId());
            }
        }
        return result;
    }

}
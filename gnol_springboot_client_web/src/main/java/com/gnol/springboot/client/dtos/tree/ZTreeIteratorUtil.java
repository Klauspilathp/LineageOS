package com.gnol.springboot.client.dtos.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @Title: ZTreeUtil
 * @Package: com.gnol.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月17日 下午6:38:23
 * @Description: ZTree 同步树工具类
 */
public class ZTreeIteratorUtil {

    /**
     * @Title: dealZtree3_5
     * @author: 吴佳隆
     * @data: 2020年4月21日 上午10:23:18
     * @Description: 根据拥有的 ztree 树顶级编号获取以顶级编号为顶级树的 ztree 树对象，列表中所有数据为同级关系
     * @param allZTrees     ztree 3.6 同步树列表，父子节点为同级关系，nodes 节点无意义
     * @param maxId         顶级树的父编号
     * @return List<ZTree>
     */
    public List<ZTree> dealZtree3_5(List<ZTree> allZTrees, Long maxId) {
        if (allZTrees == null || allZTrees.isEmpty()) {
            return new ArrayList<ZTree>();
        }
        if (maxId == null || maxId.equals(0L)) {
            return allZTrees;
        }
        ListIterator<ZTree> allIterator = allZTrees.listIterator();
        ArrayList<ZTree> result = new ArrayList<ZTree>();
        // 先根据父编号获取顶级树对象及顶级树对象编号
        Long id = null;
        while (allIterator.hasNext()) {
            ZTree ztree = (ZTree) allIterator.next();
            if (ztree.getId().compareTo(maxId) == 0) {
                allIterator.remove();
                result.add(ztree);
                id = ztree.getId();
            }
        }
        if (id == null || id.compareTo(0L) != 1) {
            return result;
        }
        return this.recursionDealZTree3_5(result, allIterator, id);
    }

    public List<ZTree> recursionDealZTree3_5(List<ZTree> result, ListIterator<ZTree> allIterator, Long id) {
        if (allIterator.hasNext()) {
            while (allIterator.hasNext()) {
                ZTree ztree = (ZTree) allIterator.next();
                if (ztree.getpId().compareTo(id) == 0) {
                    allIterator.remove();
                    result.add(ztree);
                    this.recursionDealZTree3_5(result, allIterator, ztree.getId());
                }
            }
        } else {
            while (allIterator.hasPrevious()) {
                ZTree ztree = (ZTree) allIterator.previous();
                if (ztree.getpId().compareTo(id) == 0) {
                    allIterator.remove();
                    result.add(ztree);
                    this.recursionDealZTree3_5(result, allIterator, ztree.getId());
                }
            }
        }
        return result;
    }

    /**
     * @Title: dealZtree2_6
     * @author: 吴佳隆
     * @data: 2020年4月17日 下午6:42:14
     * @Description: 处理 ZTree 列表为父子级关系
     * @param allZTrees     ztree 2.6 同步树列表，要求子节点在父节点的 nodes 属性中
     * @param maxId         顶级树编号
     * @return List<ZTree>
     */
    public List<ZTree> dealZtree2_6(List<ZTree> allZTrees, Long maxId) {
        if (allZTrees == null || allZTrees.isEmpty()) {
            return allZTrees;
        }
        if (maxId == null) {
            maxId = 0L;
        }
        ListIterator<ZTree> allIterator = allZTrees.listIterator();
        if (maxId.compareTo(0L) == 1) {
            ZTree zTree = null;
            while (allIterator.hasNext()) {
                zTree = (ZTree) allIterator.next();
                if (maxId.compareTo(zTree.getId()) == 0) {
                    allIterator.remove();
                    List<ZTree> nodes = this.recursionDealZTree2_6(allIterator, maxId);
                    zTree.setNodes(nodes);
                    List<ZTree> result = new ArrayList<ZTree>();
                    result.add(zTree);
                    return result;
                }
            }
        }
        return this.recursionDealZTree2_6(allIterator, maxId);
    }

    public List<ZTree> recursionDealZTree2_6(ListIterator<ZTree> allIterator, Long parentId) {
        List<ZTree> result = new ArrayList<ZTree>();
        List<ZTree> nodes = this.getNodes(allIterator, parentId);
        for (ZTree node : nodes) {
            List<ZTree> nextZTrees = this.recursionDealZTree2_6(allIterator, node.getId());
            if (!nextZTrees.isEmpty()) {
                node.setNodes(nextZTrees);
            }
            result.add(node);
        }
        return result;
    }

    public List<ZTree> getNodes(ListIterator<ZTree> allIterator, Long parentId) {
        List<ZTree> nodes = new ArrayList<ZTree>();
        if (allIterator.hasNext()) {
            while (allIterator.hasNext()) {
                ZTree ztree = (ZTree) allIterator.next();
                if (parentId.compareTo(ztree.getpId()) == 0) {
                    allIterator.remove();
                    nodes.add(ztree);
                }
            }
        } else {
            while (allIterator.hasPrevious()) {
                ZTree ztree = (ZTree) allIterator.previous();
                if (parentId.compareTo(ztree.getpId()) == 0) {
                    allIterator.remove();
                    nodes.add(ztree);
                }
            }
        }
        return nodes;
    }

}
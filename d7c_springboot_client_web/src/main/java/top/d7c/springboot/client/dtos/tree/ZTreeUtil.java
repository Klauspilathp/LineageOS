package top.d7c.springboot.client.dtos.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ZTreeUtil
 * @Package: top.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月17日 下午6:38:23
 * @Description: ZTree 同步树工具类
 */
public class ZTreeUtil {

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
        ArrayList<ZTree> result = new ArrayList<ZTree>();
        // 先根据父编号获取顶级树对象及顶级树对象编号
        Long id = null;
        for (ZTree ztree : allZTrees) {
            if (ztree.getId().compareTo(maxId) == 0) {
                result.add(ztree);
                id = ztree.getId();
            }
        }
        if (id == null || id.compareTo(0L) != 1) {
            return result;
        }
        return this.recursionDealZTree3_5(result, allZTrees, id);
    }

    public List<ZTree> recursionDealZTree3_5(List<ZTree> result, List<ZTree> allZTrees, Long id) {
        for (ZTree ztree : allZTrees) {
            if (ztree.getpId().compareTo(id) == 0) {
                result.add(ztree);
                this.recursionDealZTree3_5(result, allZTrees, ztree.getId());
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
        if (maxId.compareTo(0L) == 1) {
            for (ZTree zTree : allZTrees) {
                if (maxId.compareTo(zTree.getId()) == 0) {
                    List<ZTree> nodes = this.recursionDealZTree2_6(allZTrees, maxId);
                    zTree.setNodes(nodes);
                    List<ZTree> result = new ArrayList<ZTree>();
                    result.add(zTree);
                    return result;
                }
            }
        }
        return this.recursionDealZTree2_6(allZTrees, maxId);
    }

    public List<ZTree> recursionDealZTree2_6(List<ZTree> allZTrees, Long parentId) {
        List<ZTree> result = new ArrayList<ZTree>();
        List<ZTree> nodes = this.getNodes(allZTrees, parentId);
        for (ZTree node : nodes) {
            List<ZTree> nextZTrees = this.recursionDealZTree2_6(allZTrees, node.getId());
            if (!nextZTrees.isEmpty()) {
                node.setNodes(nextZTrees);
            }
            result.add(node);
        }
        return result;
    }

    public List<ZTree> getNodes(List<ZTree> zTrees, Long parentId) {
        List<ZTree> nodes = new ArrayList<ZTree>();
        for (ZTree zTree : zTrees) {
            if (parentId.compareTo(zTree.getpId()) == 0) {
                nodes.add(zTree);
            }
        }
        return nodes;
    }

}
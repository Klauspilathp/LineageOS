package com.d7c.springboot.client.dtos.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: MenuTreeUtil
 * @Package: com.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月21日 上午11:11:28
 * @Description: 菜单树工具类
 */
public class MenuTreeUtil {

    /**
     * @Title: dealMenuTree
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午6:10:03
     * @Description: 根据拥有的菜单树顶级父编号获取以顶级父编号为顶级树的菜单树对象，列表中所有数据为父子级关系，子在父的 childrens 属性中
     * @param allMenuTree   全部菜单树列表
     * @param maxId         拥有的菜单树顶级编号
     * @return List<MenuTree>
     */
    public List<MenuTree> dealMenuTree(List<MenuTree> allMenuTree, Integer maxId) {
        if (allMenuTree == null || allMenuTree.isEmpty()) {
            return new ArrayList<MenuTree>();
        }
        if (maxId == null) {
            maxId = 0;
        }
        if (maxId.compareTo(0) == 1) {
            for (MenuTree menuTree : allMenuTree) {
                if (maxId.compareTo(menuTree.getMenuId()) == 0) {
                    List<MenuTree> childrens = this.recursionDealMenuTree(allMenuTree, maxId);
                    menuTree.setChildrens(childrens);
                    List<MenuTree> result = new ArrayList<MenuTree>();
                    result.add(menuTree);
                    return result;
                }
            }
        }
        return this.recursionDealMenuTree(allMenuTree, maxId);
    }

    public List<MenuTree> recursionDealMenuTree(List<MenuTree> allMenuTree, Integer parentId) {
        List<MenuTree> result = new ArrayList<MenuTree>();
        List<MenuTree> childrens = this.getChildrens(allMenuTree, parentId);
        for (MenuTree children : childrens) {
            List<MenuTree> nextMenuTrees = this.recursionDealMenuTree(allMenuTree, children.getMenuId());
            if (!nextMenuTrees.isEmpty()) {
                children.setChildrens(nextMenuTrees);
            }
            result.add(children);
        }
        return result;
    }

    public List<MenuTree> getChildrens(List<MenuTree> allMenuTree, Integer parentId) {
        List<MenuTree> childrens = new ArrayList<MenuTree>();
        for (MenuTree menuTree : allMenuTree) {
            if (parentId.compareTo(menuTree.getParentId()) == 0) {
                childrens.add(menuTree);
            }
        }
        return childrens;
    }

}
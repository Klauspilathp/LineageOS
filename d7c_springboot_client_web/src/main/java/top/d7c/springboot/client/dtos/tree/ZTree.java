package top.d7c.springboot.client.dtos.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: ZTree
 * @Package: top.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月7日 下午2:08:39
 * @Description: zTree 菜单树 Pojo
 */
public class ZTree implements Serializable {
    private static final long serialVersionUID = -8746902146932630324L;
    /**
     * 菜单编号
     */
    private Long id;
    /**
     * 父级菜单编号
     */
    private Long pId;
    /**
     * 菜单文本值
     */
    private String name;
    /**
     * 是否打开
     */
    private Boolean open;
    /**
     * 是否没有子节点
     */
    private Boolean isParent;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否选中
     */
    private Boolean checked = false;
    /**
     * 菜单目标
     */
    private String target;
    /**
     * 菜单链接
     */
    private String url;
    /**
     * 子菜单列表
     */
    private List<ZTree> nodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ZTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<ZTree> nodes) {
        this.nodes = nodes;
    }

}
package top.d7c.springboot.client.dtos.tree;

import java.io.Serializable;

/**
 * @Title: SelectTree
 * @Package: top.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2020年4月15日 下午6:07:44
 * @Description: SelectTree pojo
 */
public class SelectTree implements Serializable {
    private static final long serialVersionUID = -5015677880319099173L;
    /**
     * 当前节点编号
     */
    private Long id;
    /**
     * 父节点编号
     */
    private Long pId;
    /**
     * 节点文本值
     */
    private String name;
    /**
     * title 值
     */
    private String title;
    /**
     * 节点图标
     */
    private String icon;
    /**
     * 点击后跳转的链接地址
     */
    private String url;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
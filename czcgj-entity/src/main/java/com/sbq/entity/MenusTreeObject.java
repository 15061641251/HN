package com.sbq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个是列表树形式显示的实体, 这里的字段是在前台显示所有的,可修改
 *
 * @author lanyuan Email：mmm333zzz520@163.com date：2014-11-20
 */
@Alias("menus")
public class MenusTreeObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8404593381297140768L;

    @JsonIgnore
    private int resources_id;
    @JsonIgnore
    private int parent_id;
    @JsonIgnore
    private String logo;
    @JsonIgnore
    private String authority_name;
    @JsonIgnore
    private int authority_id;
    @JsonIgnore
    private String resources_name;
    @JsonIgnore
    private String path;
    @JsonIgnore
    private String weburl;
    @JsonIgnore
    private boolean is_show;

    //用于superui
    private String id;

    private String text;

    private String icon;

    private String targetType = "iframe-tab";

    private String url;

    private String isLocal = "True";

    private List<MenusTreeObject> children = new ArrayList<MenusTreeObject>();

    public int getResources_id() {
        return resources_id;
    }

    public void setResources_id(int resources_id) {
        this.resources_id = resources_id;
        this.id = String.valueOf(resources_id);
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
        this.icon = logo;
    }

    public String getAuthority_name() {
        return authority_name;
    }

    public void setAuthority_name(String authority_name) {
        this.authority_name = authority_name;
    }

    public int getAuthority_id() {
        return authority_id;
    }

    public void setAuthority_id(int authority_id) {
        this.authority_id = authority_id;
    }

    public String getResources_name() {
        return resources_name;
    }

    public void setResources_name(String resources_name) {
        this.resources_name = resources_name;
        this.text = resources_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MenusTreeObject> getChildren() {
        return children;
    }

    public void setChildren(List<MenusTreeObject> children) {
        this.children = children;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
        this.url = weburl;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }


    public String getId() {
        return id;
    }

    public MenusTreeObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public MenusTreeObject setText(String text) {
        this.text = text;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public MenusTreeObject setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getTargetType() {
        return targetType;
    }

    public MenusTreeObject setTargetType(String targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MenusTreeObject setUrl(String url) {
        this.url = url;
        return this;
    }


    public String getIsLocal() {
        return isLocal;
    }

    public MenusTreeObject setIsLocal(String isLocal) {
        this.isLocal = isLocal;
        return this;
    }
}

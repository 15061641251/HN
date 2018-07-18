package com.sbq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Alias("Resources")
@Table(name = "t_resources")
@Entity
public class Resources implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1713323019055167398L;

	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer int_id;

	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * 资源描述
	 */
	private String description;

	/**
	 * 访问路径(正则表达式,主要用于权限filter验证)
	 */
	private String path;
	/**
	 * 是否启用
	 */
	private boolean is_enabled;

	/**
	 * 父资源主键
	 */
	private Integer parent_id;

	/**
	 * 是否可见（主要控制菜单和按钮）
	 */
	private boolean is_show;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date create_time;
	/**
	 * 模块类型;1-菜单；2-按钮；3-功能
	 */
	private Integer type;
	/**
	 * 页面地址
	 */
	private String weburl;

	/**
	 * 菜单图片
	 */
	private String logo;

	/**
	 * 冗余字段，权限主键
	 */
	@Transient
	private Integer authority_id;
	/**
	 * 冗余字段，权限名称
	 */
	@Transient
	private String authority_name;
	/**
	 * 冗余字段，权限描述
	 */
	@Transient
	private String authority_description;
	/**
	 * 冗余字段，是否被选中
	 */
	@Transient
	private boolean is_checked;

    /**
     * 序值
     */
    private Integer ordernum;

	@Transient
	private List<Resources> children = new ArrayList<Resources>();

	public Integer getInt_id() {
		return int_id;
	}

	public void setInt_id(Integer int_id) {
		this.int_id = int_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(boolean is_enabled) {
		this.is_enabled = is_enabled;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public boolean isIs_show() {
		return is_show;
	}

	public void setIs_show(boolean is_show) {
		this.is_show = is_show;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<Resources> getChildren() {
		return children;
	}

	public void setChildren(List<Resources> children) {
		this.children = children;
	}

	public boolean isIs_checked() {
		return is_checked;
	}

	public void setIs_checked(boolean is_checked) {
		this.is_checked = is_checked;
	}

	public Integer getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
	}

	public String getAuthority_name() {
		return authority_name;
	}

	public void setAuthority_name(String authority_name) {
		this.authority_name = authority_name;
	}

	public String getAuthority_description() {
		return authority_description;
	}

	public void setAuthority_description(String authority_description) {
		this.authority_description = authority_description;
	}

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
}

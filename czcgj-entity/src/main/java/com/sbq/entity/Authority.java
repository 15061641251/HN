package com.sbq.entity;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Alias("Authority")
@Table(name = "t_authority")
@Entity
public class Authority implements Serializable
{
	private static final long serialVersionUID = 1123802606807261027L;
	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer int_id;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 权限描述
	 */
	private String description;
	/**
	 * 权限编码
	 */
	private String code;

	/**
	 * 是否启用
	 */
	private boolean is_enabled;
	
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date create_time;

	@Transient
	private String auth_ids;
	
	
	public Integer getInt_id()
	{
		return int_id;
	}
	
	public void setInt_id(Integer int_id)
	{
		this.int_id = int_id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public boolean isIs_enabled()
	{
		return is_enabled;
	}
	
	public void setIs_enabled(boolean is_enabled)
	{
		this.is_enabled = is_enabled;
	}
	
	public Date getCreate_time()
	{
		return create_time;
	}
	
	public void setCreate_time(Date create_time)
	{
		this.create_time = create_time;
	}
	
	@Override
	public String toString()
	{
		return super.toString();
	}

	public String getAuth_ids() {
		return auth_ids;
	}

	public void setAuth_ids(String auth_ids) {
		this.auth_ids = auth_ids;
	}
}

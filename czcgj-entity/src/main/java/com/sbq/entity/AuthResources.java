package com.sbq.entity;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Alias("AuthResources")
@Table(name = "t_auth_res")
@Entity
public class AuthResources implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5827347686342202681L;

	/**
	 * 主键
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer int_id;

	/**
	 * 权限主键
	 */
	private Integer authority_id;

	/**
	 * 资源主键
	 */
	private Integer resources_id;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date create_time;

	public Integer getInt_id() {
		return int_id;
	}

	public void setInt_id(Integer int_id) {
		this.int_id = int_id;
	}

	public Integer getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(Integer authority_id) {
		this.authority_id = authority_id;
	}

	public Integer getResources_id() {
		return resources_id;
	}

	public void setResources_id(Integer resources_id) {
		this.resources_id = resources_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}

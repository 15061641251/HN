package com.sbq.entity;

import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Alias("UserAuth")
@Table(name = "t_user_auth")
@Entity
public class UserAuth implements Serializable {
    private static final long serialVersionUID = -2532437838724066420L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    /**
     * 用户主键
     */
    private Long user_id;
    /**
     * 权限主键
     */
    private Integer auth_id;

    /**
     * 权限主键名称
     */
    @Transient
    private String auth_name;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(Integer auth_id) {
        this.auth_id = auth_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }
}

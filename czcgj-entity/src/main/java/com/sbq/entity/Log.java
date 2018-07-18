package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

@Alias("Log")
@Table(name = "t_log")
@Entity
public class Log extends BaseModel {

    /**
     * 模块名称
     */
    private String module_name;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 访问ip
     */
    private String ip;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 用户主键
     */
    private Long user_int_id;

    /**
     * 用户名称
     */
    private String user_name;

    public String getModule_name() {
        return module_name;
    }

    public Log setModule_name(String module_name) {
        this.module_name = module_name;
        return this;
    }

    public String getParams() {
        return params;
    }

    public Log setParams(String params) {
        this.params = params;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public Log setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public Long getUser_int_id() {
        return user_int_id;
    }

    public Log setUser_int_id(Long user_int_id) {
        this.user_int_id = user_int_id;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public Log setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }
}

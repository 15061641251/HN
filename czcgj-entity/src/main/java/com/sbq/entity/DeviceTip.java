package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

@Alias("DeviceTip")
@Table(name = "t_device_tip")
@Entity
public class DeviceTip extends BaseModel {

    /**
     * 设备主键
     */
    private Long device_int_id;

    /**
     * 手机号码
     */
    private String phone_num;

    /**
     * 提醒状态;1-启用;2-禁用
     */
    private Integer status;

    public Long getDevice_int_id() {
        return device_int_id;
    }

    public DeviceTip setDevice_int_id(Long device_int_id) {
        this.device_int_id = device_int_id;
        return this;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public DeviceTip setPhone_num(String phone_num) {
        this.phone_num = phone_num;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DeviceTip setStatus(Integer status) {
        this.status = status;
        return this;
    }
}

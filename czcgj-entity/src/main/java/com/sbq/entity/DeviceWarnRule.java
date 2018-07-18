package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备告警规则,多条记录是或的关系
 */
@Alias("DeviceWarnRule")
@Table(name = "t_device_warn_rule")
@Entity
public class DeviceWarnRule extends BaseModel {

    /**
     * 设备主键
     */
    private Long device_int_id;

    /**
     * 数据流的key
     */
    private String data_key;

    /**
     * 匹配条件;
     * 1-小于;2-小于等于;3-等于;4-大于等于;5-大于
     */
    private Integer condition_type;

    /**
     * 条件值
     */
    private String data_value;

    public Long getDevice_int_id() {
        return device_int_id;
    }

    public DeviceWarnRule setDevice_int_id(Long device_int_id) {
        this.device_int_id = device_int_id;
        return this;
    }

    public String getData_key() {
        return data_key;
    }

    public DeviceWarnRule setData_key(String data_key) {
        this.data_key = data_key;
        return this;
    }

    public Integer getCondition_type() {
        return condition_type;
    }

    public DeviceWarnRule setCondition_type(Integer condition_type) {
        this.condition_type = condition_type;
        return this;
    }

    public String getData_value() {
        return data_value;
    }

    public DeviceWarnRule setData_value(String data_value) {
        this.data_value = data_value;
        return this;
    }
}

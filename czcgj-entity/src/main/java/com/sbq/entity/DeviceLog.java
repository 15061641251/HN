package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Alias("DeviceLog")
@Table(name = "t_device_log")
@Entity
public class DeviceLog extends BaseModel {

    /**
     * 本系统中设备主键
     */
    private Long device_int_id;

    /**
     * 设备编号
     */
    private String device_id;

    /**
     * json数据存本次获取到的记录数
     */
    private String data;

    /**
     * 状态值 0-正常;1-告警;2-获取信息异常
     */
    private Integer status;

    /**
     * 本次记录上报的时间
     */
    private Date log_time;

    /**
     * 设备类型(哪家的,默认1)
     * 1:重庆厂家;
     * 2:常州本地厂家
     */
    private Integer device_type;


    public Long getDevice_int_id() {
        return device_int_id;
    }

    public DeviceLog setDevice_int_id(Long device_int_id) {
        this.device_int_id = device_int_id;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public DeviceLog setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getData() {
        return data;
    }

    public DeviceLog setData(String data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DeviceLog setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getLog_time() {
        return log_time;
    }

    public DeviceLog setLog_time(Date log_time) {
        this.log_time = log_time;
        return this;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }
}

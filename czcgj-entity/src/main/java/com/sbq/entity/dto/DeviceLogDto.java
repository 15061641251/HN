package com.sbq.entity.dto;

import java.util.Date;

public class DeviceLogDto {

    private Long int_id;

    private Integer stateflag = 0;

    private Date time_stamp = new Date();

    private Date create_time = new Date();

    private String device_name;

    private String device_id;

    private String data;

    private Integer status;

    private Date log_time;

    private Integer device_type;

    public Long getInt_id() {
        return int_id;
    }

    public DeviceLogDto setInt_id(Long int_id) {
        this.int_id = int_id;
        return this;
    }

    public Integer getStateflag() {
        return stateflag;
    }

    public DeviceLogDto setStateflag(Integer stateflag) {
        this.stateflag = stateflag;
        return this;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public DeviceLogDto setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public DeviceLogDto setCreate_time(Date create_time) {
        this.create_time = create_time;
        return this;
    }

    public String getDevice_name() {
        return device_name;
    }

    public DeviceLogDto setDevice_name(String device_name) {
        this.device_name = device_name;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public DeviceLogDto setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getData() {
        return data;
    }

    public DeviceLogDto setData(String data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DeviceLogDto setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Date getLog_time() {
        return log_time;
    }

    public DeviceLogDto setLog_time(Date log_time) {
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

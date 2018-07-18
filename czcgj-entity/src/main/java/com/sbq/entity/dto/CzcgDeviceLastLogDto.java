package com.sbq.entity.dto;

import java.util.Date;

public class CzcgDeviceLastLogDto {

    private String device_name;

    private String device_id;

    private Double lat;

    private Double lng;

    private String address;

    private Integer last_warn_status;

    private Date last_warn_time;

    private String company_name;

    private String area_name;

    private String data;

    private Integer status;

    private Date log_time;

    private Double x;

    private Double y;

    private Double z;

    private Integer type;

    private Integer device_type;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLast_warn_status() {
        return last_warn_status;
    }

    public void setLast_warn_status(Integer last_warn_status) {
        this.last_warn_status = last_warn_status;
    }

    public Date getLast_warn_time() {
        return last_warn_time;
    }

    public void setLast_warn_time(Date last_warn_time) {
        this.last_warn_time = last_warn_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }
}

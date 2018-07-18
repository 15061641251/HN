package com.sbq.entity.dto;

import java.util.Date;

public class DeviceDto {

    private Long int_id;
    private Integer stateflag = 0;
    private Date time_stamp = new Date();
    private Date create_time = new Date();
    /**
     * 设备名称
     */
    private String device_name;
    /**
     * 设备ID
     */
    private String device_id;

    /**
     * 设备类型(哪家的,默认1)
     * 1:重庆厂家;
     * 2:常州本地厂家
     */
    private Integer device_type;

    /**
     * 授权信息
     */
    private String auth_id;
    /**
     * 接入方式
     */
    private String access_type;
    /**
     * API-KEY
     */
    private String api_key;
    /**
     * 设备API 请求地址
     */
    private String api_url;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 经度
     */
    private Double lng;

    /**
     * 正常上报数据周期,默认60分钟
     */
    private Integer period;

    /**
     * 一般告警上报数据周期，默认10分钟
     */
    private Integer warn_period;

    /**
     * 一般告警级别的倾斜角度,默认5°
     */
    private Float warn_angle;

    /**
     * 严重告警上报数据周期，默认5分钟
     */
    private Integer serious_warn_period;

    /**
     * 严重告警级别的倾斜角度,默认10°
     */
    private Float serious_warn_angle;
    /**
     * 设备安装地址
     */
    private String address;

    /**
     * 所属公司主键
     */
    private Long company_id;

    private String company_name;

    /**
     * 定时任务状态
     */
    private Integer status = 0;

    /**
     * 是否设置了告警规则
     */
    private Integer is_set_rule;

    private Long area_id;

    private String area_name;

    /**
     * 最后一次告警状态;0-正常;1-告警;
     */
    private Integer last_warn_status = 0;

    /**
     * 最后一次告警时间
     */
    private Date last_warn_time;

    /**
     * 是否需要提醒
     */
    private Integer need_tip;

    /**
     * 多少分钟内不重复提醒
     */
    private Integer no_tip_minute;

    /**
     * 广告牌类型
     * 1、铁路立交；2、楼顶；3、高炮；4、外墙；5、地面LED
     */
    private Integer type = 1;

    private String phone;

    private String user_name;

    private String user_unit;

    public Long getInt_id() {
        return int_id;
    }

    public DeviceDto setInt_id(Long int_id) {
        this.int_id = int_id;
        return this;
    }

    public Integer getStateflag() {
        return stateflag;
    }

    public DeviceDto setStateflag(Integer stateflag) {
        this.stateflag = stateflag;
        return this;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public DeviceDto setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public DeviceDto setCreate_time(Date create_time) {
        this.create_time = create_time;
        return this;
    }

    public String getDevice_name() {
        return device_name;
    }

    public DeviceDto setDevice_name(String device_name) {
        this.device_name = device_name;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public DeviceDto setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public DeviceDto setAuth_id(String auth_id) {
        this.auth_id = auth_id;
        return this;
    }

    public String getAccess_type() {
        return access_type;
    }

    public DeviceDto setAccess_type(String access_type) {
        this.access_type = access_type;
        return this;
    }

    public String getApi_key() {
        return api_key;
    }

    public DeviceDto setApi_key(String api_key) {
        this.api_key = api_key;
        return this;
    }

    public String getApi_url() {
        return api_url;
    }

    public DeviceDto setApi_url(String api_url) {
        this.api_url = api_url;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public DeviceDto setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLng() {
        return lng;
    }

    public DeviceDto setLng(Double lng) {
        this.lng = lng;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DeviceDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public DeviceDto setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }

    public String getCompany_name() {
        return company_name;
    }

    public DeviceDto setCompany_name(String company_name) {
        this.company_name = company_name;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public DeviceDto setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public Integer getWarn_period() {
        return warn_period;
    }

    public DeviceDto setWarn_period(Integer warn_period) {
        this.warn_period = warn_period;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public DeviceDto setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getIs_set_rule() {
        return is_set_rule;
    }

    public DeviceDto setIs_set_rule(Integer is_set_rule) {
        this.is_set_rule = is_set_rule;
        return this;
    }

    public Long getArea_id() {
        return area_id;
    }

    public DeviceDto setArea_id(Long area_id) {
        this.area_id = area_id;
        return this;
    }

    public String getArea_name() {
        return area_name;
    }

    public DeviceDto setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }

    public Integer getLast_warn_status() {
        return last_warn_status;
    }

    public DeviceDto setLast_warn_status(Integer last_warn_status) {
        this.last_warn_status = last_warn_status;
        return this;
    }

    public Integer getNeed_tip() {
        return need_tip;
    }

    public DeviceDto setNeed_tip(Integer need_tip) {
        this.need_tip = need_tip;
        return this;
    }

    public Integer getNo_tip_minute() {
        return no_tip_minute;
    }

    public DeviceDto setNo_tip_minute(Integer no_tip_minute) {
        this.no_tip_minute = no_tip_minute;
        return this;
    }

    public Float getWarn_angle() {
        return warn_angle;
    }

    public void setWarn_angle(Float warn_angle) {
        this.warn_angle = warn_angle;
    }

    public Integer getSerious_warn_period() {
        return serious_warn_period;
    }

    public void setSerious_warn_period(Integer serious_warn_period) {
        this.serious_warn_period = serious_warn_period;
    }

    public Float getSerious_warn_angle() {
        return serious_warn_angle;
    }

    public void setSerious_warn_angle(Float serious_warn_angle) {
        this.serious_warn_angle = serious_warn_angle;
    }

    public Date getLast_warn_time() {
        return last_warn_time;
    }

    public void setLast_warn_time(Date last_warn_time) {
        this.last_warn_time = last_warn_time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_unit() {
        return user_unit;
    }

    public void setUser_unit(String user_unit) {
        this.user_unit = user_unit;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }
}

package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Alias("Device")
@Table(name = "t_device")
@Entity
public class Device extends BaseModel {

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
    private Integer device_type = 1;
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
    private Integer warn_period = 10;

    /**
     * 一般告警级别的倾斜角度,默认5°
     */
    private Float warn_angle = 5.0f;

    /**
     * 严重告警上报数据周期，默认5分钟
     */
    private Integer serious_warn_period = 5;

    /**
     * 严重告警级别的倾斜角度,默认10°
     */
    private Float serious_warn_angle = 10.0f;

    /**
     * 设备安装地址
     */
    private String address;

    /**
     * 所属公司主键
     */
    private Long company_id;

    /**
     * 所属区域主键
     */
    private Long area_id;

    /**
     * 最后一次告警状态;0-正常;1-一般告警;2-严重告警
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
     * 多少分钟内不重复提醒,默认60
     */
    private Integer no_tip_minute = 60;

    /**
     * 广告牌类型
     * 0、测试；1、铁路立交（默认）；2、楼顶；3、高炮；4、外墙；5、地面LED
     */
    private Integer type = 1;

    private String phone;

    private String user_name;

    private String user_unit;


    public String getDevice_name() {
        return device_name;
    }

    public Device setDevice_name(String device_name) {
        this.device_name = device_name;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
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

    public Long getCompany_id() {
        return company_id;
    }

    public Device setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public Device setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public Integer getWarn_period() {
        return warn_period;
    }

    public Device setWarn_period(Integer warn_period) {
        this.warn_period = warn_period;
        return this;
    }

    public Long getArea_id() {
        return area_id;
    }

    public Device setArea_id(Long area_id) {
        this.area_id = area_id;
        return this;
    }

    public Integer getLast_warn_status() {
        return last_warn_status;
    }

    public Device setLast_warn_status(Integer last_warn_status) {
        this.last_warn_status = last_warn_status;
        return this;
    }

    public Integer getNeed_tip() {
        return need_tip;
    }

    public Device setNeed_tip(Integer need_tip) {
        this.need_tip = need_tip;
        return this;
    }

    public Integer getNo_tip_minute() {
        return no_tip_minute;
    }

    public Device setNo_tip_minute(Integer no_tip_minute) {
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

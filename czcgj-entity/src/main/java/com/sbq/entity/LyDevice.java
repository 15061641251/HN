package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Alias("LyDevice")
@Table(name = "t_ly_device")
@Entity
public class LyDevice extends BaseModel {

    /**
     * 归属区
     */
    private String area_name;
    /**
     * 设置地址
     */
    private String address;
    /**
     * 客户单位
     */
    private String company_name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 负责人
     */
    private String person_name;
    /**
     * 形式
     */
    private String style;
    /**
     * 长
     */
    private Double length;
    /**
     * 宽
     */
    private Double width;
    /**
     * 数量
     */
    private Long num;
    /**
     * 面积
     */
    private Double area;
    /**
     * 批准日期
     */
    private Date confirm_date;
    /**
     * 设置期限
     */
    private Date dead_time;
    /**
     * 隶属街道
     */
    private String of_streets;
    /**
     * 现场照片
     */
    private String img_ids;
    /**
     * 通知书编号
     */
    private String notice_num;
    /**
     * mac地址
     */
    private String sn;


    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Date getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(Date confirm_date) {
        this.confirm_date = confirm_date;
    }

    public Date getDead_time() {
        return dead_time;
    }

    public void setDead_time(Date dead_time) {
        this.dead_time = dead_time;
    }

    public String getOf_streets() {
        return of_streets;
    }

    public void setOf_streets(String of_streets) {
        this.of_streets = of_streets;
    }

    public String getImg_ids() {
        return img_ids;
    }

    public void setImg_ids(String img_ids) {
        this.img_ids = img_ids;
    }

    public String getNotice_num() {
        return notice_num;
    }

    public void setNotice_num(String notice_num) {
        this.notice_num = notice_num;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

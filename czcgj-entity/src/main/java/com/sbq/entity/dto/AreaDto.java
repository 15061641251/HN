package com.sbq.entity.dto;

import java.util.Date;

public class AreaDto {

    private Long int_id;
    private Integer stateflag = 0;
    private Date time_stamp = new Date();
    private Date create_time = new Date();

    /**
     * 区域名称
     */
    private String area_name;

    /**
     * 负责人姓名
     */
    private String leader_name;

    /**
     * 负责人联系dian
     */
    private String leader_phone;

    /**
     * 所在注册机构主键
     */
    private Long company_id;

    private String company_name;

    public Long getInt_id() {
        return int_id;
    }

    public AreaDto setInt_id(Long int_id) {
        this.int_id = int_id;
        return this;
    }

    public Integer getStateflag() {
        return stateflag;
    }

    public AreaDto setStateflag(Integer stateflag) {
        this.stateflag = stateflag;
        return this;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public AreaDto setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public AreaDto setCreate_time(Date create_time) {
        this.create_time = create_time;
        return this;
    }

    public String getArea_name() {
        return area_name;
    }

    public AreaDto setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public AreaDto setLeader_name(String leader_name) {
        this.leader_name = leader_name;
        return this;
    }

    public String getLeader_phone() {
        return leader_phone;
    }

    public AreaDto setLeader_phone(String leader_phone) {
        this.leader_phone = leader_phone;
        return this;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public AreaDto setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }

    public String getCompany_name() {
        return company_name;
    }

    public AreaDto setCompany_name(String company_name) {
        this.company_name = company_name;
        return this;
    }
}

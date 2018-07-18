package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

@Alias("Area")
@Table(name = "t_area")
@Entity
public class Area extends BaseModel {

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

    public String getArea_name() {
        return area_name;
    }

    public Area setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public Area setLeader_name(String leader_name) {
        this.leader_name = leader_name;
        return this;
    }

    public String getLeader_phone() {
        return leader_phone;
    }

    public Area setLeader_phone(String leader_phone) {
        this.leader_phone = leader_phone;
        return this;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public Area setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }
}

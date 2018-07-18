package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

@Alias("Company")
@Table(name = "t_company")
@Entity
public class Company extends BaseModel {

    /**
     * 公司名称
     */
    private String company_name;
    /**
     * 公司地址
     */
    private String address;
    /**
     * 联系人名称
     */
    private String person_name;
    /**
     * 联系人电话
     */
    private String person_phone;
    /**
     * 备注
     */
    private String remark;


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_phone() {
        return person_phone;
    }

    public void setPerson_phone(String person_phone) {
        this.person_phone = person_phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.sbq.entity.dto;


import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDto {

    @NotEmpty(message = "机构名称不能为空")
    private String company_name;

    @NotEmpty(message = "注册地址不能为空")
    private String address;

    @NotEmpty(message = "联系人不能为空")
    private String person_name;

    @NotEmpty(message = "联系电话不能为空")
    private String person_phone;

    @NotEmpty(message = "账号不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String pwd;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

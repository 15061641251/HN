package com.sbq.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * 用户对象
 *
 * @author ZhuYanBing
 * @create 2017-03-22 9:50
 */

@ApiModel(value = "UserDto", description = "用户对象")
public class UserDto {

    private Long int_id;
    private String account = "";
    private String username = "";
    private String password;
    private Long dept_id;
    private String dept_name = "";
    private String tel = "";
    private String mobile = "";
    private String mobile2 = "";
    private String sex = "";
    private String email = "";
    private String address = "";
    private String head_url = "";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer is_enabled;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer order_index;
    private String auth_ids = "";
    private String auth_names = "";
    private String remark = "";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float jifen;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float liul;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int nature;
    private String nick_name = "";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int nick_name_flag;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int workstatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date sign_time;

    private Integer is_admin;

    private Long company_id;

    private String company_name;

    private Long area_id;

    private String area_name;

    public Long getInt_id() {
        return int_id;
    }

    public void setInt_id(Long int_id) {
        this.int_id = int_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDept_id() {
        return dept_id;
    }

    public void setDept_id(Long dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getAuth_ids() {
        return auth_ids;
    }

    public void setAuth_ids(String auth_ids) {
        this.auth_ids = auth_ids;
    }

    public String getAuth_names() {
        return auth_names;
    }

    public void setAuth_names(String auth_names) {
        this.auth_names = auth_names;
    }

    public Integer getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Integer is_enabled) {
        this.is_enabled = is_enabled;
    }

    public Integer getOrder_index() {
        return order_index;
    }

    public void setOrder_index(Integer order_index) {
        this.order_index = order_index;
    }

    public String getRemark() {
        return remark;
    }

    public UserDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public float getJifen() {
        return jifen;
    }

    public UserDto setJifen(float jifen) {
        this.jifen = jifen;
        return this;
    }

    public float getLiul() {
        return liul;
    }

    public UserDto setLiul(float liul) {
        this.liul = liul;
        return this;
    }

    public int getNature() {
        return nature;
    }

    public UserDto setNature(int nature) {
        this.nature = nature;
        return this;
    }

    public String getNick_name() {
        return nick_name;
    }

    public UserDto setNick_name(String nick_name) {
        this.nick_name = nick_name;
        return this;
    }

    public int getNick_name_flag() {
        return nick_name_flag;
    }

    public UserDto setNick_name_flag(int nick_name_flag) {
        this.nick_name_flag = nick_name_flag;
        return this;
    }

    public int getWorkstatus() {
        return workstatus;
    }

    public UserDto setWorkstatus(int workstatus) {
        this.workstatus = workstatus;
        return this;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public UserDto setSign_time(Date sign_time) {
        this.sign_time = sign_time;
        return this;
    }

    public Integer getIs_admin() {
        return is_admin;
    }

    public UserDto setIs_admin(Integer is_admin) {
        this.is_admin = is_admin;
        return this;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public UserDto setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }

    public String getCompany_name() {
        return company_name;
    }

    public UserDto setCompany_name(String company_name) {
        this.company_name = company_name;
        return this;
    }

    public Long getArea_id() {
        return area_id;
    }

    public UserDto setArea_id(Long area_id) {
        this.area_id = area_id;
        return this;
    }

    public String getArea_name() {
        return area_name;
    }

    public UserDto setArea_name(String area_name) {
        this.area_name = area_name;
        return this;
    }
}

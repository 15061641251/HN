package com.sbq.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Alias("User")
@Table(name = "t_user")
@Entity
@ApiModel(value = "用户对象", description = "user")
public class User extends BaseModel {

    private static final long serialVersionUID = -3761540321759253253L;

    /**
     * 账户
     */
    @Column(name = "account")
    private String account;

    /**
     * 姓名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 部门主键
     */
    @Column(name = "dept_id")
    private Long dept_id;

    /**
     * 冗余字段，部门名称
     */
    @Transient
    private String dept_name;

    /**
     * 固定电话
     */
    @Column(name = "tel")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tel;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile;

    /**
     * 手机号码2
     */
    @Column(name = "mobile2")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile2;

    /**
     * 性别
     */
    @Column(name = "sex")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sex;

    /**
     * 邮箱
     */
    @Column(name = "email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    /**
     * 地址
     */
    @Column(name = "address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    /**
     * 序号
     */
    @Column(name = "order_index")
    private Integer order_index;

    /**
     * 头像
     */
    @Column(name = "head_url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String head_url;

    /**
     * 设备token号
     */
    @Column(name = "device_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String device_token;

    /**
     * 设备类型 1--ios；2--android
     */
    @Column(name = "app_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer type;

    /**
     * 状态
     * 0-正常,1-禁用
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "is_enabled")
    private Integer is_enabled;

    /**
     * pc最后登录时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date pc_log_last_time;

    /**
     * APP最后登录时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date app_log_last_time;

    /**
     * APP是否可用
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer app_is_enabled;

    /**
     * PC端使用可用
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pc_is_enabled;

    /**
     * 具体手机类型，例如android的华为;
     * 0:其他；1-华为;
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer phone_type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float jifen;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float liul;

    /**
     * 1--巡视员；2--调研员；3--主任科员；4--科员；5--办事员
     */
    private Integer nature;

    /**
     * 昵称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nick_name;

    /**
     * 昵称是否启用：1 是启用， 0 是未启用
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer nick_name_flag;

    /**
     * 岗位状态：1-在岗；2-会议；3-出差；4-休息；5-就餐
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer workstatus;

    /**
     *
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date sign_time;

    /**
     * 是否管理员
     */
    @Column(name = "is_admin")
    private Integer is_admin;

    /**
     * 所属公司主键
     */
    private Long company_id;

    /**
     * 所在区域主键
     */
    private Long area_id;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOrder_index() {
        return order_index;
    }

    public void setOrder_index(Integer order_index) {
        this.order_index = order_index;
    }

    public Integer getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Integer is_enabled) {
        this.is_enabled = is_enabled;
    }


    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public Date getApp_log_last_time() {
        return app_log_last_time;
    }

    public User setApp_log_last_time(Date app_log_last_time) {
        this.app_log_last_time = app_log_last_time;
        return this;
    }

    public Integer getApp_is_enabled() {
        return app_is_enabled;
    }

    public User setApp_is_enabled(Integer app_is_enabled) {
        this.app_is_enabled = app_is_enabled;
        return this;
    }

    public Integer getPc_is_enabled() {
        return pc_is_enabled;
    }

    public User setPc_is_enabled(Integer pc_is_enabled) {
        this.pc_is_enabled = pc_is_enabled;
        return this;
    }

    public Date getPc_log_last_time() {
        return pc_log_last_time;
    }

    public void setPc_log_last_time(Date pc_log_last_time) {
        this.pc_log_last_time = pc_log_last_time;
    }

    public Integer getPhone_type() {
        return phone_type;
    }

    public User setPhone_type(Integer phone_type) {
        this.phone_type = phone_type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public User setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Float getJifen() {
        return jifen;
    }

    public User setJifen(Float jifen) {
        this.jifen = jifen;
        return this;
    }

    public Float getLiul() {
        return liul;
    }

    public User setLiul(Float liul) {
        this.liul = liul;
        return this;
    }

    public Integer getNature() {
        return nature;
    }

    public User setNature(Integer nature) {
        this.nature = nature;
        return this;
    }

    public String getNick_name() {
        return nick_name;
    }

    public User setNick_name(String nick_name) {
        this.nick_name = nick_name;
        return this;
    }

    public Integer getNick_name_flag() {
        return nick_name_flag;
    }

    public User setNick_name_flag(Integer nick_name_flag) {
        this.nick_name_flag = nick_name_flag;
        return this;
    }

    public Integer getWorkstatus() {
        return workstatus;
    }

    public User setWorkstatus(int workstatus) {
        this.workstatus = workstatus;
        return this;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public User setSign_time(Date sign_time) {
        this.sign_time = sign_time;
        return this;
    }

    public User setWorkstatus(Integer workstatus) {
        this.workstatus = workstatus;
        return this;
    }

    public Integer getIs_admin() {
        return is_admin;
    }

    public User setIs_admin(Integer is_admin) {
        this.is_admin = is_admin;
        return this;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public User setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }

    public Long getArea_id() {
        return area_id;
    }

    public User setArea_id(Long area_id) {
        this.area_id = area_id;
        return this;
    }
}

package com.sbq.entity.dto;

import org.apache.ibatis.type.Alias;

/**
 * 客户端通讯录返回实体类，部门和人员通用模版
 */

@Alias("DeptOrUserDto")
public class DeptOrUserDto {

    /**
     * 主键
     */
    private String int_id;

    /**
     * 默认为0 ，设置为1时数据无效
     */
    private String stateflag;

    /**
     * 时间戳(插入,更新时间)
     */
    private String time_stamp;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 账户
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门主键
     */
    private String dept_id;

    /**
     * 手机号码
     */

    private String tel;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 手机号码
     */
    private String mobile2;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 类型
     */
    private String type;

    /**
     * 头像
     */
    private String head_url;

    /**
     * 状态
     * 0-正常,1-禁用
     */
    private String is_enabled;

    /**
     * 冗余字段
     */
    private String parent_id;

    private String dept_short;

    private String fax;

    private String order_index;

    public String getStateflag() {
        return stateflag;
    }

    public void setStateflag(String stateflag) {
        this.stateflag = stateflag;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(String is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getDept_short() {
        return dept_short;
    }

    public void setDept_short(String dept_short) {
        this.dept_short = dept_short;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getOrder_index() {
        return order_index;
    }

    public void setOrder_index(String order_index) {
        this.order_index = order_index;
    }

    public String getInt_id() {
        return int_id;
    }

    public void setInt_id(String int_id) {
        this.int_id = int_id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeptOrUserDto{");
        sb.append("int_id='").append(int_id).append('\'');
        sb.append(", stateflag='").append(stateflag).append('\'');
        sb.append(", time_stamp='").append(time_stamp).append('\'');
        sb.append(", create_time='").append(create_time).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", dept_id='").append(dept_id).append('\'');
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", mobile2='").append(mobile2).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", head_url='").append(head_url).append('\'');
        sb.append(", is_enabled='").append(is_enabled).append('\'');
        sb.append(", parent_id='").append(parent_id).append('\'');
        sb.append(", dept_short='").append(dept_short).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", order_index='").append(order_index).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

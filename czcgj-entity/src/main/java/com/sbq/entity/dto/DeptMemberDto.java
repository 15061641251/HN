package com.sbq.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门和用户信息的通用类
 * Created by zhangyuan on 2016/12/30.
 */
public class DeptMemberDto {

    private long int_id;

    private long parent_id;

    private long dept_id;

    private String account;

    private String name = "";
    private String mobile = "";
    private String type = "";
    private String tel = "";
    private String pinyin = "";

    private String head_url = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DeptMemberDto> children = new ArrayList<DeptMemberDto>();

    public long getInt_id() {
        return int_id;
    }

    public DeptMemberDto setInt_id(long int_id) {
        this.int_id = int_id;
        return this;
    }

    public long getParent_id() {
        return parent_id;
    }

    public DeptMemberDto setParent_id(long parent_id) {
        this.parent_id = parent_id;
        return this;
    }

    public long getDept_id() {
        return dept_id;
    }

    public DeptMemberDto setDept_id(long dept_id) {
        this.dept_id = dept_id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public DeptMemberDto setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeptMemberDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeptMemberDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public DeptMemberDto setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public List<DeptMemberDto> getChildren() {
        return children;
    }

    public DeptMemberDto setChildren(List<DeptMemberDto> children) {
        this.children = children;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public DeptMemberDto setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPinyin() {
        return pinyin;
    }

    public DeptMemberDto setPinyin(String pinyin) {
        this.pinyin = pinyin;
        return this;
    }

    public String getHead_url() {
        return head_url;
    }

    public DeptMemberDto setHead_url(String head_url) {
        this.head_url = head_url;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeptMemberDto{");
        sb.append("int_id=").append(int_id);
        sb.append(", parent_id=").append(parent_id);
        sb.append(", dept_id=").append(dept_id);
        sb.append(", account='").append(account).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", pinyin='").append(pinyin).append('\'');
        sb.append(", head_url='").append(head_url).append('\'');
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}

package com.sbq.entity;

import com.sbq.entity.dto.UserDto;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Alias("Dept")
@Table(name = "a_dept")
@Entity
public class Dept extends BaseModel {
    private static final long serialVersionUID = -5666547300281991389L;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String dept_name;

    /**
     * 部门简称
     */
    @Column(name = "dept_short")
    private String dept_short;

    /**
     * 父部门主键
     */
    @Column(name = "parent_id")
    private Long parent_id;

    /**
     * 部门号码
     */
    @Column(name = "tel")
    private String tel;

    /**
     * 部门传真
     */
    @Column(name = "fax")
    private String fax;

    /**
     * 排序
     */
    private Integer order_index;

    /**
     * 所属公司主键
     */
    private Long company_id;

    /**
     * 冗余字段
     * 子部门列表
     */
    @Transient
    private List<Dept> childDepts = new ArrayList<Dept>();

    /**
     * 冗余字段
     * 部门人员
     */
    @Transient
    private List<UserDto> memList = new ArrayList<UserDto>();

    public List<UserDto> getMemList() {
        return memList;
    }

    public void setMemList(List<UserDto> memList) {
        this.memList = memList;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_short() {
        return dept_short;
    }

    public void setDept_short(String dept_short) {
        this.dept_short = dept_short;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getOrder_index() {
        return order_index;
    }

    public void setOrder_index(Integer order_index) {
        this.order_index = order_index;
    }

    public List<Dept> getChildDepts() {
        return childDepts;
    }

    public void setChildDepts(List<Dept> childDepts) {
        this.childDepts = childDepts;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public Dept setCompany_id(Long company_id) {
        this.company_id = company_id;
        return this;
    }
}

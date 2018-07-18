package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 间隔定时器
 */
@Alias("IntervalQuartz")
@Table(name = "t_interval_quartz")
@Entity
public class IntervalQuartz extends BaseModel {

    /**
     * 关联的设备主键
     */
    private Long device_int_id;

    /**
     * 任务key
     */
    private String task_key;

    /**
     * 执行的bean
     */
    private String bean_class;

    /**
     * 时间类型,1-秒;2-分;3-时
     */
    private Integer time_type;

    /**
     * 对应的值
     */
    private Integer value;

    /**
     * 状态;1-启用;0-未启用
     */
    private Integer status = 0;

    private String job_name;

    private String job_group_name;

    private String trigger_name;

    private String trigger_group_name;

    public Long getDevice_int_id() {
        return device_int_id;
    }

    public IntervalQuartz setDevice_int_id(Long device_int_id) {
        this.device_int_id = device_int_id;
        return this;
    }

    public String getTask_key() {
        return task_key;
    }

    public IntervalQuartz setTask_key(String task_key) {
        this.task_key = task_key;
        return this;
    }

    public String getBean_class() {
        return bean_class;
    }

    public IntervalQuartz setBean_class(String bean_class) {
        this.bean_class = bean_class;
        return this;
    }

    public Integer getTime_type() {
        return time_type;
    }

    public IntervalQuartz setTime_type(Integer time_type) {
        this.time_type = time_type;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public IntervalQuartz setValue(Integer value) {
        this.value = value;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public IntervalQuartz setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getJob_name() {
        return job_name;
    }

    public IntervalQuartz setJob_name(String job_name) {
        this.job_name = job_name;
        return this;
    }

    public String getJob_group_name() {
        return job_group_name;
    }

    public IntervalQuartz setJob_group_name(String job_group_name) {
        this.job_group_name = job_group_name;
        return this;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public IntervalQuartz setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
        return this;
    }

    public String getTrigger_group_name() {
        return trigger_group_name;
    }

    public IntervalQuartz setTrigger_group_name(String trigger_group_name) {
        this.trigger_group_name = trigger_group_name;
        return this;
    }
}

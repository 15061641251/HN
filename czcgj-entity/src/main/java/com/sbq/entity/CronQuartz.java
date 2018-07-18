package com.sbq.entity;

import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zhangyuan on 2017/6/13.
 */
@Alias("CronQuartz")
@Table(name = "t_cron_quartz")
@Entity
public class CronQuartz extends BaseModel {

    /**
     * 关联的设备主键
     */
    private String device_int_id;

    /**
     * 任务key
     */
    private String task_key;

    /**
     * 执行的bean
     */
    private String bean_class;

    /**
     * 时间表达式
     */
    private String cron_exp;

    /**
     * 状态;1-启用;0-未启用
     */
    private Integer status = 0;

    private String job_name;

    private String job_group_name;

    private String trigger_name;

    private String trigger_group_name;


    public String getDevice_int_id() {
        return device_int_id;
    }

    public CronQuartz setDevice_int_id(String device_int_id) {
        this.device_int_id = device_int_id;
        return this;
    }

    public String getTask_key() {
        return task_key;
    }

    public CronQuartz setTask_key(String task_key) {
        this.task_key = task_key;
        return this;
    }

    public String getCron_exp() {
        return cron_exp;
    }

    public CronQuartz setCron_exp(String cron_exp) {
        this.cron_exp = cron_exp;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public CronQuartz setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getBean_class() {
        return bean_class;
    }

    public CronQuartz setBean_class(String bean_class) {
        this.bean_class = bean_class;
        return this;
    }

    public String getJob_name() {
        return job_name;
    }

    public CronQuartz setJob_name(String job_name) {
        this.job_name = job_name;
        return this;
    }

    public String getJob_group_name() {
        return job_group_name;
    }

    public CronQuartz setJob_group_name(String job_group_name) {
        this.job_group_name = job_group_name;
        return this;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public CronQuartz setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
        return this;
    }

    public String getTrigger_group_name() {
        return trigger_group_name;
    }

    public CronQuartz setTrigger_group_name(String trigger_group_name) {
        this.trigger_group_name = trigger_group_name;
        return this;
    }
}

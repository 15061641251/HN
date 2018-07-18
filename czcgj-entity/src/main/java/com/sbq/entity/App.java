package com.sbq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sbq.common.dateAdapter.JaxbDateTimeAdapter;
import com.sbq.common.dateAdapter.JsonDateSerializer;
import com.sbq.common.dateAdapter.JsonDateTimeSerializer;
import org.apache.ibatis.type.Alias;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyuan on 2017/2/13.
 */
@Alias("App")
@Table(name = "t_app")
@Entity
@XmlRootElement(name = "App")
public class App implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long int_id;

    private Integer stateflag;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date create_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date time_stamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer app_id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String app_version;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer is_force;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    public Integer getStateflag() {
        return stateflag;
    }

    public App setStateflag(Integer stateflag) {
        this.stateflag = stateflag;
        return this;
    }

    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    public Date getCreate_time() {
        return create_time;
    }

    public App setCreate_time(Date create_time) {
        this.create_time = create_time;
        return this;
    }

    @XmlJavaTypeAdapter(JaxbDateTimeAdapter.class)
    public Date getTime_stamp() {
        return time_stamp;
    }

    public App setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }

    public Integer getApp_id() {
        return app_id;
    }

    public App setApp_id(Integer app_id) {
        this.app_id = app_id;
        return this;
    }

    public String getApp_version() {
        return app_version;
    }

    public App setApp_version(String app_version) {
        this.app_version = app_version;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public App setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getIs_force() {
        return is_force;
    }

    public App setIs_force(Integer is_force) {
        this.is_force = is_force;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public App setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getInt_id() {
        return int_id;
    }

    public void setInt_id(Long int_id) {
        this.int_id = int_id;
    }
}

package com.sbq.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ZhuYanBing
 * @create 2017-03-22 14:43
 */
public class BaseDto {

    private Long int_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time_stamp;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    public Long getInt_id() {
        return int_id;
    }

    public void setInt_id(Long int_id) {
        this.int_id = int_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public BaseDto setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
        return this;
    }
}

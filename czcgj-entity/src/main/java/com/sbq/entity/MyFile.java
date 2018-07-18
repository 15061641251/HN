package com.sbq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sbq.common.Constants;
import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 文件类
 */
@Alias("MyFile")
@Table(name = "t_file")
@Entity
public class MyFile extends BaseModel {

    /**
     * 客户端上传文件名称
     */
    private String file_name;
    /**
     * 文件服务器保存名称
     */
    private String file_save_name;
    /**
     * 文件类型 1:图片;2:视频、音频;3:文档、PDF、压缩包、其他
     */
    private Long file_type;
    /**
     * 文件保存路径
     */
    @JsonIgnore
    private String file_path;
    /**
     * 客户端本地文件上传路径
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String local_file_path;
    /**
     * 如果是图片涉及到该字段
     * 图片压缩地址
     */
    @JsonIgnore
    private String file_zip_path;
    /**
     * 如果是音频文件涉及到
     * 播放时长
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long video_second;
    /**
     * 文件大小
     */
    private String file_size;

    /**
     * UUID,主要利用客户端下载
     */
    private String uuid;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String file_web_path;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String file_zip_web_path;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_save_name() {
        return file_save_name;
    }

    public void setFile_save_name(String file_save_name) {
        this.file_save_name = file_save_name;
    }

    public Long getFile_type() {
        return file_type;
    }

    public void setFile_type(Long file_type) {
        this.file_type = file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getLocal_file_path() {
        return local_file_path;
    }

    public void setLocal_file_path(String local_file_path) {
        this.local_file_path = local_file_path;
    }

    public String getFile_zip_path() {
        return file_zip_path;
    }

    public void setFile_zip_path(String file_zip_path) {
        this.file_zip_path = file_zip_path;
    }

    public Long getVideo_second() {
        return video_second;
    }

    public void setVideo_second(Long video_second) {
        this.video_second = video_second;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getUuid() {
        return uuid;
    }

    public MyFile setUuid(String uuid) {
        this.uuid = uuid;

        if (!StringUtils.isEmpty(file_path)) {
            setFile_web_path(Constants.DOWN_LOAD_PATH + uuid);
        }

        if (!StringUtils.isEmpty(file_zip_path)) {
            setFile_zip_web_path(Constants.DOWN_LOAD_PATH + uuid + "&zip=1");
        }


        return this;
    }

    public String getFile_web_path() {
        return file_web_path;
    }

    public MyFile setFile_web_path(String file_web_path) {
        this.file_web_path = file_web_path;
        return this;
    }

    public String getFile_zip_web_path() {
        return file_zip_web_path;
    }

    public MyFile setFile_zip_web_path(String file_zip_web_path) {
        this.file_zip_web_path = file_zip_web_path;
        return this;
    }
}

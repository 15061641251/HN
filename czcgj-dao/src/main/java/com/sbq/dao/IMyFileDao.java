package com.sbq.dao;

import com.sbq.entity.MyFile;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhangyuan on 2017/3/28.
 */
public interface IMyFileDao extends Mapper<MyFile> {

    List<MyFile> selectMyFileByIds(@Param("file_ids") String file_ids);

    MyFile selectMyFileById(@Param("file_id") Long file_id);
}

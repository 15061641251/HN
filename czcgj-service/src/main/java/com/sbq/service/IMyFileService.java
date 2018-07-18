package com.sbq.service;

import com.sbq.entity.MyFile;

import java.util.List;

/**
 * Created by zhangyuan on 2017/3/28.
 */
public interface IMyFileService {

    public void insertMyFile(MyFile file);


    MyFile selectByUUID(String uuid);

    List<MyFile> selectMyFileByIds(String file_ids);
}

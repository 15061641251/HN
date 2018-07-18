package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Log;

import java.util.Map;

public interface ILogService {

    public void insertLog(Log log);

    public PageInfo<Log> getLogListByPage(Map<String, Object> map);
}

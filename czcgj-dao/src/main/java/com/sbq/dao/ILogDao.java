package com.sbq.dao;

import com.sbq.entity.Log;
import com.sbq.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface ILogDao extends MyMapper<Log>{

    public List<Log> getLogListByMap(Map map);
}

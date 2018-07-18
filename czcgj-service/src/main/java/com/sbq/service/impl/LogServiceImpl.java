package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.ILogDao;
import com.sbq.entity.Log;
import com.sbq.service.BaseService;
import com.sbq.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl extends BaseService implements ILogService {

    @Autowired
    private ILogDao logDao;


    @Override
    public void insertLog(Log log) {
        logDao.insertSelective(log);
    }

    @Override
    public PageInfo<Log> getLogListByPage(Map<String, Object> map) {
        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));


        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<Log> list = logDao.getLogListByMap(map);
        return new PageInfo<Log>(list);
    }
}

package com.sbq.service.impl;

import com.sbq.dao.ICronQuartzDao;
import com.sbq.entity.CronQuartz;
import com.sbq.service.BaseService;
import com.sbq.service.ICronQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/6/13.
 */
@Service
public class CronQuartzServiceImpl extends BaseService implements ICronQuartzService {

    @Autowired
    private ICronQuartzDao iCronQuartzDao;


    @Override
    public List<CronQuartz> getCronQuartzListByMap(Map map) {
        return iCronQuartzDao.getCronQuartzListByMap(map);
    }
}

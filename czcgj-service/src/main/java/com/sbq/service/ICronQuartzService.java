package com.sbq.service;

import com.sbq.entity.CronQuartz;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/6/13.
 */
public interface ICronQuartzService {

    public List<CronQuartz> getCronQuartzListByMap(Map map);

}

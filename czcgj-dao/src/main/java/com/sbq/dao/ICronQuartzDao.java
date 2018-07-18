package com.sbq.dao;

import com.sbq.entity.CronQuartz;
import com.sbq.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/6/13.
 */
public interface ICronQuartzDao extends MyMapper<CronQuartz> {

    public List<CronQuartz> getCronQuartzListByMap(Map map);

    void updateCronQuartzByMap(Map map);
}

package com.sbq.dao;

import com.sbq.entity.IntervalQuartz;
import com.sbq.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/6/13.
 */
public interface IIntervalQuartzDao extends MyMapper<IntervalQuartz> {

    public List<IntervalQuartz> getIntervalQuartzListByMap(Map map);

    void updateIntervalQuartzByMap(Map map);
}

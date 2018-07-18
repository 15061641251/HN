package com.sbq.service;

import com.sbq.entity.IntervalQuartz;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/6/13.
 */
public interface IIntervalQuartzService {

    public List<IntervalQuartz> getIntervalQuartzListByMap(Map map);

    public void insertIntervalQuartz(IntervalQuartz intervalQuartz);

    /**
     * 插入实体类,并判断开启实体类相应的定时器
     * @param intervalQuartz
     */
    public void insertAndStartIntervalQuartz(IntervalQuartz intervalQuartz);

    /**
     * 如果定时器未开启,则开启定时器
     *
     * @param intervalQuartz
     */
    public void ifNoStartAndStartIntervalQuartz(IntervalQuartz intervalQuartz);

    /**
     * 更新实体类对象,并停止实体类相关各种的定时器,正常抓取定时器,告警定时器
     *
     * @param intervalQuartz
     */
    public void updateAndStopIntervalQuartz(IntervalQuartz intervalQuartz);

    /**
     * 更新实体类对象,并开启实体类对应的定时器
     *
     * @param intervalQuartz
     */
    public void updateAndStartIntervalQuartz(IntervalQuartz intervalQuartz);

    /**
     * 如果定时器未停止,则停止定时器
     *
     * @param intervalQuartz
     */
    public void ifNoStopAndStopIntervalQuartz(IntervalQuartz intervalQuartz);

}

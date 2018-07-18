package com.sbq.service.impl;

import com.sbq.dao.IIntervalQuartzDao;
import com.sbq.entity.IntervalQuartz;
import com.sbq.quartz.QuartzIntervalManager;
import com.sbq.service.BaseService;
import com.sbq.service.IIntervalQuartzService;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyuan on 2017/6/13.
 */
@Service
public class IntervalQuartzServiceImpl extends BaseService implements IIntervalQuartzService {

    @Autowired
    private QuartzIntervalManager quartzIntervalManager;

    @Autowired
    private IIntervalQuartzDao iIntervalQuartzDao;


    @Override
    public List<IntervalQuartz> getIntervalQuartzListByMap(Map map) {
        return iIntervalQuartzDao.getIntervalQuartzListByMap(map);
    }

    @Override
    public void insertIntervalQuartz(IntervalQuartz intervalQuartz) {
        iIntervalQuartzDao.insert(intervalQuartz);
    }

    @Override
    @Transactional
    public void insertAndStartIntervalQuartz(IntervalQuartz intervalQuartz) {

        iIntervalQuartzDao.insert(intervalQuartz);
        //启动定时器
        _ifNoStartAndStartIntervalQuartz(intervalQuartz);
    }

    private void _ifNoStartAndStartIntervalQuartz(IntervalQuartz intervalQuartz) {
        //启动定时器
        TimeUnit timeUnit;

        switch (intervalQuartz.getTime_type()) {
            case 1:
                timeUnit = TimeUnit.SECONDS;
                break;
            case 2:
                timeUnit = TimeUnit.MINUTES;
                break;
            case 3:
                timeUnit = TimeUnit.HOURS;
                break;
            default:
                timeUnit = TimeUnit.MINUTES;
        }

        try {

            if (Trigger.TriggerState.NONE == quartzIntervalManager.getJobState(intervalQuartz.getJob_name(), intervalQuartz.getJob_group_name(), intervalQuartz.getTrigger_name(), intervalQuartz.getTrigger_group_name())) {
                quartzIntervalManager.addJob(intervalQuartz.getJob_name(), intervalQuartz.getJob_group_name(), intervalQuartz.getTrigger_name(), intervalQuartz.getTrigger_group_name(), Class.forName(intervalQuartz.getBean_class()), timeUnit, intervalQuartz.getValue());

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void _ifNoStopAndStopIntervalQuartz(IntervalQuartz intervalQuartz) {

        //关闭定时器
        if (Trigger.TriggerState.NONE != quartzIntervalManager.getJobState(intervalQuartz.getJob_name(), intervalQuartz.getJob_group_name(), intervalQuartz.getTrigger_name(), intervalQuartz.getTrigger_group_name())) {
            quartzIntervalManager.removeJob(intervalQuartz.getJob_name(), intervalQuartz.getJob_group_name(), intervalQuartz.getTrigger_name(), intervalQuartz.getTrigger_group_name());
        }

    }

    @Override
    public void ifNoStartAndStartIntervalQuartz(IntervalQuartz intervalQuartz) {

        _ifNoStartAndStartIntervalQuartz(intervalQuartz);
    }

    @Override
    public void updateAndStopIntervalQuartz(IntervalQuartz intervalQuartz) {

        iIntervalQuartzDao.updateByPrimaryKey(intervalQuartz);

        _ifNoStopAndStopIntervalQuartz(intervalQuartz);

        //停止任务,连同一般告警定时器一起停止
        intervalQuartz.setTask_key("warn_task_key#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setJob_name("warn_job_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setJob_group_name("warn_job_group_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setTrigger_name("warn_trigger_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + intervalQuartz.getDevice_int_id());

        _ifNoStopAndStopIntervalQuartz(intervalQuartz);

        //停止任务,连同严重告警定时器一起停止
        intervalQuartz.setTask_key("serious_warn_task_key#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setJob_name("serious_warn_job_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setJob_group_name("serious_warn_job_group_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setTrigger_name("serious_warn_trigger_name#" + intervalQuartz.getDevice_int_id());
        intervalQuartz.setTrigger_group_name("serious_warn_trigger_group_name#" + intervalQuartz.getDevice_int_id());

        _ifNoStopAndStopIntervalQuartz(intervalQuartz);

    }

    @Override
    public void updateAndStartIntervalQuartz(IntervalQuartz intervalQuartz) {

        iIntervalQuartzDao.updateByPrimaryKey(intervalQuartz);

        _ifNoStartAndStartIntervalQuartz(intervalQuartz);
    }

    @Override
    public void ifNoStopAndStopIntervalQuartz(IntervalQuartz intervalQuartz) {

        _ifNoStopAndStopIntervalQuartz(intervalQuartz);

    }
}

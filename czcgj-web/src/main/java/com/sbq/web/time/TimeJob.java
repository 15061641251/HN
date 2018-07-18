package com.sbq.web.time;

import com.sbq.service.BaseService;
import com.sbq.tools.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * Created by zhangyuan on 2017/2/20.
 */
public class TimeJob extends BaseService implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("TimeJob-execute...." + DateUtil.getCurrentDateString("HH:mm:ss"));

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        String task_key = jobKey.getGroup() + "#" + jobKey.getName();

        System.out.println(task_key);

    }


}

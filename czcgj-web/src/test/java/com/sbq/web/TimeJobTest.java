package com.sbq.web;


import com.sbq.quartz.QuartzIntervalManager;
import com.sbq.web.time.TimeJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TimeJobTest {

    @Autowired
    private QuartzIntervalManager quartzIntervalManager;

    @Test
    public void test() throws Exception {

        System.out.println("添加65秒任务");

        quartzIntervalManager.addJob("job1", "jobgroup1", "trigger1", "triggergroup1",
                TimeJob.class, TimeUnit.SECONDS, 65);

        System.out.println("添加10秒任务");

        quartzIntervalManager.addJob("job2", "jobgroup2", "trigger2", "triggergroup2",
                TimeJob.class, TimeUnit.SECONDS, 10);


        Thread.sleep(150000);

        System.out.println("修改调整为20秒一次");

        quartzIntervalManager.modifyJobTime("job1", "jobgroup1", "trigger1", "triggergroup1", TimeUnit.SECONDS, 20);

        Thread.sleep(60000);

        System.out.println("去掉任务");

        quartzIntervalManager.removeJob("job1", "jobgroup1", "trigger1", "triggergroup1");

        Thread.sleep(10000);

        System.out.println("添加10秒任务");

        quartzIntervalManager.addJob("job1", "jobgroup1", "trigger1", "triggergroup1",
                TimeJob.class, TimeUnit.SECONDS, 10);


        Thread.sleep(30000);

        System.out.println("停止任务");

        quartzIntervalManager.shutdownJobs();


        Thread.sleep(30000);

    }
}

package com.sbq.quartz;

import org.quartz.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyuan on 2017/6/13.
 */
public class QuartzIntervalManager {

    private Scheduler scheduler;

    /**
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time_type        时间类型
     * @param value            间隔值
     * @Description: 添加一个定时任务
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addJob(String jobName, String jobGroupName,
                       String triggerName, String triggerGroupName, Class jobClass, TimeUnit time_type, int value) {

        String task_key = "";
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();

            // 触发器时间设定
            switch (time_type) {
                case HOURS:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(value));
                    break;
                case MINUTES:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(value));
                    break;
                case SECONDS:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(value));
                    break;
                default:
                    break;
            }

            // 创建Trigger对象
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取job详情
     * STATE_BLOCKED 4 阻塞
     * STATE_COMPLETE 2 完成
     * STATE_ERROR 3 错误
     * STATE_NONE -1 不存在
     * STATE_NORMAL 0 正常
     * STATE_PAUSED 1 暂停
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @return
     */
    public Trigger.TriggerState getJobState(String jobName,
                                            String jobGroupName, String triggerName, String triggerGroupName) {

        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            return scheduler.getTriggerState(triggerKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Trigger.TriggerState.NONE;
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param time_type        时间类型
     * @param value            间隔值
     * @Description: 修改一个任务的触发时间
     */
    public void modifyJobTime(String jobName,
                              String jobGroupName, String triggerName, String triggerGroupName, TimeUnit time_type, int value) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            /** 方式一 ：调用 rescheduleJob 开始 */
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            switch (time_type) {
                case HOURS:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(value));
                    break;
                case MINUTES:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(value));
                    break;
                case SECONDS:
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(value));
                    break;
                default:
                    break;
            }
            // 创建Trigger对象
            trigger = (SimpleTrigger) triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
            /** 方式一 ：调用 rescheduleJob 结束 */

            /** 方式二：先删除，然后在创建一个新的Job  */
            //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            //Class<? extends Job> jobClass = jobDetail.getJobClass();
            //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
            /** 方式二 ：先删除，然后在创建一个新的Job */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @Description: 移除一个任务
     */
    public void removeJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


}

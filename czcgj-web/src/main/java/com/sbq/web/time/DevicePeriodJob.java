package com.sbq.web.time;

import com.sbq.entity.Device;
import com.sbq.entity.DeviceLog;
import com.sbq.entity.IntervalQuartz;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.service.*;
import com.sbq.tools.CzcgUtil;
import com.sbq.tools.DateUtil;
import com.sbq.tools.HttpUtil;
import com.sbq.tools.SpringContext;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONException;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DevicePeriodJob extends BaseService implements Job {

    private static IDeviceService deviceService;

    private static IDeviceLogService deviceLogService;

    private static IDeviceWarnRuleService deviceWarnRuleService;

    private static IIntervalQuartzService intervalQuartzService;

    private static ICzcgService czcgService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (deviceService == null) {
            deviceService = (IDeviceService) SpringContext.getBean("deviceServiceImpl");
        }

        if (deviceLogService == null) {
            deviceLogService = (IDeviceLogService) SpringContext.getBean("deviceLogServiceImpl");
        }

        if (deviceWarnRuleService == null) {
            deviceWarnRuleService = (IDeviceWarnRuleService) SpringContext.getBean("deviceWarnRuleServiceImpl");
        }

        if (intervalQuartzService == null) {
            intervalQuartzService = (IIntervalQuartzService) SpringContext.getBean("intervalQuartzServiceImpl");
        }

        if (czcgService == null) {
            czcgService = (ICzcgService) SpringContext.getBean("czcgServiceImpl");
        }


        JobKey jobKey = context.getJobDetail().getKey();

        String task_key = jobKey.getGroup() + "#" + jobKey.getName();

        logger.info("DevicePeriodJob-execute...." + DateUtil.getCurrentDateString("HH:mm:ss") + task_key);

        Long device_int_id = Long.valueOf(jobKey.getName().split("#")[1]);

        if (device_int_id != null) {

            Map param = new HashMap<>();
            param.put("int_id", device_int_id);

            DeviceDto device = deviceService.getInfoByMap(param);

            if (device != null && StringUtils.isNoneBlank(device.getApi_url(), device.getApi_key())) {

                //请求onnet平台数据
                Map headers = new HashMap();
                headers.put("api-key", device.getApi_key());

                String get_url = device.getApi_url() + "/datapoints?limit=1";//约定只取最新一条数据
                String result = HttpUtil.get(get_url, headers);

                handle(result, device);

            }

        }

    }

    /**
     * 处理数据,转换成key/value存入库
     * 并根据规则判断是否是报警信息
     */
    private void handle(String json, DeviceDto device) {

//        json = "{\"errno\":0,\"data\":{\"count\":6,\"datastreams\":[{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"1\"}],\"id\":\"move\"},{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"0\"}],\"id\":\"liquid\"},{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"lat 21164 ci 20642\"}],\"id\":\"pos\"},{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"accex 31.20 accey -2020.20 accez 1076.40\"}],\"id\":\"acce\"},{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"3.68\"}],\"id\":\"batvoit\"},{\"datapoints\":[{\"at\":\"2017-09-23 06:33:25.000\",\"value\":\"43200\"}],\"id\":\"cycle\"}]},\"error\":\"succ\"}";

        JSONObject jsonObject = new JSONObject(json);

        int errno = (int) jsonObject.getObj("errno");

        if (errno == 0) {

            JSONArray datastreams = jsonObject.getJSONObject("data").getJSONArray("datastreams");

            JSONArray resultArray = new JSONArray();

            String data = "";

            String logtime = "";

            for (int i = 0; i < datastreams.size(); i++) {

                JSONObject datastream = datastreams.getJSONObject(i);

                String key = (String) datastream.getObj("id", "");

                JSONArray datapointArray = datastream.getJSONArray("datapoints");

                JSONObject datapoint = datapointArray.getJSONObject(0);//只取第一条数据


                JSONObject object = new JSONObject();

                object.put("id", key);

                object.put("value", datapoint.getObj("value", ""));

                object.put("at", datapoint.getObj("at", ""));

                if (StringUtils.isBlank(logtime)) {
                    logtime = (String) datapoint.getObj("at", "");
                }

                //有时间比当前时间大的，更新为大的
                if (logtime.compareTo((String) datapoint.getObj("at", "")) < 0) {
                    logtime = (String) datapoint.getObj("at", "");
                }

                resultArray.add(object);

                data = resultArray.toString();//最终入库结果

            }

            if (StringUtils.isNoneBlank(data)) {

                DeviceLog deviceLog = new DeviceLog();
                deviceLog.setData(data);
                deviceLog.setDevice_int_id(device.getInt_id());
                deviceLog.setDevice_id(device.getDevice_id());
                deviceLog.setLog_time(DateUtil.getDate(logtime, "yyyy-MM-dd HH:mm:ss"));
                deviceLog.setDevice_type(device.getDevice_type());


                //判断是否符合告警规则
                if (device.getCompany_id() == null || (device.getCompany_id() != null && device.getCompany_id() != 4)) {

                    //不是城管局的设备,城管局主键是4
                    /**
                     * 城管的告警分为一般告警和严重告警，下面代码适用一种告警并匹配规则设置中的逻辑
                     */
                    //
                    boolean is_warn = deviceWarnRuleService.is_warn(deviceLog);

                    Integer last_warn_stauts = device.getLast_warn_status();

                    Long device_int_id = device.getInt_id();

                    IntervalQuartz intervalQuartz = new IntervalQuartz();

                    intervalQuartz.setDevice_int_id(device_int_id);
                    intervalQuartz.setTask_key("warn_task_key#" + device_int_id);
//                    intervalQuartz.setStatus(1);
                    intervalQuartz.setBean_class(DevicePeriodJob.class.getName());
                    intervalQuartz.setJob_name("warn_job_name#" + device_int_id);
                    intervalQuartz.setJob_group_name("warn_job_group_name#" + device_int_id);
                    intervalQuartz.setTrigger_name("warn_trigger_name#" + device_int_id);
                    intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + device_int_id);
                    intervalQuartz.setTime_type(2);
                    intervalQuartz.setValue(device.getWarn_period());

                    if (is_warn) {
                        //告警
                        deviceLog.setStatus(1);

                        //判断是否已有,如果没有则启动告警的定时任务
                        intervalQuartzService.ifNoStartAndStartIntervalQuartz(intervalQuartz);

                        if (last_warn_stauts == 0) {
                            //上次正常

                            //更新最后一次状态
                            Device device1 = new Device();
                            BeanUtils.copyProperties(device, device1);
                            device1.setLast_warn_status(1);

                            deviceService.updateDevice(device1);
                        }

                    } else {
                        //正常
                        deviceLog.setStatus(0);

                        //判断是否已有,已有的话停止告警定时任务
                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                        if (last_warn_stauts == 1) {
                            //上一次不正常,这次正常了

                            //更新最后一次状态
                            Device device1 = new Device();
                            BeanUtils.copyProperties(device, device1);
                            device1.setLast_warn_status(0);

                            deviceService.updateDevice(device1);
                        }
                    }
                    //

                } else {
                    /**
                     * 城管的告警用下面的规则
                     *
                     */
                    //获取各个方面的角度

                    JSONArray array = null;
                    try {
                        array = new JSONArray(data);
                    } catch (JSONException e) {
//                        e.printStackTrace();
                    }

                    if (array == null) {
                        return;
                    }

                    CzcgLogDto czcgLogDto = null;

                    //TODO: 根据设备类型进行区分
                    czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array, device.getDevice_id(), device.getDevice_type());

                    Float warn_angle = device.getWarn_angle();//一般告警角度
                    Float serious_warn_angle = device.getSerious_warn_angle();//严重告警角度

                    ////////////////////////////////////////
//                    if ("10826047".equals(device.getDevice_id())) {
//                        czcgLogDto.setX(czcgLogDto.getX() + 4.0);
//                        czcgLogDto.setY(czcgLogDto.getY() + 4.0);
//                        czcgLogDto.setZ(czcgLogDto.getZ() + 4.0);
//                    }
                    ////////////////////////////////////////

                    if (Math.abs(czcgLogDto.getX()) >= serious_warn_angle || Math.abs(czcgLogDto.getY()) >= serious_warn_angle || Math.abs(czcgLogDto.getZ()) >= serious_warn_angle) {
                        //如果任何一个角度符合严重告警规则,则启动严重告警定时器,更新状态为严重告警状态和告警时间,关闭当前定时器,并调用一次严重告警接口

                        deviceLog.setStatus(1);

                        //过滤不告警的设备
//                        if ("10826047".equals(device.getDevice_id())) {
                        //启动严重告警定时器
                        IntervalQuartz intervalQuartz = new IntervalQuartz();

                        intervalQuartz.setDevice_int_id(device.getInt_id());
//                        intervalQuartz.setStatus(1);
                        intervalQuartz.setTime_type(2);
                        intervalQuartz.setValue(device.getSerious_warn_period());
                        intervalQuartz.setBean_class(SeriousPeriodJob.class.getName());
                        intervalQuartz.setTask_key("serious_warn_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("serious_warn_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("serious_warn_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("serious_warn_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("serious_warn_trigger_group_name#" + device.getInt_id());

                        intervalQuartzService.ifNoStartAndStartIntervalQuartz(intervalQuartz);

                        //更新状态为严重告警状态和告警时间
                        Device device1 = new Device();
                        BeanUtils.copyProperties(device, device1);
                        device1.setLast_warn_status(2);//严重告警
                        device1.setLast_warn_time(new Date());

                        deviceService.updateDevice(device1);

                        //关闭当前定时器
                        intervalQuartz.setTask_key("task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("trigger_group_name#" + device.getInt_id());
                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                        //调用一次严重告警接口
                        czcgService.pushWarn(device.getDevice_id(), 4, czcgLogDto, device);
//                        }

                    } else if (Math.abs(czcgLogDto.getX()) >= warn_angle || Math.abs(czcgLogDto.getY()) >= warn_angle || Math.abs(czcgLogDto.getZ()) >= warn_angle) {
                        //如果任何一个角度符合一般告警规则,则启动一般告警定时器,更新状态为一般告警状态和告警时间,关闭当前定时器,并调用一次一般告警接口

                        deviceLog.setStatus(1);

//                        if ("10826047".equals(device.getDevice_id())) {

                        //启动一般告警定时器
                        IntervalQuartz intervalQuartz = new IntervalQuartz();

                        intervalQuartz.setDevice_int_id(device.getInt_id());
//                        intervalQuartz.setStatus(1);
                        intervalQuartz.setTime_type(2);
                        intervalQuartz.setValue(device.getWarn_period());
                        intervalQuartz.setBean_class(NormalPeriodJob.class.getName());
                        intervalQuartz.setTask_key("warn_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("warn_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("warn_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("warn_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + device.getInt_id());

                        intervalQuartzService.ifNoStartAndStartIntervalQuartz(intervalQuartz);

                        //更新状态为一般告警状态和告警时间
                        Device device1 = new Device();
                        BeanUtils.copyProperties(device, device1);
                        device1.setLast_warn_status(1);//一般告警
                        device1.setLast_warn_time(new Date());

                        deviceService.updateDevice(device1);

                        //关闭当前定时器
                        intervalQuartz.setTask_key("task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("trigger_group_name#" + device.getInt_id());
                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                        //调用一次一般告警接口
                        czcgService.pushWarn(device.getDevice_id(), 2, czcgLogDto, device);
//                        }

                    } else {

                        //如果角度正常,则关闭一般告警定时器和严重告警定时器

                        deviceLog.setStatus(0);

//                        if ("10826047".equals(device.getDevice_id())) {

                        IntervalQuartz intervalQuartz = new IntervalQuartz();

                        //停止一般告警定时器
                        intervalQuartz.setTask_key("warn_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("warn_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("warn_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("warn_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + device.getInt_id());

                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                        //停止严重告警定时器
                        intervalQuartz.setTask_key("serious_warn_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("serious_warn_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("serious_warn_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("serious_warn_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("serious_warn_trigger_group_name#" + device.getInt_id());

                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

//                        }

                    }
                }

                deviceLogService.insetDeviceLog(deviceLog);
            }

        }

    }

    public static void main(String[] args) {

//        new DevicePeriodJob().handle("");
    }


}

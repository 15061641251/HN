package com.sbq.web.time;

import com.sbq.entity.Device;
import com.sbq.entity.DeviceLog;
import com.sbq.entity.IntervalQuartz;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.service.*;
import com.sbq.tools.CzcgUtil;
import com.sbq.tools.HttpUtil;
import com.sbq.tools.SpringContext;
import com.xiaoleilu.hutool.date.DateField;
import com.xiaoleilu.hutool.date.DateUnit;
import com.xiaoleilu.hutool.date.DateUtil;
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

/**
 * 一般告警定时器,默认10分钟一次,不入具体业务表,系统启动不加载,只加载正常定时器
 * 业务逻辑:
 * 判断是否告警:
 * -----如果不告警,则更新状态,关闭一般告警定时器,启动正常定时器
 * -----如果告警:
 * ---------获取当前状态是否被告警清除接口置为正常状态
 * ---------如果是正常状态：
 * -------------则在no_tip_minutes + last_warn_time内不再调用告警接口,如果超过时间范围,则更新状态为一般告警状态和告警时间,再调用一次一般告警接口
 * ---------如果状态还是一般告警状态:
 * -------------则升级为严重告警状态,关闭一般告警定时器,更新状态为严重告警状态和告警时间,启动严重告警定时器,调用一次严重告警接口</p>
 */
public class NormalPeriodJob extends BaseService implements Job {

    private static IDeviceService deviceService;

    private static IDeviceLogService deviceLogService;

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

        if (intervalQuartzService == null) {
            intervalQuartzService = (IIntervalQuartzService) SpringContext.getBean("intervalQuartzServiceImpl");
        }

        if (czcgService == null) {
            czcgService = (ICzcgService) SpringContext.getBean("czcgServiceImpl");
        }

        JobKey jobKey = context.getJobDetail().getKey();

        String task_key = jobKey.getGroup() + "#" + jobKey.getName();


        logger.info("NormalPeriodJob-execute...." + DateUtil.now() + task_key);

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

    private void handle(String json, DeviceDto device) {

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
                deviceLog.setLog_time(DateUtil.parse(logtime, "yyyy-MM-dd HH:mm:ss"));
                deviceLog.setDevice_type(device.getDevice_type());

                JSONArray array = null;
                try {
                    array = new JSONArray(data);
                } catch (JSONException e) {
//                        e.printStackTrace();
                }

                if (array == null) {
                    return;
                }

                CzcgLogDto czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array,device.getDevice_id(),device.getDevice_type());

                Float warn_angle = device.getWarn_angle();//一般告警角度
                Float serious_warn_angle = device.getSerious_warn_angle();//严重告警角度

                ////////////////////////////////////////
//                if ("10826047".equals(device.getDevice_id())) {
//                    czcgLogDto.setX(czcgLogDto.getX() + 4.0);
//                    czcgLogDto.setY(czcgLogDto.getY() + 4.0);
//                    czcgLogDto.setZ(czcgLogDto.getZ() + 4.0);
//                }
                ////////////////////////////////////////

                if (Math.abs(czcgLogDto.getX()) >= warn_angle || Math.abs(czcgLogDto.getY()) >= warn_angle || Math.abs(czcgLogDto.getZ()) >= warn_angle) {
                    //如果告警
                    deviceLog.setStatus(1);

                    if (device.getLast_warn_status() == 0) {
                        //正常状态
                        Date last_warn_time = device.getLast_warn_time();

                        Date time = DateUtil.offset(last_warn_time, DateField.MINUTE, device.getNo_tip_minute());

                        if (DateUtil.between(new Date(), time, DateUnit.SECOND) > 0) {
                            //在no_tip_minutes + last_warn_time内不再调用告警接口

                        } else {
                            //如果超过时间范围,则更新状态为一般告警状态和告警时间,再调用一次一般告警接口
                            Device device1 = new Device();
                            BeanUtils.copyProperties(device, device1);
                            device1.setLast_warn_status(1);//一般告警
                            device1.setLast_warn_time(new Date());
                            deviceService.updateDevice(device1);

                            //调用一般告警接口
                            czcgService.pushWarn(device.getDevice_id(), 2, czcgLogDto, device);
                        }

                    } else if (device.getLast_warn_status() == 1) {
                        //如果状态还是一般告警状态
                        IntervalQuartz intervalQuartz = new IntervalQuartz();

                        //关闭一般告警定时器
                        intervalQuartz.setTask_key("warn_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("warn_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("warn_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("warn_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + device.getInt_id());

                        intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                        Device device1 = new Device();
                        BeanUtils.copyProperties(device, device1);
                        device1.setLast_warn_status(2);//严重告警
                        device1.setLast_warn_time(new Date());
                        deviceService.updateDevice(device1);

                        //启动严重告警定时器
//                        intervalQuartz.setStatus(1);
                        intervalQuartz.setTime_type(2);
                        intervalQuartz.setValue(device.getSerious_warn_period());
                        intervalQuartz.setBean_class(SeriousPeriodJob.class.getName());
                        intervalQuartz.setTask_key("serious_task_key#" + device.getInt_id());
                        intervalQuartz.setJob_name("serious_job_name#" + device.getInt_id());
                        intervalQuartz.setJob_group_name("serious_job_group_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_name("serious_trigger_name#" + device.getInt_id());
                        intervalQuartz.setTrigger_group_name("serious_trigger_group_name#" + device.getInt_id());
                        intervalQuartzService.ifNoStartAndStartIntervalQuartz(intervalQuartz);

                        //调用一次严重告警接口
                        czcgService.pushWarn(device.getDevice_id(), 4, czcgLogDto, device);
                    }

                    deviceLog.setStatus(1);

                } else {

                    //如果不告警,则更新状态,关闭一般告警定时器,启动正常定时器

                    deviceLog.setStatus(0);

                    Device device1 = new Device();
                    BeanUtils.copyProperties(device, device1);
                    device1.setLast_warn_status(0);//不告警

                    deviceService.updateDevice(device1);

                    //关闭一般告警定时器
                    IntervalQuartz intervalQuartz = new IntervalQuartz();

                    intervalQuartz.setTask_key("warn_task_key#" + device.getInt_id());
                    intervalQuartz.setJob_name("warn_job_name#" + device.getInt_id());
                    intervalQuartz.setJob_group_name("warn_job_group_name#" + device.getInt_id());
                    intervalQuartz.setTrigger_name("warn_trigger_name#" + device.getInt_id());
                    intervalQuartz.setTrigger_group_name("warn_trigger_group_name#" + device.getInt_id());

                    intervalQuartzService.ifNoStopAndStopIntervalQuartz(intervalQuartz);

                    //启动正常定时器
//                    intervalQuartz.setStatus(1);
                    intervalQuartz.setTime_type(2);
                    intervalQuartz.setValue(device.getPeriod());
                    intervalQuartz.setBean_class(DevicePeriodJob.class.getName());
                    intervalQuartz.setTask_key("task_key#" + device.getInt_id());
                    intervalQuartz.setJob_name("job_name#" + device.getInt_id());
                    intervalQuartz.setJob_group_name("job_group_name#" + device.getInt_id());
                    intervalQuartz.setTrigger_name("trigger_name#" + device.getInt_id());
                    intervalQuartz.setTrigger_group_name("trigger_group_name#" + device.getInt_id());
                    intervalQuartzService.ifNoStartAndStartIntervalQuartz(intervalQuartz);
                }

                deviceLogService.insetDeviceLog(deviceLog);
            }
        }
    }
}

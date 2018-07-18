package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.IDeviceDao;
import com.sbq.dao.IDeviceLogDao;
import com.sbq.dao.IDeviceWarnRuleDao;
import com.sbq.entity.DeviceLog;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.entity.dto.DeviceLogDto;
import com.sbq.service.BaseService;
import com.sbq.service.IDeviceLogService;
import com.sbq.service.IDeviceWarnRuleService;
import com.sbq.tools.DateUtil;
import com.sbq.tools.HttpUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceLogServiceImpl extends BaseService implements IDeviceLogService {

    @Autowired
    private IDeviceWarnRuleService deviceWarnRuleService;

    @Autowired
    private IDeviceLogDao deviceLogDao;

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public void insetDeviceLog(DeviceLog deviceLog) {
//        deviceLogDao.insert(deviceLog);
        deviceLogDao.ifNoExistAndInsert(deviceLog);
    }

    @Override
    public PageInfo<DeviceLogDto> getDeviceLogListByPage(Map<String, Object> map) {

        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));


        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<DeviceLogDto> list = deviceLogDao.getDeviceLogListByMap(map);

        return new PageInfo<DeviceLogDto>(list);
    }

    @Override
    public void importLog(Map map) {

        Long device_int_id = (Long) map.get("device_int_id");

        Date log_start_time = (Date) map.get("log_start_time");
        Date log_end_time = (Date) map.get("log_end_time");

        //2.start:表示提取数据点的开始时间，格式为2015-01-10T08:00:35
        //3.end:表示提取数据点的结束时间，格式为2015-01-10T08:00:35
        String start = DateUtil.getDateString(log_start_time, "yyyy-MM-dd'T'HH:mm:ss");
        String end = DateUtil.getDateString(log_end_time, "yyyy-MM-dd'T'HH:mm:ss");

        Map param = new HashMap();
        param.put("int_id", device_int_id);

        DeviceDto device = deviceDao.getInfoByMap(param);

        if (device != null && StringUtils.isNoneBlank(device.getApi_url(), device.getApi_key())) {

            //请求onnet平台数据
            Map headers = new HashMap();
            headers.put("api-key", device.getApi_key());

            String get_url = device.getApi_url() + "/datapoints?limit=999999&start=" + start + "&end=" + end;
            String result = HttpUtil.get(get_url, headers);

            handle(result, device);

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


            int datastreams_size = datastreams.size(); //数据流点
            int datapoints_size = 0;//数据条数


            if (datastreams_size > 0) {
                JSONObject datastream = datastreams.getJSONObject(0);
                JSONArray datapointArray = datastream.getJSONArray("datapoints");

                datapoints_size = datapointArray.size();
            }

            for (int i = 0; i < datapoints_size; i++) {

                JSONArray resultArray = new JSONArray();

                String data = "";
                String logtime = "";

                for (int j = 0; j < datastreams_size; j++) {

                    JSONObject object = new JSONObject();

                    JSONObject datastream = datastreams.getJSONObject(j);
                    String key = (String) datastream.getObj("id", "");
                    JSONArray datapointArray = datastream.getJSONArray("datapoints");
                    JSONObject datapoint = datapointArray.getJSONObject(i);

                    object.put("id", key);

                    if (datapoint == null) {
                        continue;
                    }
                    object.put("value", datapoint.getObj("value", ""));
                    object.put("at", datapoint.getObj("at", ""));

                    logtime = (String) datapoint.getObj("at", "");

                    resultArray.add(object);

                }

                data = resultArray.toString();

                if (StringUtils.isNoneBlank(data)) {

                    DeviceLog deviceLog = new DeviceLog();
                    deviceLog.setData(data);
                    deviceLog.setDevice_int_id(device.getInt_id());
                    deviceLog.setDevice_id(device.getDevice_id());
                    deviceLog.setLog_time(DateUtil.getDate(logtime, "yyyy-MM-dd HH:mm:ss"));
                    deviceLog.setDevice_type(device.getDevice_type());

                    //判断是否符合告警规则
                    boolean is_warn = deviceWarnRuleService.is_warn(deviceLog);

                    Integer last_warn_stauts = device.getLast_warn_status();

                    Long device_int_id = device.getInt_id();

                    if (is_warn) {
                        //告警
                        deviceLog.setStatus(1);

                    } else {
                        //正常
                        deviceLog.setStatus(0);

                    }

                    insetDeviceLog(deviceLog);
                }

            }


        }

    }
}

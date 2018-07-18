package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.dto.DeviceLogDto;
import com.sbq.service.IDeviceLogService;
import com.sbq.service.IDeviceWarnRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("deviceLog")
public class DeviceLogController extends BaseController {

    @Autowired
    private IDeviceLogService deviceLogService;

    @Autowired
    private IDeviceWarnRuleService deviceWarnRuleService;

    @RequestMapping("manager")
    public String manager() {

        return "devicelog/logManager";
    }

    @RequestLog(moduleName = "获取设备日志列表")
    @RequestMapping("/getDeviceLogListByPage")
    @ResponseBody
    public Object getDeviceLogListByPage(@RequestParam(value = "pageIndex", required = false) Integer pageIndex, @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(value = "log_start_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_start_time,
                                         @RequestParam(value = "log_end_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_end_time,
                                         @RequestParam(value = "status", required = false) Integer status,
                                         @RequestParam("device_int_id") Long device_int_id) {

        if (device_int_id == null) {

            return renderRequestError();
        }

        Map map = new HashMap<>();
        map.put("device_int_id", device_int_id);

        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        if (null != log_start_time) {
            map.put("log_start_time", log_start_time);
        }
        if (null != log_end_time) {
            map.put("log_end_time", log_end_time);
        }
        if (null != status) {
            map.put("status", status);
        }

        PageInfo<DeviceLogDto> pageInfo = deviceLogService.getDeviceLogListByPage(map);

        return renderSuccess(pageInfo);
    }

    @RequestMapping("/importLogHome")
    public String importLogHome() {

        return "devicelog/importLog";
    }

    @RequestLog(moduleName = "补录日志数据")
    @RequestMapping("/importLog")
    @ResponseBody
    public Object importLog(@RequestParam(value = "log_start_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_start_time,
                            @RequestParam(value = "log_end_time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_end_time,
                            @RequestParam("device_int_id") Long device_int_id) {


        Map param = new HashMap();

        if (log_start_time == null || log_end_time == null || device_int_id == null) {
            return renderRequestError();
        }

        param.put("log_start_time", log_start_time);
        param.put("log_end_time", log_end_time);
        param.put("device_int_id", device_int_id);

        deviceLogService.importLog(param);
        return renderSuccess();
    }

}

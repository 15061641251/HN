package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.DeviceWarnRule;
import com.sbq.service.IDeviceWarnRuleService;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("deviceWarnRule")
public class DeviceWarnRuleController extends BaseController {

    @Autowired
    private IDeviceWarnRuleService deviceWarnRuleService;


    @RequestMapping("manager")
    public String manager() {
        return "deviceWarnRule/ruleManager";
    }

    @RequestLog(moduleName = "获取设备告警规则")
    @RequestMapping("getDeviceWarnRuleList")
    @ResponseBody
    public Object getDeviceWarnRuleList(@RequestParam("device_int_id") Long device_int_id) {

        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("device_int_id", device_int_id);

        List<DeviceWarnRule> deviceWarnRuleList = deviceWarnRuleService.getDeviceWarnRuleListByMap(param);

        return renderSuccess(deviceWarnRuleList);
    }

    @RequestLog(moduleName = "保存设备告警规则")
    @RequestMapping(value = "saveDeviceWarnRule", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDeviceWarnRule(@RequestParam("device_int_id") Long device_int_id, @RequestParam("ruleList") String json) {

        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("device_int_id", device_int_id);


        JSONArray array = JSONUtil.parseArray(json);

        List<DeviceWarnRule> deviceWarnRuleList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {

            JSONObject object = array.getJSONObject(i);
            DeviceWarnRule deviceWarnRule = new DeviceWarnRule();

            jsonObjectToDeviceWarnRule(object, deviceWarnRule);

            deviceWarnRuleList.add(deviceWarnRule);
        }

        if (deviceWarnRuleList == null || deviceWarnRuleList.size() <= 0) {
            deviceWarnRuleService.clearDeviceWarnRuleByMap(param);
        } else {
            param.put("deviceWarnRuleList", deviceWarnRuleList);

            deviceWarnRuleService.clearAndInsertDeviceWarnRuleByMap(param);
        }


        return renderSuccess();
    }

    private void jsonObjectToDeviceWarnRule(JSONObject object, DeviceWarnRule deviceWarnRule) {

        Long device_int_id = object.getLong("device_int_id");
        Integer condition_type = object.getInt("condition_type");
        String data_key = object.getStr("data_key");
        String data_value = object.getStr("data_value");

        deviceWarnRule.setData_key(data_key);
        deviceWarnRule.setCondition_type(condition_type);
        deviceWarnRule.setData_value(data_value);
        deviceWarnRule.setDevice_int_id(device_int_id);
    }

}

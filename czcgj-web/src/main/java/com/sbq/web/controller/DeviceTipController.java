package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.Device;
import com.sbq.entity.DeviceTip;
import com.sbq.entity.DeviceWarnRule;
import com.sbq.service.IDeviceTipService;
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
@RequestMapping("deviceTip")
public class DeviceTipController extends BaseController {

    @Autowired
    private IDeviceTipService deviceTipService;

    @RequestMapping("manager")
    public String manager() {
        return "devicetip/tipManager";
    }

    @RequestLog(moduleName = "获取设备提醒设置列表")
    @RequestMapping("getDeviceTipList")
    @ResponseBody
    public Object getDeviceTipList(@RequestParam("device_int_id") Long device_int_id) {

        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("device_int_id", device_int_id);

        List<DeviceTip> deviceTipList = deviceTipService.getDeviceTipListByMap(param);

        return renderSuccess(deviceTipList);
    }

    @RequestLog(moduleName = "设置设备提醒")
    @RequestMapping(value = "saveDeviceTip", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDeviceWarnRule(@RequestParam("device_int_id") Long device_int_id, @RequestParam(value = "no_tip_minute", defaultValue = "0") Integer no_tip_minute, @RequestParam("tipList") String json) {

        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("device_int_id", device_int_id);


        JSONArray array = JSONUtil.parseArray(json);

        List<DeviceTip> deviceTipList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {

            JSONObject object = array.getJSONObject(i);
            DeviceTip deviceTip = new DeviceTip();

            jsonObjectToDeviceTip(object, deviceTip);

            deviceTipList.add(deviceTip);
        }

        if (deviceTipList == null || deviceTipList.size() <= 0) {

            param.put("no_tip_minute", no_tip_minute);
            deviceTipService.clearDeviceTipByMap(param);

        } else {

            param.put("deviceTipList", deviceTipList);
            param.put("no_tip_minute", no_tip_minute);

            deviceTipService.clearAndInsertDeviceTipByMap(param);
        }


        return renderSuccess();
    }

    private void jsonObjectToDeviceTip(JSONObject object, DeviceTip deviceTip) {

        Long device_int_id = object.getLong("device_int_id");
        Integer status = object.getInt("status");
        String phone_num = object.getStr("phone_num");

        deviceTip.setDevice_int_id(device_int_id);
        deviceTip.setStatus(status);
        deviceTip.setPhone_num(phone_num);
    }

}

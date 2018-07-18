package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.service.IDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 对外提供的服务和页面入口
 */
@Controller
@RequestMapping("api")
public class ApiController extends BaseController {

    @Autowired
    private IDeviceService deviceService;

    /**
     * 设备详情界面入口
     *
     * @param request
     * @return
     */
    @RequestMapping("deviceDetail")
    public String deviceDetail(HttpServletRequest request) {

        return "api/showDevice";

    }

    @RequestLog(moduleName = "api:根据设备编号获取设备详情")
    @RequestMapping("getDeviceDetailByDeviceId")
    @ResponseBody
    public Object getDeviceDetailByDeviceId(@RequestParam("deviceId") String deviceId) {

        if (StringUtils.isBlank(deviceId)) {
            return renderRequestError();
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("device_id", deviceId);

        DeviceDto device = deviceService.getInfoByMap(param);

        return renderSuccess(device);
    }

    @RequestLog(moduleName = "api:告警清除")
    @RequestMapping(value = "clearDeviceWarn", method = RequestMethod.POST)
    @ResponseBody
    public Object clearDeviceWarn(@RequestParam("deviceId") String deviceId) {

        if (StringUtils.isBlank(deviceId)) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("device_id", deviceId);

        //查询是否存在
        DeviceDto deviceDto = deviceService.getInfoByMap(param);

        if (deviceDto == null) {
            return renderRequestError("设备不存在");
        }

        param.put("last_warn_status", 0);

        deviceService.updateDeviceByDeviceId(param);

        return renderSuccess();
    }
}

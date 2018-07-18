package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.Device;
import com.sbq.entity.IntervalQuartz;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IDeviceService;
import com.sbq.service.IIntervalQuartzService;
import com.sbq.tools.Constant;
import com.sbq.web.time.DevicePeriodJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备管理控制器
 */
@Controller
@RequestMapping(value = "device")
public class DeviceController extends BaseController {

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IIntervalQuartzService iIntervalQuartzService;

    @RequestMapping("manager")
    public String manager() {

        return "device/deviceManager";
    }

    @RequestMapping("deviceDetail")
    public String deviceDetail(HttpServletRequest request) {

        return "device/showDevice";
    }

    @RequestLog(moduleName = "根据设备主键获取设备详情")
    @RequestMapping("getDeviceDetail")
    @ResponseBody
    public Object getDeviceDetail(@RequestParam("deviceId") Long deviceId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("int_id", deviceId);

        DeviceDto device = deviceService.getInfoByMap(param);

        return renderSuccess(device);
    }

    @RequestLog(moduleName = "根据设备ID获取设备详情")
    @RequestMapping("getDeviceDetailByDeviceId")
    @ResponseBody
    public Object getDeviceDetailByDeviceId(@RequestParam("deviceId") Long deviceId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("device_id", deviceId);

        DeviceDto device = deviceService.getInfoByMap(param);

        return renderSuccess(device);
    }

    @RequestLog(moduleName = "获取设备列表")
    @RequestMapping("/getDeviceListByPage")
    @ResponseBody
    public Object getDeviceListByPage(@RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                                      @RequestParam(value = "device_name", required = false) String device_name,
                                      @RequestParam(value = "area_name", required = false) String area_name,
                                      @RequestParam(value = "device_id", required = false) String device_id,
                                      @RequestParam(value = "company_name", required = false) String company_name,
                                      HttpSession session) throws UnsupportedEncodingException {


        UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

        Map<String, Object> parammap = new HashMap<String, Object>();

        parammap.put("pageIndex", pageIndex);
        parammap.put("pageSize", pageSize);

        if (StringUtils.isNotEmpty(device_name)) {
            device_name = URLDecoder.decode(device_name, "UTF-8");
            parammap.put("device_name", device_name);
        }

        if (StringUtils.isNotEmpty(company_name)) {
            company_name = URLDecoder.decode(company_name, "UTF-8");
            parammap.put("company_name", company_name);
        }

        if (StringUtils.isNotEmpty(area_name)) {
            area_name = URLDecoder.decode(area_name, "UTF-8");
            parammap.put("area_name", area_name);
        }

        if (StringUtils.isNotEmpty(device_id)) {
            device_id = URLDecoder.decode(device_id, "UTF-8");
            parammap.put("device_id", device_id);
        }

        if (userDto.getCompany_id() != null) {
            parammap.put("company_id", userDto.getCompany_id());
        }

        PageInfo<DeviceDto> pageInfo = deviceService.getDeviceListByPage(parammap);
        return renderSuccess(pageInfo);

    }

    @RequestMapping("/editDevice")
    public String editDevice(@RequestParam("deviceId") String deviceId) {
        return "/device/editDevice";
    }

    @RequestLog(moduleName = "修改设备信息")
    @RequestMapping("editDeviceById")
    @ResponseBody
    public Object editDeviceById(Device device) {

        if (device == null || device.getInt_id() == null) {
            return renderRequestError();
        }
        deviceService.updateDevice(device);

        return renderSuccess();
    }

    @RequestLog(moduleName = "删除设备信息")
    @RequestMapping(value = "/delDevice", method = RequestMethod.POST)
    public
    @ResponseBody
    Object delDevice(@RequestParam("deviceId") Long deviceId) {

        Map<String, String> resultMap = new HashMap<String, String>();

        if (deviceId == null) {
            return renderRequestError();
        } else {
            // 删除成功
            deviceService.delDeviceById(deviceId);
            return renderSuccess();
        }

    }

    @RequestMapping(value = "/addDevice")
    public String addDevice() {
        return "/device/addDevice";
    }

    @RequestLog(moduleName = "添加设备")
    @RequestMapping(value = "/addDevicePost")
    public
    @ResponseBody
    Object addDevicePost(Device device, HttpSession session) {

        if (device != null && StringUtils.isNoneBlank(device.getDevice_name())) {


            UserDto userDto = (UserDto) session.getAttribute(Constant.SESSION_ATTRIBUTE_USER);

            device.setCompany_id(userDto.getCompany_id());

            deviceService.addDevice(device);

            return renderSuccess();
        } else {
            return renderRequestError();
        }

    }

    /**
     * 开始调度任务
     *
     * @return
     */
    @RequestLog(moduleName = "开始任务调度")
    @RequestMapping("/startTask")
    @ResponseBody
    public Object startTask(@RequestParam("device_int_id") Long device_int_id) {

        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();

        //查询设备信息
        param.put("int_id", device_int_id);
        DeviceDto device = deviceService.getInfoByMap(param);

        if (device == null) {
            //设备不存在
            return renderRequestError();
        }

        param.clear();
        param.put("device_int_id", device_int_id);

        List<IntervalQuartz> intervalQuartzList = iIntervalQuartzService.getIntervalQuartzListByMap(param);

        if (intervalQuartzList == null || intervalQuartzList.size() <= 0) {
            //如果没有记录,则添加记录后启动下任务
            IntervalQuartz intervalQuartz = new IntervalQuartz();

            intervalQuartz.setDevice_int_id(device_int_id);
            intervalQuartz.setTask_key("task_key#" + device_int_id);
            intervalQuartz.setStatus(1);
            intervalQuartz.setBean_class(DevicePeriodJob.class.getName());
            intervalQuartz.setJob_name("job_name#" + device_int_id);
            intervalQuartz.setJob_group_name("job_group_name#" + device_int_id);
            intervalQuartz.setTrigger_name("trigger_name#" + device_int_id);
            intervalQuartz.setTrigger_group_name("trigger_group_name#" + device_int_id);
            intervalQuartz.setTime_type(2);
            intervalQuartz.setValue(device.getPeriod());

            iIntervalQuartzService.insertAndStartIntervalQuartz(intervalQuartz);

        } else {
            //查询是否调度表中已有记录(默认只有正常的调度,告警的定时器不入库)
            //则查看状态
            IntervalQuartz intervalQuartz = intervalQuartzList.get(0);
            if (intervalQuartz.getStatus() == 0) {
                //如果未启动则启动下任务
                try {
                    intervalQuartz.setStatus(1);
                    intervalQuartz.setValue(device.getPeriod());//更新频率
                    intervalQuartz.setTime_type(2);

                    iIntervalQuartzService.updateAndStartIntervalQuartz(intervalQuartz);
                } catch (Exception e) {
                    e.printStackTrace();
                    return renderServerError();
                }
            }

        }

        return renderSuccess();
    }

    /**
     * 停止调度任务
     *
     * @return
     */
    @RequestLog(moduleName = "停止任务调度")
    @RequestMapping("/stopTask")
    @ResponseBody
    public Object stopTask(@RequestParam("device_int_id") Long device_int_id) {
        //查询是否调度表中已有记录
        //如果有任务,则停止任务
        if (device_int_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();

        //查询设备信息
        param.put("int_id", device_int_id);
        DeviceDto device = deviceService.getInfoByMap(param);

        if (device == null) {
            //设备不存在
            return renderRequestError();
        }

        param.clear();
        param.put("device_int_id", device_int_id);

        List<IntervalQuartz> intervalQuartzList = iIntervalQuartzService.getIntervalQuartzListByMap(param);

        if (intervalQuartzList == null || intervalQuartzList.size() <= 0) {
            //如果没有记录
            return renderRequestError();

        } else {

            //查询是否调度表中已有记录(默认只有正常的调度,告警的定时器不入库)
            //则查看状态
            IntervalQuartz intervalQuartz = intervalQuartzList.get(0);
            if (intervalQuartz.getStatus() == 1) {
                //如果启动则停止任务
                try {

                    intervalQuartz.setStatus(0);
                    iIntervalQuartzService.updateAndStopIntervalQuartz(intervalQuartz);

                } catch (Exception e) {
                    e.printStackTrace();
                    return renderServerError();
                }

            }

        }

        return renderSuccess();
    }

}

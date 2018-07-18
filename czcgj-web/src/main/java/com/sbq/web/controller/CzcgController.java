package com.sbq.web.controller;

import com.github.pagehelper.PageInfo;
import com.sbq.annotation.RequestLog;
import com.sbq.entity.dto.CzcgDeviceLastLogDto;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceLogDto;
import com.sbq.service.ICzcgService;
import com.sbq.service.IDeviceLogService;
import com.sbq.tools.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RequestMapping("czcg")
@Controller
public class CzcgController extends BaseController {

    @Autowired
    private IDeviceLogService deviceLogService;

    @Autowired
    private ICzcgService czcgService;

    @RequestMapping("")
    @RequestLog(moduleName = "城管门户页面")
    public String home(Model model) {

        Map map = new HashMap();

//        map.put("pageIndex", 1);
//
//        map.put("pageSize", 11);

        PageInfo<CzcgDeviceLastLogDto> dtoPageInfo = czcgService.getDeviceListAndLastLog(map);

        List list = new ArrayList();

        //常州市总数
        long all_num = dtoPageInfo.getTotal();
        //武进总数
        int wj_num = 0;
        //钟楼总数
        int zl_num = 0;
        //新北总数
        int xb_num = 0;
        //天宁总数
        int tn_num = 0;
        //金坛总数
        int jt_num = 0;
        //溧阳总数
        int ly_num = 0;

        //建议关注总数
        int jygz = 0;

        //建议检修总数
        int jyjx = 0;

        //建议整改
        int jyzg = 0;


        for (CzcgDeviceLastLogDto czcgDeviceLastLogDto : dtoPageInfo.getList()) {


            //对区域进行统计
            String area_name = czcgDeviceLastLogDto.getArea_name();

            if ("武进区".equals(area_name)) {
                wj_num++;
            } else if ("钟楼区".equals(area_name)) {
                zl_num++;
            } else if ("新北区".equals(area_name)) {
                xb_num++;
            } else if ("天宁区".equals(area_name)) {
                tn_num++;
            } else if ("金坛区".equals(area_name)) {
                jt_num++;
            } else if ("溧阳市".equals(area_name)) {
                ly_num++;
            }

            Double x = czcgDeviceLastLogDto.getX();
            Double y = czcgDeviceLastLogDto.getY();
            Double z = czcgDeviceLastLogDto.getZ();

            if (is_alarm(x, y, z)) {
                jyzg++;
                list.add(czcgDeviceLastLogDto);
            } else {
                jygz++;

            }
        }

//        if (dtoPageInfo.getTotal() > lenth) {
//            list = dtoPageInfo.getList().subList(0, lenth);
//        } else {
//            list = dtoPageInfo.getList();
//        }

        //首页右侧显示数量
        int lenth = 11;

        if (list.size() > lenth) {
            list = list.subList(0, lenth);
        }

        //这里存放有告警的记录
        model.addAttribute("dtoList", list);

        Map result = new HashMap();

        result.put("all_num", all_num);
        result.put("wj_num", wj_num);
        result.put("zl_num", zl_num);
        result.put("xb_num", xb_num);
        result.put("tn_num", tn_num);
        result.put("jt_num", jt_num);
        result.put("ly_num", ly_num);

        result.put("jygz", jygz);
        result.put("jyjx", jyjx);
        result.put("jyzg", jyzg);

        model.addAttribute("result", result);

        return "czcg/home";

    }

    private boolean is_alarm(Double x, Double y, Double z) {

        boolean flag = false;

        int value = 5;

        if (Math.abs(x) > value || Math.abs(y) > value || Math.abs(z) > value) {
            flag = true;
        }
        return flag;
    }

    @RequestMapping("tongji")
    public String tongji(Model model) {

        Map map = new HashMap();
        PageInfo<CzcgDeviceLastLogDto> dtoPageInfo = czcgService.getDeviceListAndLastLog(map);

        //建议关注
        List<Integer> jygz = new ArrayList<Integer>();

        //武进总数
        int wj_jygz_num = 0;
        //钟楼总数
        int zlj_jygz_num = 0;
        //新北总数
        int xbj_jygz_num = 0;
        //天宁总数
        int tnj_jygz_num = 0;
        //金坛总数
        int jtj_jygz_num = 0;
        //溧阳总数
        int lyj_jygz_num = 0;

        for (CzcgDeviceLastLogDto czcgDeviceLastLogDto : dtoPageInfo.getList()) {

            String area_name = czcgDeviceLastLogDto.getArea_name();

            Double x = czcgDeviceLastLogDto.getX();
            Double y = czcgDeviceLastLogDto.getY();
            Double z = czcgDeviceLastLogDto.getZ();

            if ("武进区".equals(area_name)) {
                if (!is_alarm(x, y, z)) {
                    wj_jygz_num++;
                }
            } else if ("钟楼区".equals(area_name)) {

                if (!is_alarm(x, y, z)) {
                    zlj_jygz_num++;
                }
            } else if ("新北区".equals(area_name)) {

                if (!is_alarm(x, y, z)) {
                    xbj_jygz_num++;
                }
            } else if ("天宁区".equals(area_name)) {

                if (!is_alarm(x, y, z)) {
                    tnj_jygz_num++;
                }
            } else if ("金坛区".equals(area_name)) {

                if (!is_alarm(x, y, z)) {
                    jtj_jygz_num++;
                }
            } else if ("溧阳市".equals(area_name)) {

                if (!is_alarm(x, y, z)) {
                    lyj_jygz_num++;
                }
            }
        }

        jygz.add(wj_jygz_num);
        jygz.add(zlj_jygz_num);
        jygz.add(xbj_jygz_num);
        jygz.add(tnj_jygz_num);
        jygz.add(jtj_jygz_num);
        jygz.add(lyj_jygz_num);


        //建议检修
        List<Integer> jyjx = new ArrayList<Integer>();

        //武进总数
        int wj_jyjx_num = 0;
        //钟楼总数
        int zlj_jyjx_num = 0;
        //新北总数
        int xbj_jyjx_num = 0;
        //天宁总数
        int tnj_jyjx_num = 0;
        //金坛总数
        int jtj_jyjx_num = 0;
        //溧阳总数
        int lyj_jyjx_num = 0;

        for (CzcgDeviceLastLogDto czcgDeviceLastLogDto : dtoPageInfo.getList()) {

            String area_name = czcgDeviceLastLogDto.getArea_name();

            Double x = czcgDeviceLastLogDto.getX();
            Double y = czcgDeviceLastLogDto.getY();
            Double z = czcgDeviceLastLogDto.getZ();

            if ("武进区".equals(area_name)) {
                if (is_alarm(x, y, z)) {
                    wj_jyjx_num++;
                }
            } else if ("钟楼区".equals(area_name)) {

                if (is_alarm(x, y, z)) {
                    zlj_jyjx_num++;
                }
            } else if ("新北区".equals(area_name)) {

                if (is_alarm(x, y, z)) {
                    xbj_jyjx_num++;
                }
            } else if ("天宁区".equals(area_name)) {

                if (is_alarm(x, y, z)) {
                    tnj_jyjx_num++;
                }
            } else if ("金坛区".equals(area_name)) {

                if (is_alarm(x, y, z)) {
                    jtj_jyjx_num++;
                }
            } else if ("溧阳市".equals(area_name)) {

                if (is_alarm(x, y, z)) {
                    lyj_jyjx_num++;
                }
            }
        }

        jyjx.add(wj_jyjx_num);
        jyjx.add(zlj_jyjx_num);
        jyjx.add(xbj_jyjx_num);
        jyjx.add(tnj_jyjx_num);
        jyjx.add(jtj_jyjx_num);
        jyjx.add(lyj_jyjx_num);

        Map result = new HashMap();

        result.put("jygz", JSONUtil.toJSON(jygz));
        result.put("jyjx", JSONUtil.toJSON(jyjx));

        model.addAttribute("result", result);

        return "czcg/tongji";

    }

    @RequestMapping("detail")
    public String detail() {


        return "czcg/detail";

    }

    @RequestMapping("list")
    public String list(@RequestParam(value = "area_name", required = false) String area_name, Model model) {

        Map map = new HashMap();

//        if(StringUtils.isNoneBlank(area_name)){
//            map.put("area_name",area_name);
//        }

//        PageInfo dtoPageInfo = czcgService.getDeviceListAndLastLog(map);
//
//        model.addAttribute("dtoList", dtoPageInfo.getList());

        return "czcg/list";

    }

    @RequestMapping("getDeviceLogListByDeviceId")
    @ResponseBody
    public Object getDeviceLogListByDeviceId(@RequestParam(value = "pageIndex", required = false) Integer pageIndex, @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                             HttpServletRequest request, @RequestParam("deviceId") String deviceId) {

        PageInfo<CzcgLogDto> czcgLogDtoList = getCzcgLogDtos(pageIndex, pageSize, deviceId);

        return renderSuccess(czcgLogDtoList);
    }

    @RequestMapping("getDeviceListByDeviceId")
    public String getDeviceListByDeviceId() {


//        PageInfo<CzcgLogDto> czcgLogDtoList = getCzcgLogDtos(pageIndex, pageSize, deviceId);

//        request.setAttribute("resultList", czcgLogDtoList);

        return "czcg/loglist";

    }

    /**
     * 获取时间段内x,y,z轴折线走势图
     *
     * @param start_time 开始时间  精确到分
     * @param end_time   结束时间 精确到分
     * @return 返回处理后的时间间隔数组，x轴数组,y轴数组,z轴数组
     * @apiNote 如果开始时间传了, 结束时间不传, 则开始时间到现在
     * 如果开始时间和结束时间都不传，则默认当前4小时到当前时间
     * 如果开始时间不传，结束时间传了,则结束时间前4小时到结束时间
     */
    @RequestMapping("getBrokenLineDeviceLogByTime")
    @ResponseBody
    public Object getBrokenLineDeviceLogByTime(@RequestParam(value = "start_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start_time,
                                               @RequestParam(value = "end_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end_time,
                                               @RequestParam("deviceId") String deviceId) {

        int hour_default = 6;//默认间隔4小时

        if (StringUtils.isBlank(deviceId)) {
            return renderRequestError();
        }


//        if (end_time == null) {
//            end_time = DateTime.now();
//        }
//
//        if (start_time == null) {
//            //开始时间不传
//            //结束时间前4小时到结束时间
//            start_time = DateUtil.offsetHour(end_time, -hour_default);
//        }
        //如果没有时间的限制，则显示最近5条数据

        Map param = new HashMap();
        param.put("log_start_time", start_time);
        param.put("log_end_time", end_time);
        param.put("device_id", deviceId);

        if (start_time == null && end_time == null) {
            param.put("limit", 5);
        }


        PageInfo<DeviceLogDto> deviceLogDtoPageInfo = deviceLogService.getDeviceLogListByPage(param);

        List<DeviceLogDto> deviceLogDtoList = deviceLogDtoPageInfo.getList();

        List<CzcgLogDto> czcgLogDtoList = czcgService.getCzcgLogDtos(deviceLogDtoList);

        return renderSuccess(czcgService.czcgLogDtoListToBrokenLine(czcgLogDtoList));

    }

    /**
     * 根据设备编号分页获取 CzcgLogDto
     *
     * @param pageIndex
     * @param pageSize
     * @param deviceId
     * @return
     */
    private PageInfo<CzcgLogDto> getCzcgLogDtos(Integer pageIndex, Integer pageSize, String deviceId) {

        Map param = new HashMap();

        if (pageIndex != null && pageIndex > 0) {
            param.put("pageIndex", pageIndex);
        }

        if (pageSize != null && pageSize > 0) {
            param.put("pageSize", pageSize);
        }

        param.put("device_id", deviceId);

        PageInfo<DeviceLogDto> deviceLogDtoPageInfo = deviceLogService.getDeviceLogListByPage(param);

        return czcgService.deviceLogDtoPageInfoToCzcgLogDtoPageInfo(deviceLogDtoPageInfo);
    }


    @RequestMapping("getDeviceListAndLastLog")
    @ResponseBody
    public Object getDeviceListAndLastLog(@RequestParam(value = "area_name", required = false) String area_name,
                                          @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Map map = new HashMap();

        if (StringUtils.isNoneBlank(area_name)) {
            try {
                map.put("area_name", java.net.URLDecoder.decode(area_name, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (pageIndex != null) {
            map.put("pageIndex", pageIndex);
        }

        if (pageSize != null) {
            map.put("pageSize", pageSize);
        }

        return renderSuccess(czcgService.getDeviceListAndLastLog(map));
    }

    @RequestLog(moduleName = "导出角度数据excel")
    @RequestMapping("/exportAngleExcel")
    @ResponseBody
    public Object exportAngleExcel(@RequestParam(value = "log_start_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_start_time,
                                   @RequestParam(value = "log_end_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date log_end_time,
                                   @RequestParam(value = "device_id", required = false) Long device_id,
                                   HttpServletResponse response) throws IOException {


        if (device_id == null) {
            return renderRequestError();
        }

        Map param = new HashMap();
        param.put("pageIndex", 1);
        param.put("pageSize", 9999999);

        param.put("log_start_time", log_start_time);
        param.put("log_end_time", log_end_time);

        param.put("device_id", device_id);


        PageInfo<DeviceLogDto> deviceLogDtoPageInfo = deviceLogService.getDeviceLogListByPage(param);

        PageInfo<CzcgLogDto> czcgLogDtoPageInfo = czcgService.deviceLogDtoPageInfoToCzcgLogDtoPageInfo(deviceLogDtoPageInfo);

        if (deviceLogDtoPageInfo.getTotal() == 0) {
            return renderRequestError("暂无数据");
        }

        List<CzcgLogDto> deviceLogDtoList = czcgLogDtoPageInfo.getList();

//        List<Map> list = czcgLogListToMapList(deviceLogDtoList);


//        OutputStream out = null;
//
//        out = response.getOutputStream();
////        List<TestBean> rows = CollUtil.newArrayList(bean1, bean2);
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("multipart/form-data");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + device_id + ".xls");
//
//        ExcelWriter writer = ExcelUtil.getWriter();
//        Map<String, String> alias = MapUtil.newHashMap();
//        alias.put("index", "序号");
//        alias.put("time", "日志时间");
//        alias.put("x", "X角度");
//        alias.put("y", "Y角度");
//        alias.put("z", "Z角度");
//        writer.setHeaderAlias(alias);
//        writer.merge(11, device_id + "的角度信息");
//
//        writer.write(deviceLogDtoList);
//        writer.flush(out);
//        writer.close();
////
//
//        out.close();

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("index","序号");
        fieldMap.put("x","X");
        fieldMap.put("y","Y");
        fieldMap.put("z","Z");
        fieldMap.put("time","日志时间");


        try {
            ExcelUtil.listToExcel(deviceLogDtoList, fieldMap, String.valueOf(device_id), response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private List<Map> czcgLogListToMapList(List<CzcgLogDto> czcgLogDtoList) {

        List<Map> list = new ArrayList();

        for (CzcgLogDto czcgLogDto : czcgLogDtoList) {

            Map map = new HashMap();

            map.put("index", czcgLogDto.getIndex());
            map.put("x", czcgLogDto.getX());
            map.put("y", czcgLogDto.getY());
            map.put("z", czcgLogDto.getZ());
            map.put("time", czcgLogDto.getTime());

            list.add(map);
        }

        return list;
    }


}

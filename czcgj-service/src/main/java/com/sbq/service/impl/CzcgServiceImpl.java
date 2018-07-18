package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.ICzcgDao;
import com.sbq.entity.Company;
import com.sbq.entity.dto.CzcgDeviceLastLogDto;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.entity.dto.DeviceLogDto;
import com.sbq.service.BaseService;
import com.sbq.service.ICzcgService;
import com.sbq.tools.CzcgUtil;
import com.sbq.tools.HttpUtil;
import com.sbq.tools.PropertiesUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CzcgServiceImpl extends BaseService implements ICzcgService {

    private static final String host = "http://58.216.50.107:8055";//58.216.50.77:8166

    @Autowired
    private ICzcgDao czcgDao;


    @Override
    public void pushWarn(String device_id, Integer warn_type, CzcgLogDto czcgLogDto, DeviceDto device) {

        InputStream inputStream = CzcgUtil.class.getResourceAsStream("/czcgj_other.properties");

        try {

            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

            String no_push_warn = propertiesUtil.getString("no_push_warn");

            String[] no_push_warn_array = no_push_warn.split(",");

            List<String> no_push_warn_list = Arrays.asList(no_push_warn_array);

            if (no_push_warn_list.contains(device_id)) {
                //存在，那需要过滤不处理推送给别人;
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map map = new HashMap();

        if (czcgLogDto != null) {

            DecimalFormat df = new java.text.DecimalFormat("#.##");

            map.put("X", df.format(czcgLogDto.getX()));
            map.put("Y", df.format(czcgLogDto.getY()));
            map.put("Z", df.format(czcgLogDto.getZ()));

        }

        if (device != null) {
            map.put("Address", device.getAddress());
        }


        String warn_msg = String.format("%s,联系单位:%s,联系人:%s,联系电话:%s,倾斜角:x轴倾斜:%s,y轴倾斜:%s,z轴倾斜:%s",
                map.getOrDefault("Address", ""),
                device.getUser_unit(),
                device.getUser_name(),
                device.getPhone(),
                map.getOrDefault("X", "0.0"),
                map.getOrDefault("Y", "0.0"),
                map.getOrDefault("Z", "0.0"));


        String url = String.format("%s/alarm/api/v1/alarm/msg?alarmTypeID=8&alarmX=0&alarmY=0&templateFlag=false&msgContent=%s&relateID=%s&alarmLevelID=%s&sysID=7",
                host, URLEncoder.encode(warn_msg), device_id, warn_type);

        logger.info(url);

        Map<String, String> headers = new HashMap<>();
        Map<String, Object> params = new HashMap<>();

        String result = "";

        try {

            result = HttpUtil.post(url, headers, params);


            JSONObject result_obj = new JSONObject(result);
            JSONObject resultInfo = result_obj.getJSONObject("resultInfo");
            boolean success = resultInfo.getBool("success");

            if (success) {
                logger.info("调用成功");
                Integer msgId = resultInfo.getJSONObject("data").getInt("magID", 0);

                logger.info("msgId=" + msgId);
            } else {
                logger.warn("调用失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfo getDeviceListAndLastLog(Map map) {

        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));

        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<CzcgDeviceLastLogDto> dtoList = czcgDao.getDeviceListAndLastLog(map);

        dtoList = getDataByCzcgDeviceLastLogDtoList(dtoList);

        return new PageInfo<>(dtoList);
    }


    private List<CzcgDeviceLastLogDto> getDataByCzcgDeviceLastLogDtoList(List<CzcgDeviceLastLogDto> dtoList) {

        for (CzcgDeviceLastLogDto dto : dtoList) {

            String data = dto.getData();

            JSONArray array = new JSONArray(data);

            CzcgLogDto czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array, dto.getDevice_id(),dto.getDevice_type());

            dto.setX(czcgLogDto.getX());
            dto.setY(czcgLogDto.getY());
            dto.setZ(czcgLogDto.getZ());

        }

        return dtoList;

    }

    /**
     * 对DeviceLogDto的list进行处理,返回处理后对应的CzcgLogDto的list
     *
     * @param deviceLogDtoList
     * @return
     */
    @Override
    public List<CzcgLogDto> getCzcgLogDtos(List<DeviceLogDto> deviceLogDtoList) {

        List<CzcgLogDto> czcgLogDtoList = new ArrayList<CzcgLogDto>();

        int sum = 0;

        for (DeviceLogDto deviceLogDto : deviceLogDtoList) {

            String data = deviceLogDto.getData();

            JSONArray array = new JSONArray(data);

            CzcgLogDto czcgLogDto = CzcgUtil.returnCzcgLogDtoByLogAndDeviceType(array, deviceLogDto.getDevice_id(),deviceLogDto.getDevice_type());

            sum++;
            czcgLogDto.setTime(DateUtil.format(deviceLogDto.getLog_time(), "yyyy-MM-dd HH:mm:ss"));
            czcgLogDto.setIndex(sum);
            czcgLogDtoList.add(czcgLogDto);

        }
        return czcgLogDtoList;
    }

    @Override
    public PageInfo<CzcgLogDto> deviceLogDtoPageInfoToCzcgLogDtoPageInfo(PageInfo<DeviceLogDto> deviceLogDtoPageInfo) {

        List<DeviceLogDto> deviceLogDtoList = deviceLogDtoPageInfo.getList();

        List<CzcgLogDto> czcgLogDtoList = getCzcgLogDtos(deviceLogDtoList);

        PageInfo<CzcgLogDto> czcgLogDtoPageInfo = new PageInfo<>(czcgLogDtoList);

        czcgLogDtoPageInfo.setEndRow(deviceLogDtoPageInfo.getEndRow());
        czcgLogDtoPageInfo.setNavigateFirstPage(deviceLogDtoPageInfo.getNavigateFirstPage());
        czcgLogDtoPageInfo.setNavigateLastPage(deviceLogDtoPageInfo.getNavigateLastPage());
        czcgLogDtoPageInfo.setNavigatepageNums(deviceLogDtoPageInfo.getNavigatepageNums());
        czcgLogDtoPageInfo.setNavigatePages(deviceLogDtoPageInfo.getNavigatePages());
        czcgLogDtoPageInfo.setNextPage(deviceLogDtoPageInfo.getNextPage());
        czcgLogDtoPageInfo.setPageNum(deviceLogDtoPageInfo.getPageNum());
        czcgLogDtoPageInfo.setPages(deviceLogDtoPageInfo.getPages());
        czcgLogDtoPageInfo.setPageSize(deviceLogDtoPageInfo.getPageSize());
        czcgLogDtoPageInfo.setPrePage(deviceLogDtoPageInfo.getPrePage());
        czcgLogDtoPageInfo.setPageSize(deviceLogDtoPageInfo.getPageSize());
        czcgLogDtoPageInfo.setStartRow(deviceLogDtoPageInfo.getStartRow());
        czcgLogDtoPageInfo.setTotal(deviceLogDtoPageInfo.getTotal());

        return czcgLogDtoPageInfo;
    }

    @Override
    public Map czcgLogDtoListToBrokenLine(List<CzcgLogDto> czcgLogDtoList) {

        Map result = new HashMap();

        List<String> xAxis_data = new ArrayList<String>();

        List<String> series_x = new ArrayList<String>();
        List<String> series_y = new ArrayList<String>();
        List<String> series_z = new ArrayList<String>();


        for (CzcgLogDto czcgLogDto : czcgLogDtoList) {

            String time = czcgLogDto.getTime();
            String x = String.format("%.2f", czcgLogDto.getX());
            String y = String.format("%.2f", czcgLogDto.getY());
            String z = String.format("%.2f", czcgLogDto.getZ());

            xAxis_data.add(time);
            series_x.add(x);
            series_y.add(y);
            series_z.add(z);

        }

        Collections.reverse(xAxis_data);

        Collections.reverse(series_x);
        Collections.reverse(series_y);
        Collections.reverse(series_z);

        result.put("xAxis_data", xAxis_data);
        result.put("series_x", series_x);
        result.put("series_y", series_y);
        result.put("series_z", series_z);

        return result;
    }

    public static void main(String[] args) {

        CzcgLogDto czcgLogDto = new CzcgLogDto();

        czcgLogDto.setX(13.84);
        czcgLogDto.setY(5.92);
        czcgLogDto.setZ(1.80);

        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setAddress("城管中心");

        //2 一般告警  ;  4- 严重告警
        new CzcgServiceImpl().pushWarn("10826047", 2, czcgLogDto, deviceDto);

    }
}

package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.dto.CzcgLogDto;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.entity.dto.DeviceLogDto;

import java.util.List;
import java.util.Map;

/**
 * 常州城管服务类
 */
public interface ICzcgService {

    /**
     * 调用告警接口
     *
     * @param device_id 关联的设备编号
     * @param warn_type 告警类型
     */
    public void pushWarn(String device_id, Integer warn_type, CzcgLogDto zcgLogDto, DeviceDto device);

    /**
     * 获取设备列表,包含设备最后一条数据
     *
     * @param map
     * @return
     */
    public PageInfo getDeviceListAndLastLog(Map map);

    public List<CzcgLogDto> getCzcgLogDtos(List<DeviceLogDto> deviceLogDtoList);

    public PageInfo<CzcgLogDto> deviceLogDtoPageInfoToCzcgLogDtoPageInfo(PageInfo<DeviceLogDto> deviceLogDtoPageInfo);

    /**
     * 将CzcgLogDto转换成需要的图表
     *
     * @param czcgLogDtoList
     * @return
     */
    public Map czcgLogDtoListToBrokenLine(List<CzcgLogDto> czcgLogDtoList);

}

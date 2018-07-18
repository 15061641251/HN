package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Device;
import com.sbq.entity.dto.DeviceDto;

import java.util.Map;

public interface IDeviceService {

    public DeviceDto getInfoByMap(Map map);

    public PageInfo<DeviceDto> getDeviceListByPage(Map<String, Object> map);

    public void updateDevice(Device device);

    public void delDeviceById(Long deviceId);

    public void addDevice(Device device);

    public void updateDeviceByDeviceId(Map map);
}

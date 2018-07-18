package com.sbq.dao;

import com.sbq.entity.Device;
import com.sbq.entity.dto.DeviceDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IDeviceDao extends Mapper<Device> {
    public DeviceDto getInfoByMap(Map map);

    public List<DeviceDto> getDeviceListByMap(Map map);

    public void updateDeviceInfoById(Map map);

    public void updateDeviceByDeviceId(Map map);
}

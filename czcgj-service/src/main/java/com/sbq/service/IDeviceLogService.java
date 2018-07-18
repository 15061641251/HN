package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.DeviceLog;
import com.sbq.entity.dto.DeviceLogDto;

import java.util.Map;

public interface IDeviceLogService {

    public void insetDeviceLog(DeviceLog deviceLog);

    public PageInfo<DeviceLogDto> getDeviceLogListByPage(Map<String, Object> map);

    public void importLog(Map map);

}

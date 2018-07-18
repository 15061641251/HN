package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.IDeviceDao;
import com.sbq.entity.Device;
import com.sbq.entity.dto.DeviceDto;
import com.sbq.service.BaseService;
import com.sbq.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl extends BaseService implements IDeviceService {

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public DeviceDto getInfoByMap(Map map) {
        return deviceDao.getInfoByMap(map);
    }

    @Override
    public PageInfo<DeviceDto> getDeviceListByPage(Map<String, Object> map) {
        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));


        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<DeviceDto> list = deviceDao.getDeviceListByMap(map);
        return new PageInfo<DeviceDto>(list);
    }

    @Override
    public void updateDevice(Device device) {
        deviceDao.updateByPrimaryKey(device);
    }

    @Override
    public void delDeviceById(Long deviceId) {
        deviceDao.deleteByPrimaryKey(deviceId);
    }

    @Override
    public void addDevice(Device device) {
        deviceDao.insert(device);
    }

    @Override
    public void updateDeviceByDeviceId(Map map) {
        deviceDao.updateDeviceByDeviceId(map);
    }
}

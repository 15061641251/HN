package com.sbq.service.impl;

import com.sbq.dao.IDeviceDao;
import com.sbq.dao.IDeviceTipDao;
import com.sbq.entity.DeviceTip;
import com.sbq.entity.DeviceWarnRule;
import com.sbq.service.BaseService;
import com.sbq.service.IDeviceTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceTipServiceImpl extends BaseService implements IDeviceTipService {

    @Autowired
    private IDeviceTipDao deviceTipDao;

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public List<DeviceTip> getDeviceTipListByMap(Map map) {
        return deviceTipDao.getDeviceTipListByMap(map);
    }

    @Override
    @Transactional
    public void clearDeviceTipByMap(Map map) {
        deviceTipDao.clearDeviceTipByMap(map);

        Map param = new HashMap();
        param.put("int_id", map.get("device_int_id"));
        param.put("no_tip_minute", map.get("no_tip_minute"));
        param.put("need_tip", 0);

        deviceDao.updateDeviceInfoById(param);
    }

    @Override
    @Transactional
    public void clearAndInsertDeviceTipByMap(Map map) {

        clearDeviceTipByMap(map);

        List<DeviceTip> deviceTipList = (List<DeviceTip>) map.get("deviceTipList");

        //TODO:设置设备告警提醒状态
        Map param = new HashMap();
        param.put("int_id", map.get("device_int_id"));
        param.put("no_tip_minute", map.get("no_tip_minute"));
        param.put("need_tip", 1);

        deviceDao.updateDeviceInfoById(param);

        deviceTipDao.insertDeviceTipByMap(deviceTipList);
    }
}

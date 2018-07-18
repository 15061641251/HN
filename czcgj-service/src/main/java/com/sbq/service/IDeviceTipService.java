package com.sbq.service;

import com.sbq.entity.DeviceTip;

import java.util.List;
import java.util.Map;

public interface IDeviceTipService {

    public List<DeviceTip> getDeviceTipListByMap(Map map);

    public void clearDeviceTipByMap(Map map);

    public void clearAndInsertDeviceTipByMap(Map map);

}

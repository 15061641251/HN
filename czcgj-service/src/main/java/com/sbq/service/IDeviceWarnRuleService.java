package com.sbq.service;

import com.sbq.entity.DeviceLog;
import com.sbq.entity.DeviceWarnRule;

import java.util.List;
import java.util.Map;

public interface IDeviceWarnRuleService {

    /**
     * 判断是否触发了警告
     *
     * @param deviceLog
     * @return
     */
    public boolean is_warn(DeviceLog deviceLog);

    public List<DeviceWarnRule> getDeviceWarnRuleListByMap(Map map);

    public void clearDeviceWarnRuleByMap(Map map);

    public void clearAndInsertDeviceWarnRuleByMap(Map map);
}

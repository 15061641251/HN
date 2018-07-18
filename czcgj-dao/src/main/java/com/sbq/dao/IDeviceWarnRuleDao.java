package com.sbq.dao;

import com.sbq.entity.DeviceWarnRule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IDeviceWarnRuleDao extends Mapper<DeviceWarnRule> {

    public List<DeviceWarnRule> getDeviceWarnRuleListByMap(Map map);

    public void clearDeviceWarnRuleByMap(Map map);

    public void insertDeviceWarnRuleList(@Param("deviceWarnRuleList") List<DeviceWarnRule> deviceWarnRuleList);

}

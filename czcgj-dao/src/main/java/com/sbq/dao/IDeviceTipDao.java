package com.sbq.dao;

import com.sbq.entity.DeviceTip;
import com.sbq.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IDeviceTipDao extends MyMapper<DeviceTip>{

    public List<DeviceTip> getDeviceTipListByMap(Map map);

    public void clearDeviceTipByMap(Map map);

    public void insertDeviceTipByMap(@Param("deviceTipList") List<DeviceTip> deviceTipList);

}

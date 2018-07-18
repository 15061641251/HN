package com.sbq.dao;

import com.sbq.entity.DeviceLog;
import com.sbq.entity.dto.DeviceLogDto;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface IDeviceLogDao extends Mapper<DeviceLog> {

    public List<DeviceLogDto> getDeviceLogListByMap(Map map);

    public void ifNoExistAndInsert(DeviceLog deviceLog);

}

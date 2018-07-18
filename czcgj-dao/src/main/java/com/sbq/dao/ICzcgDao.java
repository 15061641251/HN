package com.sbq.dao;

import com.sbq.entity.dto.CzcgDeviceLastLogDto;

import java.util.List;
import java.util.Map;

public interface ICzcgDao {

    public List<CzcgDeviceLastLogDto> getDeviceListAndLastLog(Map map);
}

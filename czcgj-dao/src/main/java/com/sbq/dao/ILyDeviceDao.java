package com.sbq.dao;

import com.sbq.entity.LyDevice;
import com.sbq.entity.dto.LyDeviceDto;
import com.sbq.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface ILyDeviceDao extends MyMapper<LyDevice> {

    public List<LyDeviceDto> getLyDeviceList(Map map);

}

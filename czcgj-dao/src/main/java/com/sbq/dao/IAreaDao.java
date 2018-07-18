package com.sbq.dao;

import com.sbq.entity.Area;
import com.sbq.entity.dto.AreaDto;
import com.sbq.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface IAreaDao extends MyMapper<Area>{

    public List<AreaDto> getAreaListByPage(Map map);

    public Area getInfoByMap(Map map);
}

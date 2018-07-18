package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Area;
import com.sbq.entity.dto.AreaDto;

import java.util.Map;

public interface IAreaService {

    public PageInfo<AreaDto> getAreaListByPage(Map map);

    public void addArea(Area area);

    public void updateArea(Area area);

    public void delAreaById(Long areaId);

    public Area getInfoByMap(Map map);

}

package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.IAreaDao;
import com.sbq.entity.Area;
import com.sbq.entity.dto.AreaDto;
import com.sbq.service.BaseService;
import com.sbq.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaServiceImpl extends BaseService implements IAreaService {

    @Autowired
    private IAreaDao areaDao;


    @Override
    public PageInfo<AreaDto> getAreaListByPage(Map map) {

        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));

        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<AreaDto> list = areaDao.getAreaListByPage(map);

        return new PageInfo<AreaDto>(list);
    }

    @Override
    public void addArea(Area area) {
        areaDao.insert(area);
    }

    @Override
    public void updateArea(Area area) {
        areaDao.updateByPrimaryKey(area);
    }

    @Override
    public void delAreaById(Long areaId) {
        areaDao.deleteByPrimaryKey(areaId);
    }

    @Override
    public Area getInfoByMap(Map map) {

        return areaDao.getInfoByMap(map);
    }
}

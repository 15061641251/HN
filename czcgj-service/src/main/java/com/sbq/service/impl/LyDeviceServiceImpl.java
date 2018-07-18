package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.ILyDeviceDao;
import com.sbq.entity.dto.LyDeviceDto;
import com.sbq.service.ILyDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LyDeviceServiceImpl implements ILyDeviceService {

    @Autowired
    private ILyDeviceDao lyDeviceDao;


    @Override
    public PageInfo<LyDeviceDto> getLyDeviceList(Map map) {

        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));

        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        return new PageInfo<LyDeviceDto>(lyDeviceDao.getLyDeviceList(map));

    }
}

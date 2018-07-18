package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.dto.LyDeviceDto;

import java.util.Map;

public interface ILyDeviceService {

    public PageInfo<LyDeviceDto> getLyDeviceList(Map map);
}

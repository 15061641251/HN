package com.sbq.dao;

import com.sbq.entity.Resources;
import com.sbq.util.MyMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IResourcesDao extends MyMapper<Resources> {

	public List<Resources> getResourcesDetailByMap(Map map);
	
}

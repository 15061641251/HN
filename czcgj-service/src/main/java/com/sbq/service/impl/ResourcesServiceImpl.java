package com.sbq.service.impl;

import com.sbq.dao.IResourcesDao;
import com.sbq.entity.Resources;
import com.sbq.service.BaseService;
import com.sbq.service.IResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourcesServiceImpl extends BaseService implements IResourcesService {

	@Autowired
	private IResourcesDao resourcesDao;

	@Override
	public List<Resources> getResourcesDetailByMap(Map<String, Object> map) {

		return resourcesDao.getResourcesDetailByMap(map);

	}

}

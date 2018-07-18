package com.sbq.service;


import com.sbq.entity.Resources;

import java.util.List;
import java.util.Map;

public interface IResourcesService {

	/**
	 * 根据权限主键获取权限资源详情
	 * 
	 * @param map
	 *            authId 可选
	 * @return
	 */
	public List<Resources> getResourcesDetailByMap(Map<String, Object> map);
}

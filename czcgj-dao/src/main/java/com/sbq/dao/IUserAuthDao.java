package com.sbq.dao;

import com.sbq.entity.UserAuth;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IUserAuthDao extends Mapper<UserAuth>
{
    /**
     * 获取角色信息
     * @param map
     * @return
     */
	public List<UserAuth> getUserAuthInfo(Map<String, Object> map);
}

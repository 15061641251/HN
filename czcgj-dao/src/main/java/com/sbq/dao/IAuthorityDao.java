package com.sbq.dao;


import com.sbq.entity.Authority;
import com.sbq.entity.MenusTreeObject;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
@Repository
public interface IAuthorityDao extends Mapper<Authority> {

	public List<Map<String, String>> getALLEnabledAuthorityList();

	public List<Map<String, String>> getALLEnabledAuthorityListByMap(Map<String, Object> map);

	public List<MenusTreeObject> getMenusByMap(Map<String, Object> map);

	@Delete("delete from t_authority where int_id = #{authId}")
	public void delAuthById(String authId);

	public int insertEntity(Authority authority);
	
	public void updateAuthInfo(Authority authority);

}

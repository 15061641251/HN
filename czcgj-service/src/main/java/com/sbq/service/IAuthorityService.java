package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Authority;
import com.sbq.entity.MenusTreeObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
public interface IAuthorityService {

	/**
	 * 根据map查询所有可用权限和其对应的路径信息,用于拦截器判断是否有权限访问,存缓存中
	 * 
	 * @param map
	 *            company_id 必传
	 * @return
	 */
	public List<Map<String, String>> getALLEnabledAuthorityListByMap(Map<String, Object> map);

	/**
	 * 根据map获取主菜单
	 * 
	 * @param map
	 *            authority_id 必传
	 * @return
	 */
	public List<MenusTreeObject> getMenusByMap(Map<String, Object> map);

	/**
	 * 根据map获取权限列表
	 * 
	 * @param map
	 *            公司主键必传
	 */
	public PageInfo<Authority> getAuthListByPage(Map<String, Object> map);

	/**
	 * 根据权限主键删除权限
	 * 
	 * @param authId
	 */
	@Transactional
	public void delAuthById(String authId);

	/**
	 * 插入并返回影响的行数
	 * 
	 * @param authority
	 * @return
	 */
	@Transactional
	public int insertEntity(Authority authority);
	
	/**
	 * 根据权限主键修改权限
	 */
	@Transactional
	public void updateAuthInfo(Authority authority);

	List<Authority> selectAll();


    /**
     * 添加
     *
     * @param authority
     */
    public void addAuth(Authority authority);

    /**
     * 更新
     *
     * @param authority
     */
    public void updateAuth(Authority authority);

}

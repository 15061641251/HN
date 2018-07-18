package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.IAuthResDao;
import com.sbq.dao.IAuthorityDao;
import com.sbq.entity.AuthResources;
import com.sbq.entity.Authority;
import com.sbq.entity.MenusTreeObject;
import com.sbq.service.BaseService;
import com.sbq.service.IAuthorityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityServiceImpl extends BaseService implements IAuthorityService {

    @Autowired
    private IAuthorityDao authorityDao;

    @Autowired
    private IAuthResDao authResDao;

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, String>> getALLEnabledAuthorityListByMap(Map<String, Object> map) {

        List<Map<String, String>> list = null;
        // list = (List<Map<String, String>>) EhcacheUtils
        // .get(Constant.KEY_CACHE_GETALLENABLEDAUTHORITYLIST +
        // map.get("dept_id"));
        //
        // if (list != null) {
        // return list;
        // }

        list = authorityDao.getALLEnabledAuthorityListByMap(map);

        // if (list != null) {
        // EhcacheUtils.put(Constant.KEY_CACHE_GETALLENABLEDAUTHORITYLIST +
        // map.get("dept_id"), list);
        // }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MenusTreeObject> getMenusByMap(Map<String, Object> map) {

        List<MenusTreeObject> list = null;
        // list = (List<MenusTreeObject>)
        // EhcacheUtils.get(Constant.GETMENUSBYMAP + map.get("authority_id"));
        //
        // if (list != null) {
        // return list;
        // }

        list = authorityDao.getMenusByMap(map);

        // if (list != null) {
        // EhcacheUtils.put(Constant.GETMENUSBYMAP + map.get("authority_id"),
        // list);
        // }
        return list;
    }

    @Override
    public PageInfo<Authority> getAuthListByPage(Map<String, Object> map) {
        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));
        String name = (String) map.get("name");
        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        Example example = new Example(Authority.class);
        Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(name)) {
            criteria.andCondition("name like '%" + name + "%'");
        }

        List<Authority> list = authorityDao.selectByExample(example);
        return new PageInfo<>(list);

    }

    @Override
    @Transactional
    public void delAuthById(String authId) {
        // 删除主键表中的记录
        authorityDao.delAuthById(authId);
        // 删除对应关系表中的记录
        Example example = new Example(AuthResources.class);
        example.createCriteria().andEqualTo("authority_id", authId);
        authResDao.deleteByExample(example);
    }

    @Override
    public int insertEntity(Authority authority) {
        return authorityDao.insert(authority);
    }

    @Override
    public void updateAuthInfo(Authority authority) {
        authorityDao.updateAuthInfo(authority);

    }

    @Override
    public List<Authority> selectAll() {
        return authorityDao.selectAll();
    }

    @Override
    @Transactional
    public void addAuth(Authority authority) {

        String[] ids = authority.getAuth_ids().split(",");

        // 先添加权限记录，拿到主键
        authority.setIs_enabled(true);
        authority.setCreate_time(new Date());
        authorityDao.insertSelective(authority);
        int authority_id = authority.getInt_id();

        // 别忘了，添加主菜单权限，1
        AuthResources authRes = new AuthResources();
        authRes.setAuthority_id(authority_id);
        authRes.setCreate_time(new Date());
        authRes.setResources_id(1);
        authResDao.insertSelective(authRes);

        // 再循环添加关联记录
        for (String id : ids) {
            authRes = new AuthResources();
            authRes.setAuthority_id(authority_id);
            authRes.setCreate_time(new Date());
            authRes.setResources_id(Integer.valueOf(id));
            authResDao.insertSelective(authRes);
        }

    }


    @Override
    @Transactional
    public void updateAuth(Authority authority) {

        authorityDao.updateAuthInfo(authority);

        int authority_id = authority.getInt_id();
        // 删除对应关系表中的记录
        Example example = new Example(AuthResources.class);
        example.createCriteria().andEqualTo("authority_id", authority_id);
        authResDao.deleteByExample(example);

        // 再循环添加关联记录
        // 别忘了，添加主菜单权限，1
        AuthResources authRes = new AuthResources();
        authRes.setAuthority_id(authority_id);
        authRes.setCreate_time(new Date());
        authRes.setResources_id(1);
        authResDao.insertSelective(authRes);

        String[] ids = authority.getAuth_ids().split(",");

        int i = 0;
        while (i < ids.length) {
            authRes = new AuthResources();
            authRes.setAuthority_id(authority_id);
            authRes.setCreate_time(new Date());
            authRes.setResources_id(Integer.valueOf(ids[i]));
            authResDao.insertSelective(authRes);
            i++;
        }

    }

}

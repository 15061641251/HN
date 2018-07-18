package com.sbq.service.impl;

import com.sbq.dao.IUserAuthDao;
import com.sbq.entity.UserAuth;
import com.sbq.service.BaseService;
import com.sbq.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author ZhuYanBing
 * @create 2017-03-22 13:24
 */
@Service
public class UserAuthServiceImpl extends BaseService implements IUserAuthService {

    @Autowired
    private IUserAuthDao userAuthDao;

    @Override
    public List<UserAuth> getUserAuthInfo(Map<String, Object> param) {
        return userAuthDao.getUserAuthInfo(param);
    }

    @Override
    public void deleteByExample(Example example) {
        userAuthDao.deleteByExample(example);
    }

    @Override
    public void insertSelective(UserAuth ua) {
        userAuthDao.insertSelective(ua);
    }

    @Override
    public void insertSelectiveByAuthsAndUser_id(String[] auths, Long userId) {
        for (String auth : auths) {
            UserAuth ua = new UserAuth();
            ua.setCreate_time(new Date());
            ua.setAuth_id(Integer.parseInt(auth));
            ua.setUser_id(userId);

            userAuthDao.insertSelective(ua);
        }
    }
}

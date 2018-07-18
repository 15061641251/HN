package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.IUserDao;
import com.sbq.entity.User;
import com.sbq.entity.dto.UserDto;
import com.sbq.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private IUserDao userDao = null;

    @Override
    public UserDto getInfoByMap(Map map) {
        // TODO Auto-generated method stub
        return userDao.getUserInfoByMap(map);
    }

    @Override
    public int insertSelective(User user) {
        // TODO Auto-generated method stub
        return userDao.insertSelective(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        if (StringUtils.isNoneBlank(user.getDevice_token())) {
            //不为空,替换之前相同的值为空
            Map map = new HashMap();
            map.put("new_token", "");
            map.put("old_token", user.getDevice_token());

            userDao.updateToken(map);
        }


        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageInfo<UserDto> selectUserDtoByMap(Map map) {
        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));
        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        return new PageInfo<>(userDao.getUserListByMap(map));
    }

    @Override
    public User selectByPrimaryKey(long l) {
        return userDao.selectByPrimaryKey(l);
    }

    @Override
    public void deleteByPrimaryKey(long l) {
        userDao.deleteByPrimaryKey(l);
    }

    @Override
    public List<UserDto> getUserInfoByIds(String userIds) {

        return userDao.getUserInfoByIds(userIds);

    }
}

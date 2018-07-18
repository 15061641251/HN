package com.sbq.service;


import com.github.pagehelper.PageInfo;
import com.sbq.entity.User;
import com.sbq.entity.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IUserService {

    //获取用户信息
    UserDto getInfoByMap(Map map);

    PageInfo<UserDto> selectUserDtoByMap(Map map);

    User selectByPrimaryKey(long l);

    @Transactional
    int insertSelective(User user);

    /**
     * 根据id修改表中的数据
     *
     * @param user
     */
    @Transactional
    void updateUser(User user);

    @Transactional
    void deleteByPrimaryKey(long l);

    /**
     * 根据用户拼接的id 按顺序获取用户列表
     * @param userIds
     * @return
     */
    List<UserDto> getUserInfoByIds(String userIds);
}

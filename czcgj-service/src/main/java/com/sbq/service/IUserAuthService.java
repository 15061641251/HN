package com.sbq.service;

import com.sbq.entity.UserAuth;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author ZhuYanBing
 * @create 2017-03-22 13:23
 */

public interface IUserAuthService {

    public List<UserAuth> getUserAuthInfo(Map<String, Object> param);

    @Transactional
    void deleteByExample(Example example);

    @Transactional
    void insertSelective(UserAuth ua);

    @Transactional
    void insertSelectiveByAuthsAndUser_id(String[] auths, Long userId);
}

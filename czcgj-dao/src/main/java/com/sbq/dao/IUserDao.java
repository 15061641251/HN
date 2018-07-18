package com.sbq.dao;

import com.sbq.entity.User;
import com.sbq.entity.dto.DeptMemberDto;
import com.sbq.entity.dto.DeptOrUserDto;
import com.sbq.entity.dto.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface IUserDao extends Mapper<User> {

    UserDto getUserInfoByMap(Map map);

    List<UserDto> getUserListByMap(Map map);

    List<UserDto> selectAllMembers();


    /**
     * 客户端调用
     *
     * @return
     */
    List<DeptOrUserDto> getAllUserList();

    /**
     * 客户端调用
     *
     * @return
     */
    List<DeptOrUserDto> getLastUserList(Map paramMap);

    List<UserDto> getUserInfoByIds(@Param("userids") String userids);

    List<DeptMemberDto> getAllUserListDeptMemberDto();

    void updateToken(Map map);
}

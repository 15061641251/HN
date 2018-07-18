package com.sbq.dao;

import com.sbq.entity.Dept;
import com.sbq.entity.dto.DeptMemberDto;
import com.sbq.entity.dto.DeptOrUserDto;
import com.sbq.util.MyMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface IDeptDao extends MyMapper<Dept> {


    List<Dept> getDeptListByParentId(Map map);

    /**
     * 获取所有部门信息
     *
     * @return
     */
    List<Dept> selectAllDepts();


    List<Dept> getChildDeptsByParentId(Map map);

    List<Dept> getDeptInfo(Map map);

    /**
     * 获取最近更新部门列表
     *
     * @param map
     * @return
     */
    List<DeptOrUserDto> getLastDeptList(Map map);

    List<DeptOrUserDto> getAllDeptList();

    List<DeptMemberDto> getAllDeptListDeptMemberDto();

}

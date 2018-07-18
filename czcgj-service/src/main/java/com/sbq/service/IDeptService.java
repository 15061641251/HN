package com.sbq.service;


import com.sbq.entity.Dept;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDeptService {

    Dept selectByPrimaryKey(long l);

    @Transactional
    void updateByPrimaryKeySelective(Dept dept);

    @Transactional
    int insertSelective(Dept dept);

    List<Dept> getDeptListByParentId(Long parent_id);

    List<Dept> selectAllDepts();
}

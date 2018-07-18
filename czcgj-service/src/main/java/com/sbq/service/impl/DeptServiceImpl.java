package com.sbq.service.impl;

import com.sbq.dao.IDeptDao;
import com.sbq.entity.Dept;
import com.sbq.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private IDeptDao deptDao;

    @Override
    public Dept selectByPrimaryKey(long l) {
        return deptDao.selectByPrimaryKey(l);
    }

    @Override
    public void updateByPrimaryKeySelective(Dept dept) {
        deptDao.updateByPrimaryKeySelective(dept);
    }

    @Override
    public int insertSelective(Dept dept) {
        return deptDao.insertSelective(dept);
    }

    @Override
    public List<Dept> getDeptListByParentId(Long parent_id) {
        Map map=new HashMap();
        map.put("parent_id",parent_id);
        return deptDao.getChildDeptsByParentId(map);
    }

    @Override
    public List<Dept> selectAllDepts() {
        return deptDao.selectAllDepts();
    }

}



package com.sbq.dao;

import com.sbq.entity.Company;
import com.sbq.util.MyMapper;

import java.util.Map;

public interface ICompanyDao extends MyMapper<Company>{

    public Company getInfoByMap(Map map);
}

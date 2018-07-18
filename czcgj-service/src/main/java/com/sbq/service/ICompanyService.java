package com.sbq.service;

import com.github.pagehelper.PageInfo;
import com.sbq.entity.Company;
import com.sbq.entity.dto.RegisterDto;

import java.util.Map;

public interface ICompanyService {

    public PageInfo<Company> getCompanyListByPage(Map<String, Object> map);

    public void delCompanyById(Long companyId);

    public void addCompany(Company company);

    public Company getInfoByMap(Map map);

    public void updateCompany(Company company);

    public void registerCompany(RegisterDto registerDto);
}

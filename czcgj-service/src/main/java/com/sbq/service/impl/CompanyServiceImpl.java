package com.sbq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbq.dao.ICompanyDao;
import com.sbq.dao.IUserAuthDao;
import com.sbq.dao.IUserDao;
import com.sbq.entity.Company;
import com.sbq.entity.User;
import com.sbq.entity.UserAuth;
import com.sbq.entity.dto.RegisterDto;
import com.sbq.service.BaseService;
import com.sbq.service.ICompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl extends BaseService implements ICompanyService {

    @Autowired
    private ICompanyDao companyDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserAuthDao userAuthDao;

    @Override
    public PageInfo<Company> getCompanyListByPage(Map<String, Object> map) {
        int pageNum = (map.get("pageIndex") == null ? 0 : (Integer) map.get("pageIndex"));
        int pageSize = (map.get("pageSize") == null ? 0 : (Integer) map.get("pageSize"));

        if (pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        Example example = new Example(Company.class);
        Example.Criteria criteria = example.createCriteria();

        String name = (String) map.get("name");
        if (StringUtils.isNotEmpty(name)) {
            criteria.andCondition("company_name like '%" + name + "%'");
        }

        Long company_id = (Long) map.get("company_id");
        if (company_id != null) {
            criteria.andEqualTo("int_id", company_id);
        }

        List<Company> list = companyDao.selectByExample(example);
        return new PageInfo<Company>(list);
    }

    @Override
    @Transactional
    public void delCompanyById(Long companyId) {
        // 删除对应关系表中的记录
        companyDao.deleteByPrimaryKey(companyId);
    }

    @Override
    public void addCompany(Company company) {
        companyDao.insert(company);
    }

    @Override
    public Company getInfoByMap(Map map) {

        return companyDao.getInfoByMap(map);
    }

    @Override
    public void updateCompany(Company company) {
        companyDao.updateByPrimaryKey(company);
    }

    @Override
    @Transactional
    public void registerCompany(RegisterDto registerDto) {
        //先添加注册机构
        Company company = new Company();
        company.setCreate_time(new Date());
        company.setTime_stamp(new Date());
        company.setStateflag(0);

        BeanUtils.copyProperties(registerDto, company);

        companyDao.insert(company);

        User user = new User();
        user.setStateflag(0);
        user.setCreate_time(new Date());
        user.setTime_stamp(new Date());
        user.setCompany_id(company.getInt_id());
        user.setAccount(registerDto.getAccount());
        user.setPassword(registerDto.getPwd());
        user.setIs_enabled(1);
        user.setUsername(registerDto.getPerson_name());
        user.setSex("男");
        user.setIs_admin(1);

        //再添加账号
        userDao.insertSelective(user);

        UserAuth userAuth = new UserAuth();

        userAuth.setCreate_time(new Date());

        userAuth.setAuth_id(15);
        userAuth.setUser_id((long) user.getInt_id());

        userAuthDao.insert(userAuth);
    }
}

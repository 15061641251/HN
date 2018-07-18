package com.sbq.dao;

import com.sbq.entity.AuthResources;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface IAuthResDao extends Mapper<AuthResources> {

}

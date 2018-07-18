package com.sbq.dao;

import com.sbq.entity.App;
import com.sbq.util.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangyuan on 2017/2/13.
 */
@Repository
public interface IAppDao extends MyMapper<App> {
}

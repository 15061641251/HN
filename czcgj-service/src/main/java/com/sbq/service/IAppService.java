package com.sbq.service;

import com.sbq.entity.App;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/2/13.
 */
@Service
public interface IAppService {

    List<App> selectAppByMap(Map map);

    @Transactional
    void insertApp();



    App getAppLatestVersion(int appid);
}

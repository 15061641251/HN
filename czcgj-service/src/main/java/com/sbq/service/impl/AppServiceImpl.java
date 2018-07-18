package com.sbq.service.impl;

import com.sbq.dao.IAppDao;
import com.sbq.entity.App;
import com.sbq.service.IAppService;
import com.sbq.tools.NullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuan on 2017/2/13.
 */
@Service
public class AppServiceImpl implements IAppService {

    @Autowired
    private IAppDao appDao;

    @Override
    public List<App> selectAppByMap(Map map) {
        return appDao.selectAll();
    }

    @Override
    public void insertApp() {

        App app = new App();
        app.setApp_version("1.0");
        app.setDescription("描述");
        app.setCreate_time(new Date());
        app.setApp_id(1);
        app.setTime_stamp(new Date());

        appDao.insert(app);


//        int[] a = new int[]{1,2,3};
//
//        int b = a[10];
//
//        app = new App();
//        app.setApp_version("1.0");
//        app.setDescription("描述");
//        app.setCreate_time(new Date());
//        app.setApp_id(1);
//        app.setTime_stamp(new Date());
//
//        appDao.insert(app);
    }


    @Override
    public App getAppLatestVersion(int appid) {

        Example example = new Example(App.class);

        example.orderBy("time_stamp").desc();
        example.createCriteria().andEqualTo("app_id", appid);

        List<App> appList = appDao.selectByExample(example);

        if (NullUtil.isNull(appList)) {
            return null;
        } else {
            return appList.get(0);
        }

    }
}

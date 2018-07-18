package com.sbq.service.impl;

import com.sbq.dao.IDeviceWarnRuleDao;
import com.sbq.entity.DeviceLog;
import com.sbq.entity.DeviceWarnRule;
import com.sbq.service.BaseService;
import com.sbq.service.IDeviceWarnRuleService;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceWarnRuleServiceImpl extends BaseService implements IDeviceWarnRuleService {

    @Autowired
    private IDeviceWarnRuleDao deviceWarnRuleDao;


    @Override
    public boolean is_warn(DeviceLog deviceLog) {

        boolean is_warn = false;

        Long device_int_id = deviceLog.getDevice_int_id();
        if (device_int_id == null) {
            return is_warn;
        }

        Map param = new HashMap();
        param.put("device_int_id", device_int_id);

        List<DeviceWarnRule> deviceWarnRuleList = deviceWarnRuleDao.getDeviceWarnRuleListByMap(param);
        if (deviceWarnRuleList != null && deviceWarnRuleList.size() > 0) {
            //循环,只要满足一个就说明需要告警
            for (DeviceWarnRule deviceWarnRule : deviceWarnRuleList) {

                Integer condition_type = deviceWarnRule.getCondition_type();
                String data_key = deviceWarnRule.getData_key();
                String seted_value = deviceWarnRule.getData_value();

                String data = deviceLog.getData();

                JSONArray jsonArray = new JSONArray(data);

                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String key = jsonObject.getStr("id", "");
                    String value = jsonObject.getStr("value", "");

                    if (!key.equals(data_key)) {
                        continue;
                    }

                    if (StringUtils.isNoneBlank(value, seted_value)) {
                        if (compare(condition_type, value, seted_value)) {
                            //满足了告警条件
                            is_warn = true;
                            break;
                        }
                    }
                }
            }
        }

        return is_warn;
    }

    @Override
    public List<DeviceWarnRule> getDeviceWarnRuleListByMap(Map map) {
        return deviceWarnRuleDao.getDeviceWarnRuleListByMap(map);
    }

    @Override
    public void clearDeviceWarnRuleByMap(Map map) {
        deviceWarnRuleDao.clearDeviceWarnRuleByMap(map);
    }

    @Override
    @Transactional
    public void clearAndInsertDeviceWarnRuleByMap(Map map) {

        deviceWarnRuleDao.clearDeviceWarnRuleByMap(map);

        List<DeviceWarnRule> deviceWarnRuleList = (List<DeviceWarnRule>) map.get("deviceWarnRuleList");

        deviceWarnRuleDao.insertDeviceWarnRuleList(deviceWarnRuleList);
    }

    /**
     * String 两个值 进行判断
     *
     * @param condition_type
     * @param value
     * @param seted_value
     * @return
     */
    private boolean compare(Integer condition_type, String value, String seted_value) {

        boolean is_warn = false;

        int result = value.compareTo(seted_value);

        //进行data_value 和 value 对比
        switch (condition_type) {
            case 1:
                //小于
                if (result < 0) {
                    is_warn = true;
                }
                break;
            case 2:
                //小于等于
                if (result <= 0) {
                    is_warn = true;
                }
                break;
            case 3:
                //等于
                if (result == 0) {
                    is_warn = true;
                }
                break;
            case 4:
                //大于等于
                if (result >= 0) {
                    is_warn = true;
                }
                break;
            case 5:
                //大于
                if (result > 0) {
                    is_warn = true;
                }
                break;
            default:
                break;
        }

        return is_warn;
    }

}

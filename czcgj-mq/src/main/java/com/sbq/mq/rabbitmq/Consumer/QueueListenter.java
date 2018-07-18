package com.sbq.mq.rabbitmq.Consumer;

import com.sbq.common.Constants;
import com.sbq.common.MQEntity;
import com.sbq.dao.IUserDao;
import com.sbq.entity.User;
import com.sbq.push.hwpush.HWPushUtil;
import com.sbq.push.xinge.XingeUtil;
import com.sbq.tools.DateUtil;
import com.sbq.tools.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class QueueListenter implements MessageListener {

    @Autowired
    private IUserDao userDao;

    @Override
    public void onMessage(Message msg) {
        try {

            String json = new String(msg.getBody());
            System.out.println(json);

            MQEntity mqEntity = (MQEntity) JsonUtil.JSONToObj(json, MQEntity.class);

            if (mqEntity == null) {
                //类型不匹配,跳过
                return;
            }
            int type = mqEntity.getType();
            Map<String, Object> ext = mqEntity.getExt();

            long user_int_id = Long.valueOf((String) ext.get("user_int_id"));

            User user = userDao.selectByPrimaryKey(user_int_id);

            if (user == null) {

                System.out.println("未找到相应人员信息 user_int_id:s" + user_int_id);
                return;
            }

            if (type == Constants.TIP_SYSTEM) {

                final String title = (String) ext.get("title");
                final int formType = (int) ext.get("formType");
                final int foreign_int_id = (int) ext.get("foreign_int_id");
                final String temp_content = (String) ext.get("content");

                final int phone_type = user.getType();//设备类型 1--ios；2--android
                final int phone_small_type = user.getPhone_type();//0:其他；1-华为;

                final String token = user.getDevice_token();

                if (phone_type == 1) {
                    //TODO:苹果推送
                } else if (phone_type == 2) {
                    if (phone_small_type == 1) {
                        //华为推送
                        if (StringUtils.isNoneBlank(token)) {

                            String content = HWPushUtil.msg_str(DateUtil.getCurrentDateString("yyyyMMddHHmmss"), String.valueOf(foreign_int_id), String.valueOf(formType), title, temp_content, "", "", DateUtil.getCurrentDateString("yyyy-MM-dd"));
                            HWPushUtil.pushSingleDeviceMessage(title, token, content);

                        }

                    } else {
                        //信鸽推送
                        List<String> tokenList = new ArrayList<String>();
                        tokenList.add(token);
                        String content = XingeUtil.msg_str(DateUtil.getCurrentDateString("yyyyMMddHHmmss"), String.valueOf(foreign_int_id), String.valueOf(formType), title, temp_content, "", "", DateUtil.getCurrentDateString("yyyy-MM-dd"));
                        XingeUtil.pushDeviceListAndroid(title, content, tokenList);
                    }
                }

            } else if (type == Constants.TIP_SMS) {

            } else if (type == Constants.TIP_EMAIL) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
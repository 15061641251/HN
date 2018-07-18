package com.sbq.push.xinge;

import com.sbq.common.Constants;
import com.sbq.push.hwpush.HWPushUtil;
import com.sbq.tools.PropertiesUtil;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 信鸽推送工具类
 * Created by zhangyuan on 2017/2/22.
 */
public class XingeUtil {


    private static XingeApp xinge = null;

    private static boolean is_pro;


    private static String push_msg;

    static {

        InputStream inputStream = XingeUtil.class.getResourceAsStream("/push.properties");

        try {

            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

            long access_id = propertiesUtil.getLong("xinge.accessId", 0);
            String secret_key = propertiesUtil.getString("xinge.secretKey");

            xinge = new XingeApp(access_id, secret_key);

            push_msg = propertiesUtil.getString("push.msg");

            is_pro = propertiesUtil.getBoolean("xinge.is_pro", false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造所需的透传消息字符串
     *
     * @param timeid
     * @param msgid
     * @param type
     * @param title
     * @param content
     * @param fromUserId
     * @param toUserId
     * @param time
     * @return
     */
    public static String msg_str(String timeid, String msgid, String type, String title, String content, String fromUserId, String toUserId, String time) {
        return String.format(push_msg, timeid, msgid, type, title, content, fromUserId, toUserId, time);
    }

    //////////////////// andorid 推送相关接口 //////////////////////

    /**
     * 单个设备下发透传消息
     *
     * @param title
     * @param token
     * @param msg
     * @return
     */
    public static JSONObject pushSingleDeviceMessage(String title, String token, String msg) {

        Message message = new Message();
        message.setTitle(title);
        message.setContent(msg);
        message.setType(Message.TYPE_MESSAGE);
        message.setExpireTime(86400);
        JSONObject ret = xinge.pushSingleDevice(token, message);

        return ret;
    }


    /**
     * 多个Android设备下发通知
     *
     * @param title
     * @param content
     * @param tokenList
     * @return
     */
    public static JSONObject pushDeviceListAndroid(String title, String content, List<String> tokenList) {

        Message message = new Message();

        TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        message.addAcceptTime(acceptTime1);

        message.setType(Message.TYPE_MESSAGE);
        message.setExpireTime(86400);
        message.setTitle(title);
        message.setContent(content);
//        messageIOS.setBadge(1);
//        messageIOS.setCategory("INVITE_CATEGORY");

        JSONObject ret = xinge.createMultipush(message);

        if (ret.getInt("ret_code") != 0)
            return ret;
        else {
            JSONObject result = new JSONObject();

            result.append("all", xinge.pushDeviceListMultiple(ret.getJSONObject("result").getLong("push_id"), tokenList));


            System.out.println("pushDeviceListAndroid:" + result);

            return result;
        }


    }

    //ios 推送相关接口

    /**
     * 单个设备下发通知消息iOS
     *
     * @param title
     * @param token
     * @param param
     * @return
     */
    public static JSONObject pushSingleDeviceNotificationIOS(String title, String token, Map<String, Object> param) {

        MessageIOS messageIOS = new MessageIOS();

        TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        messageIOS.addAcceptTime(acceptTime1);

        messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
        messageIOS.setExpireTime(86400);
        messageIOS.setAlert(title);
//        messageIOS.setBadge(1);
//        messageIOS.setCategory("INVITE_CATEGORY");
        messageIOS.setSound("beep.wav");
        messageIOS.setCustom(param);

        JSONObject ret = xinge.pushSingleDevice(token, messageIOS, is_pro ? XingeApp.IOSENV_PROD : XingeApp.IOSENV_DEV);
        return ret;
    }

    /**
     * 多个IOS设备下发通知
     *
     * @param title
     * @param deviceList
     * @param param
     * @return
     */
    public static JSONObject pushDeviceListIOS(String title, List<String> tokenList, Map<String, Object> param) {

        MessageIOS messageIOS = new MessageIOS();

        TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
        messageIOS.addAcceptTime(acceptTime1);

        messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
        messageIOS.setExpireTime(86400);
        messageIOS.setAlert(title);
//        messageIOS.setBadge(1);
//        messageIOS.setCategory("INVITE_CATEGORY");
        messageIOS.setSound("beep.wav");
        messageIOS.setCustom(param);

        JSONObject ret = xinge.createMultipush(messageIOS, is_pro ? XingeApp.IOSENV_PROD : XingeApp.IOSENV_DEV);

        if (ret.getInt("ret_code") != 0)
            return ret;
        else {
            JSONObject result = new JSONObject();

            result.append("all", xinge.pushDeviceListMultiple(ret.getJSONObject("result").getLong("push_id"), tokenList));

            return result;
        }

    }

    public static void main(String[] args) {

        String token = "9d0534dc94f00bf14bb29d2700765bc8954156ce";
        String msg = XingeUtil.msg_str("1", "35", String.valueOf(Constants.CODE_MATERIAL), "测试标题", "测试内容", "1", "", "1213123");
        XingeUtil.pushSingleDeviceMessage("", token, msg);

    }

}

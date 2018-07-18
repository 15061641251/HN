package com.sbq.push.hwpush;

import com.sbq.common.Constants;
import com.sbq.push.xinge.XingeUtil;
import com.sbq.tools.PropertiesUtil;
import nsp.NSPClient;
import nsp.OAuth2Client;
import nsp.support.common.AccessToken;
import nsp.support.common.NSPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 华为推送工具类
 * Created by zhangyuan on 2017/4/11.
 */
public class HWPushUtil {

    protected static Logger logger = LoggerFactory.getLogger(HWPushUtil.class);

    private static String appId;

    private static String appKey;

    private static String push_msg;

    private static URL jks_url;

    public static final String TIMESTAMP_NORMAL = "yyyy-MM-dd HH:mm:ss";

    static {

        InputStream inputStream = XingeUtil.class.getResourceAsStream("/push.properties");

        try {

            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

            appId = propertiesUtil.getString("hw.appId");
            appKey = propertiesUtil.getString("hw.appSecret");
            push_msg = propertiesUtil.getString("push.msg");

            jks_url = HWPushUtil.class.getResource("/mykeystorebj.jks");

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

    /**
     * 单个设备下发透传消息
     *
     * @param title
     * @param token
     * @param msg
     * @return
     */
    public static boolean pushSingleDeviceMessage(String title, String token, String msg) {

        boolean flag = false;

        OAuth2Client oauth2Client = new OAuth2Client();

        try {

            oauth2Client.initKeyStoreStream(jks_url.openStream(), "123456");
            AccessToken access_token = oauth2Client.getAccessToken("client_credentials", appId, appKey);
            NSPClient client = new NSPClient(access_token.getAccess_token());
            client.initHttpConnections(30, 50);//设置每个路由的连接数和最大连接数
            client.initKeyStoreStream(jks_url.openStream(), "123456");

            long currentTime = System.currentTimeMillis();
            SimpleDateFormat dataFormat = new SimpleDateFormat(TIMESTAMP_NORMAL);

            //必选
            //0：高优先级
            //1：普通优先级
            //缺省值为1
            int priority = 1;

            //消息是否需要缓存，必选
            //0：不缓存
            //1：缓存
            //  缺省值为0
            int cacheMode = 1;

            //标识消息类型（缓存机制），必选
            //由调用端赋值，取值范围（1~100）。当TMID+msgType的值一样时，仅缓存最新的一条消息
            int msgType = 1;

            //可选
            //如果请求消息中，没有带，则MC根据ProviderID+timestamp生成，各个字段之间用下划线连接
            //String requestID = "1_1362472787848";

            //unix时间戳，可选
            //格式：2013-08-29 19:55
            // 消息过期删除时间
            //如果不填写，默认超时时间为当前时间后48小时
            //String expire_time = dataFormat.format(currentTime + 3 * 60 * 60 * 1000);

            //构造请求
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("deviceToken", token);
            hashMap.put("message", msg);
            hashMap.put("priority", priority);
            hashMap.put("cacheMode", cacheMode);
            hashMap.put("msgType", msgType);
            //hashMap.put("requestID", requestID);
            //hashMap.put("expire_time", expire_time);

            //设置http超时时间
            client.setTimeout(10000, 15000);
            //接口调用
            PushRet resp = client.call("openpush.message.single_send", hashMap, PushRet.class);

            logger.info("HWPushUtil-单发接口消息响应:" + resp.getResultcode() + ",message:" + resp.getMessage());

            if (resp.getResultcode() == 0) {
                flag = true;
            }

        } catch (NSPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("推送pushSingleDeviceMessage调用结果:" + flag);

        return flag;
    }

    /**
     * 多个Android设备下发通知
     *
     * @param title
     * @param content
     * @param tokenList
     * @return
     */
    public static boolean pushDeviceListAndroid(String title, String content, List<String> tokenList) {

        boolean flag = false;

        OAuth2Client oauth2Client = new OAuth2Client();

        try {

            oauth2Client.initKeyStoreStream(jks_url.openStream(), "123456");
            AccessToken access_token = oauth2Client.getAccessToken("client_credentials", appId, appKey);
            NSPClient client = new NSPClient(access_token.getAccess_token());
            client.initHttpConnections(30, 50);//设置每个路由的连接数和最大连接数
            client.initKeyStoreStream(jks_url.openStream(), "123456");

            long currentTime = System.currentTimeMillis();
            SimpleDateFormat dataFormat = new SimpleDateFormat(TIMESTAMP_NORMAL);

            //消息是否需要缓存，必选
            //0：不缓存
            //1：缓存
            //  缺省值为0
            int cacheMode = 1;

            //标识消息类型（缓存机制），必选
            //由调用端赋值，取值范围（1~100）。当TMID+msgType的值一样时，仅缓存最新的一条消息
            int msgType = 1;

            //可选
            //如果请求消息中，没有带，则MC根据ProviderID+timestamp生成，各个字段之间用下划线连接
            //String requestID = "1_1362472787848";

            //unix时间戳，可选
            //格式：2013-08-29 19:55
            // 消息过期删除时间
            //如果不填写，默认超时时间为当前时间后48小时
            //String expire_time = dataFormat.format(currentTime + 3 * 60 * 60 * 1000);

            //构造请求
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("deviceTokenList", tokenList);
            hashMap.put("message", content);
            hashMap.put("cacheMode", cacheMode);
            hashMap.put("msgType", msgType);
            //hashMap.put("requestID", requestID);
            //hashMap.put("expire_time", expire_time);

            //设置http超时时间
            client.setTimeout(10000, 15000);
            //接口调用
            PushRet resp = client.call("openpush.message.batch_send", hashMap, PushRet.class);

            logger.info("HWPushUtil-群发接口消息响应:" + resp.getResultcode() + ",message:" + resp.getMessage());
            if (resp.getResultcode() == 0) {
                flag = true;
            }

        } catch (NSPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("推送pushDeviceListAndroid调用结果:" + flag);
        return flag;

    }

    public static void main(String[] args) {

//        String token = "0861451035208324300000337100CN01";
//        String msg = HWPushUtil.msg_str("1", "35", String.valueOf(Constants.CODE_MATERIAL), "测试标题", "测试内容", "1", "", "1213123");
//        HWPushUtil.pushSingleDeviceMessage("测试标题", token, msg);

        List<String> tokenList = new ArrayList<String>();
        tokenList.add("0861451035208324300000337100CN01");

        String msg = HWPushUtil.msg_str("1", "35", String.valueOf(Constants.CODE_MATERIAL), "测试标题", "测试内容", "1", "", "1213123");
        HWPushUtil.pushDeviceListAndroid("测试标题", msg, tokenList);

    }

}

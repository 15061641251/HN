package com.sbq.tools;

/**
 * 常量类
 *
 * @author
 */
public class Constant {

//    public static final String KEY = "czcgj";

    // 返回码
    public static final String CODE_SUCCESS = "0";

    public static final String CODE_SUCCESS_MSG = "成功";

    public static final String CODE_REQUEST_ERROR = "-1";

    public static final String CODE_REQUEST_ERROR_MSG = "请求参数有误";

    public static final String CODE_SERVER_ERROR = "-1";

    public static final String CODE_SERVER_ERROR_MSG = "服务器异常";

//    public static final int ENVIRONMENT = 1;// 1-表示开发环境,2-表示真实环境
//
//    public static boolean getRunEnvironment() {
//        if (ENVIRONMENT == 1) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    public static final String CERTIFICATION = "wxgsoa@sbq$2016";

    /**
     * android使用信鸽推送还是极光
     * 1:极光,2:信鸽,3:华为
     */
    public static final int Android_HUAWEI_PUSH = 3;

    /**
     * android使用信鸽推送还是极光
     * 1:极光,2:信鸽,3:华为
     */
    public static final int Android_XingeOrJPush = 2;
    /**
     * ios使用信鸽推送还是极光
     * 1:极光,2:信鸽
     */
    public static final int IOS_XingeOrJPush = 2;

    // ********************tencent信鸽推送********************
    public static final Long Andorid_ACCESS_ID = (long) 2100238255L;
    public static final String Andorid_SECRET_KEY = "dd8e37376551c67c374490b8ddb40c92";

    public static final Long IOS_ACCESS_ID = (long) 2200238254L;
    public static final String IOS_SECRET_KEY = "6ebf942ba56d7d15e789600151d97fbe";


    // ********************tencent信鸽推送  纳税人********************
    public static final Long Andorid_ACCESS_ID_NSR = (long) 2100248520;
    public static final String Andorid_SECRET_KEY_NSR = "8566afafdc7eaaa19c388c33b43809b1";

    public static final Long IOS_ACCESS_ID_NSR = (long) 2200248297L;
    public static final String IOS_SECRET_KEY_NSR = "221f6543a6a35eaa16f0a423ebc8d092";

    // ********************JPush信鸽推送********************

    public static final String CONTEXT_ATTRIBUTE_APPLYCATION_ID = "application_id";

    public static final String SESSION_ATTRIBUTE_USER = "user";
    public static final String MENU_IDS = "menuids";

    public static final int CODE_SESSION_TIMEOUT = 1000;
    public static final int CODE_SECURITY_ERROR = 1001;

    public static final int YES = 1;
    public static final int NO = 0;

    // ********************ehcache key********************

    public static final String KEY_CACHE_GETALLENABLEDAUTHORITYLIST = "getALLEnabledAuthorityList";

    public static final String GETMENUSBYMAP = "getMenusByMap";

    public static final String BULLETIN_TYPE_KEY = "通知公告类型";

    public static final String MATERIAL_TYPE_KEY = "物资类型";

    public static final String VERIFY_TYPE_KEY = "申请类型";

    public static final String ADMIN_USER_ID = "1";

    public static final String DEFAULT_PASSWORD = "123456";
}

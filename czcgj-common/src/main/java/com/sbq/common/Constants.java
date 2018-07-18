package com.sbq.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Constants {

    Charset UTF_8 = StandardCharsets.UTF_8;

    byte[] EMPTY_BYTES = new byte[0];
    String EMPTY_STRING = "";
    String ANY_HOST = "0.0.0.0";

    String WEB_PROJECT_NAME = "czcgj-web";

    String WS_PROJECT_NAME = "czcgj-ws";

    String DOWN_LOAD_PATH = "/file/download?uuid=";

    /**
     * 短信提醒
     */
    int TIP_SMS = 1;

    /**
     * 系统提醒
     */
    int TIP_SYSTEM = 2;

    /**
     * 邮件提醒
     */
    int TIP_EMAIL = 3;


    /**
     * 普通日程模块代码
     */
    int CODE_TODO = 1000;

    /**
     * 通知公告模块代码
     */
    int CODE_BULLETIN = 1002;

    /**
     * 会议模块代码
     */
    int CODE_MEETING = 1004;

    /**
     * 工作预约模块代码
     */
    int CODE_WORK_APPOINTMENT = 1006;

    /**
     * 用车申请模块代码
     */
    int CODE_UER_CAR = 1008;

    /**
     * 物资模块代码
     */
    int CODE_MATERIAL = 1010;

    /**
     * 请假模块代码
     */
    int CODE_LEAVE = 1012;

    /**
     * 假期模块代码
     */
    int CODE_VACATION = 1014;

    /**
     * 加班申请
     */
    int CODE_OVERTIME = 1016;

    /**
     * 注册模块代码(预留)
     */
    int CODE_REGISTER = 1018;

    /**
     * 值班模块代码
     */
    int CODE_DUTY = 1020;

    /**
     * 值班提醒
     */
    int CODE_DUTY_TIP = 1022;

    /**
     * 人员角色-主任
     */
    int ROLE_DIRECTOR = 11;

    //副主任
    int ROLE_VICE_DIRECTOR = 10;

    //科长
    int ROLE_SECTION = 9;

    //副科长
    int ROLE_VICE_SECTION = 8;

    /**
     * 节假日
     */
    String[] holiday = new String[]{

        "2017-01-01", "2017-01-28", "2017-04-04", "2017-05-01", "2017-05-30", "2017-10-04", "2017-10-01",
        "2018-01-01", "2018-02-16", "2018-04-05", "2018-05-01", "2018-06-18", "2018-09-24", "2018-10-01",
        "2019-01-01", "2019-02-05", "2019-04-05", "2019-05-01", "2019-06-07", "2019-09-13", "2019-10-01",
        "2020-01-01", "2020-01-25", "2020-04-05", "2020-05-01", "2020-06-25", "2020-10-01",
        "2021-01-01", "2021-02-12", "2021-04-05", "2021-05-01", "2021-06-14", "2021-09-21", "2021-10-01"

    };
}

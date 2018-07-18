package com.sbq.tools;

import com.sbq.entity.dto.CzcgLogDto;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CzcgUtil {


    private static DecimalFormat df = new DecimalFormat("#.00");

    /**
     * 计算三个方向的角度
     *
     * @return
     */
    public static Map getAngelByStr(String str, String device_id) {

        Map map = new HashMap();

        String[] splits = str.split(" ");

        float accex = Float.valueOf(splits[1]);
        float accey = Float.valueOf(splits[3]);
        float accez = Float.valueOf(splits[5]);

        double x = ADXL345_Get_Angle(accex, accey, accez, 1);
        double y = ADXL345_Get_Angle(accex, accey, accez, 2);
        double z = ADXL345_Get_Angle(accex, accey, accez, 0);

        map.put("x", Math.abs(x));
        map.put("y", Math.abs(y));
        map.put("z", Math.abs(z));

        return map;
    }

    /**
     * 计算角度
     *
     * @param x   x方向的重力加速度分量
     * @param y   y方向的重力加速度分量
     * @param z   z方向的重力加速度分量
     * @param dir 要获得的角度;0-与Z轴的角度;1-与X轴的角度;2-与Y轴的角度
     * @return 角度值 单位 0.1°
     */
    public static double ADXL345_Get_Angle(float x, float y, float z, int dir) {

        double temp;
        double res = 0;

//        z += 1000;
//        y -= 1000;
//        z -= 1000;

        switch (dir) {
            case 0://与Z轴的角度
                temp = z / Math.sqrt((x * x + y * y));
                res = Math.atan(temp);
                break;
            case 1://与X轴的角度
                temp = x / Math.sqrt((y * y + z * z));
                res = Math.atan(temp);
                break;
            case 2://与Y轴的角度
                temp = y / Math.sqrt((x * x + z * z));
                res = Math.atan(temp);
                break;
        }
        return res * 180 / 3.14;
    }

    /**
     * 针对获取物联网平台的日志一条记录,进行解析返回城管日志对象
     *
     * @param device_type 设备类型 1:重庆厂家,2:常州本地厂家
     * @return
     */
    public static CzcgLogDto returnCzcgLogDtoByLogAndDeviceType(JSONArray array, String device_id, Integer device_type) {

        CzcgLogDto czcgLogDto = new CzcgLogDto();

        czcgLogDto.setDevice_type(device_type);

        if (device_type == 1) {
            //重庆设备

            for (int i = 0; i < array.size(); i++) {

                JSONObject object = array.getJSONObject(i);

                String id = object.getStr("id");

                if (id.equals("move")) {
                    czcgLogDto.setMove(object.getStr("value", ""));
                }

                if (id.equals("liquid")) {
                    czcgLogDto.setLiquid(object.getStr("value", ""));
                }

                if (id.equals("pos")) {
                    czcgLogDto.setPos(object.getStr("value", ""));
                }

                if (id.equals("acce")) {

                    String acce = object.getStr("value", "");

                    Map map = getAngelByStr(acce, device_id);

                    float o1 = Math.abs(Float.parseFloat(map.get("x").toString()));
                    float o2 = Math.abs(Float.parseFloat(map.get("y").toString()));
                    float o3 = Math.abs(Float.parseFloat(map.get("z").toString()));

                    //TODO:相应的放置方式，对应的减去90角度

                    InputStream inputStream = CzcgUtil.class.getResourceAsStream("/czcgj_device.properties");

                    try {

                        PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

                        String angle_str = propertiesUtil.getString(device_id);

                        if (StringUtils.isNoneBlank(angle_str)) {
                            //需要纠偏
                            String angle[] = angle_str.split(",");

                            float x = Float.parseFloat(angle[0]);
                            float y = Float.parseFloat(angle[1]);
                            float z = Float.parseFloat(angle[2]);

                            o1 += x;
                            o2 += y;
                            o3 += z;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    //FIXME:对特殊设备进行处理
                    if ("80086668".equals(device_id)
                            || "80086684".equals(device_id)
                            || "80086685".equals(device_id)
                            || "80086683".equals(device_id)
                            || "80086700".equals(device_id)
                            || "80086703".equals(device_id)
                            || "80086701".equals(device_id)
                            || "80086677".equals(device_id)
                            || "80086674".equals(device_id)
                            || "80086706".equals(device_id)
                            || "80086712".equals(device_id)
                            || "80086711".equals(device_id)
                            || "80086686".equals(device_id)
                            || "80086702".equals(device_id)) {
                        o1 = doWithAngle(o1, 5);
                        o2 = doWithAngle(o2, 5);
                        o3 = doWithAngle(o3, 5);
                    }


                    try {

                        czcgLogDto.setX(Double.valueOf(df.format(o1)));
                        czcgLogDto.setY(Double.valueOf(df.format(o2)));
                        czcgLogDto.setZ(Double.valueOf(df.format(o3)));

                    } catch (NumberFormatException e) {

                    }

//                for (int j = 0; j < 3; j++) {
//
//                    double db = CzcgUtil.ADXL345_Get_Angle(o1, o2, o3, j);
//
//                    switch (j) {
//                        case 0:
//                            czcgLogDto.setZ(db);
//                            break;
//                        case 1:
//                            czcgLogDto.setX(db);
//                            break;
//                        case 2:
//                            czcgLogDto.setY(db);
//                            break;
//                    }
//                }

                }

                if (id.equals("batvoit")) {
                    czcgLogDto.setBatvoit(object.getStr("batvoit", ""));
                }

                if (id.equals("cycle")) {
                    czcgLogDto.setCycle(object.getStr("cycle", ""));
                }
            }

        } else if (device_type == 2) {
            //常州本地设备

            for (int i = 0; i < array.size(); i++) {

                JSONObject object = array.getJSONObject(i);

                String id = object.getStr("id");

                if (id.equals("xAngle")) {
                    czcgLogDto.setX(object.getDouble("value"));
                }

                if (id.equals("yAngle")) {
                    czcgLogDto.setY(object.getDouble("value"));
                }

                if (id.equals("zAngle")) {
                    czcgLogDto.setZ(object.getDouble("value"));
                }

                InputStream inputStream = CzcgUtil.class.getResourceAsStream("/czcgj_device.properties");

                try {

                    PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

                    String angle_str = propertiesUtil.getString(device_id);

                    if (StringUtils.isNoneBlank(angle_str)) {
                        //需要纠偏
                        String angle[] = angle_str.split(",");

                        float x = Float.parseFloat(angle[0]);
                        float y = Float.parseFloat(angle[1]);
                        float z = Float.parseFloat(angle[2]);

                        czcgLogDto.setX(czcgLogDto.getX() + x);
                        czcgLogDto.setY(czcgLogDto.getY() + y);
                        czcgLogDto.setZ(czcgLogDto.getZ() + z);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return czcgLogDto;

    }

    /**
     * 针对获取物联网平台的日志一条记录,进行解析返回城管日志对象
     *
     * @return
     */
//    public static CzcgLogDto returnCzcgLogDtoByLog(JSONArray array, String device_id) {
//
//        CzcgLogDto czcgLogDto = new CzcgLogDto();
//
//        for (int i = 0; i < array.size(); i++) {
//
//            JSONObject object = array.getJSONObject(i);
//
//            String id = object.getStr("id");
//
//            if (id.equals("move")) {
//                czcgLogDto.setMove(object.getStr("value", ""));
//            }
//
//            if (id.equals("liquid")) {
//                czcgLogDto.setLiquid(object.getStr("value", ""));
//            }
//
//            if (id.equals("pos")) {
//                czcgLogDto.setPos(object.getStr("value", ""));
//            }
//
//            if (id.equals("acce")) {
//
//                String acce = object.getStr("value", "");
////                StringBuilder sb = new StringBuilder();
////                String[] s1;
////                String[] s2;
////                String[] s3;
////
////                String[] splits = acce.split("acce");
////
////                s1 = splits[1].split("x");
////                s2 = splits[2].split("y");
////                s3 = splits[3].split("z");
////
////                sb.delete(0, sb.length());
////
////                sb.append(s1[1]);
////                sb.append(s2[1]);
////                sb.append(s3[1]);
////
////                String sss = sb.toString();
////                sss.replaceAll("  ", " ");
//
//                Map map = getAngelByStr(acce, device_id);
//
//                float o1 = Math.abs(Float.parseFloat(map.get("x").toString()));
//                float o2 = Math.abs(Float.parseFloat(map.get("y").toString()));
//                float o3 = Math.abs(Float.parseFloat(map.get("z").toString()));
//
//                //TODO:相应的放置方式，对应的减去90角度
//
////                if ("10826030".equals(device_id)) {
////                    //青龙高速交叉口
////                    o2 -= 60;
////                    o3 -= 25;
////                }
//
//                InputStream inputStream = CzcgUtil.class.getResourceAsStream("/czcgj_device.properties");
//
//                try {
//
//                    PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);
//
//                    String angle_str = propertiesUtil.getString(device_id);
//
//                    if (StringUtils.isNoneBlank(angle_str)) {
//                        //需要纠偏
//                        String angle[] = angle_str.split(",");
//
//                        float x = Float.parseFloat(angle[0]);
//                        float y = Float.parseFloat(angle[1]);
//                        float z = Float.parseFloat(angle[2]);
//
//                        o1 += x;
//                        o2 += y;
//                        o3 += z;
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                //FIXME:对特殊设备进行处理
//                if ("80086668".equals(device_id)
//                        || "80086684".equals(device_id)
//                        || "80086685".equals(device_id)
//                        || "80086683".equals(device_id)
//                        || "80086700".equals(device_id)
//                        || "80086703".equals(device_id)
//                        || "80086701".equals(device_id)
//                        || "80086677".equals(device_id)
//                        || "80086674".equals(device_id)
//                        || "80086706".equals(device_id)
//                        || "80086712".equals(device_id)
//                        || "80086711".equals(device_id)
//                        || "80086686".equals(device_id)
//                        || "80086702".equals(device_id)) {
//                    o1 = doWithAngle(o1, 5);
//                    o2 = doWithAngle(o2, 5);
//                    o3 = doWithAngle(o3, 5);
//                }
//
//
//                try {
//
//                    czcgLogDto.setX(Double.valueOf(df.format(o1)));
//                    czcgLogDto.setY(Double.valueOf(df.format(o2)));
//                    czcgLogDto.setZ(Double.valueOf(df.format(o3)));
//
//                } catch (NumberFormatException e) {
//
//                }
//
////                for (int j = 0; j < 3; j++) {
////
////                    double db = CzcgUtil.ADXL345_Get_Angle(o1, o2, o3, j);
////
////                    switch (j) {
////                        case 0:
////                            czcgLogDto.setZ(db);
////                            break;
////                        case 1:
////                            czcgLogDto.setX(db);
////                            break;
////                        case 2:
////                            czcgLogDto.setY(db);
////                            break;
////                    }
////                }
//
//            }
//
//            if (id.equals("batvoit")) {
//                czcgLogDto.setBatvoit(object.getStr("batvoit", ""));
//            }
//
//            if (id.equals("cycle")) {
//                czcgLogDto.setCycle(object.getStr("cycle", ""));
//            }
//        }
//
//        return czcgLogDto;
//    }

    /**
     * 强制对角度进行处理,保证其绝对值在指定范围内
     *
     * @return
     */
    private static float doWithAngle(float angle, float threshold) {

        if (angle < 0) {
            //负数，要进行加法操作
            while (Math.abs(angle) > threshold) {
                //超过阀值
                angle += threshold;
            }
        } else if (angle > 0) {
            //正数，要进行减法操作
            while (angle > threshold) {
                //超过阀值
                angle -= threshold;
            }
        }

        return angle;
    }

    public static void main(String[] args) {

        String str = "accex -1497.60 accey -1037.40 accez -514.80";
        Map map = getAngelByStr(str, "");

        System.out.println(map);
    }
}

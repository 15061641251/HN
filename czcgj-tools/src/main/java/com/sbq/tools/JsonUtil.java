package com.sbq.tools;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

/**
 * json工具类
 *
 * @author wuyy
 * @version 1.0 2010-11-12
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 把json输出到前台
     *
     * @param response
     * @param json
     */
    public static void printJson(HttpServletResponse response, String json) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 根据json对象返回一个实体对象（目前仅适用成员变量都是基本数据类型的对象）
     *
     * @param jo          json对象，只能是一个基本的json对象
     * @param entityClass 实体class
     * @param prefix      前缀
     * @return 实体对象
     */
    public static Object json2bean(JSONObject jo, Class<?> entityClass, String prefix) {
        Object o = null;
        try {
            o = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return o;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return o;
        }

        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();
            for (int i = 0; i < props.length; i++) {
                String name = props[i].getName();
                String jsonKey = name;
                if (!NullUtil.isNull(prefix)) {
                    jsonKey += prefix;
                }
                if (!((Map<?, ?>) jo).containsKey(jsonKey)) {
                    continue;
                }
                Class<?> type = props[i].getPropertyType();
                if (type.equals(byte.class) || type.equals(Byte.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Byte.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(short.class) || type.equals(Short.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Short.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(int.class) || type.equals(Integer.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Integer.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(long.class) || type.equals(Long.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Long.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(float.class) || type.equals(Float.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Float.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(double.class) || type.equals(Double.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Double.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, Boolean.valueOf(jo.getString(jsonKey)));
                    }
                } else if (type.equals(String.class)) {
                    props[i].getWriteMethod().invoke(o, jo.getString(jsonKey));
                } else if (type.equals(BigInteger.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, BigInteger.valueOf(Long.valueOf(jo.getString(jsonKey))));
                    }
                } else if (type.equals(BigDecimal.class)) {
                    if (!NullUtil.isNull(jo.getString(jsonKey))) {
                        props[i].getWriteMethod().invoke(o, BigDecimal.valueOf(Long.valueOf(jo.getString(jsonKey))));
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return o;
    }

    /**
     * 把java对象转化为json
     *
     * @param o
     * @return
     */
    public static String object2json(Object o) {
        StringBuilder json = new StringBuilder();
        if (o == null) {
            json.append("\"\"");
        } else if (o instanceof String) {
            json.append("\"").append(string2json(o.toString())).append("\"");
        } else if (o instanceof Byte || o instanceof Short
                || o instanceof Long
                || o instanceof Float || o instanceof Double
                || o instanceof BigInteger || o instanceof BigDecimal
                || o instanceof Boolean) {
            json.append(o.toString());
        } else if (o instanceof Integer) {
            json.append(o.toString());
        } else if (o instanceof byte[] || o instanceof short[]
                || o instanceof int[] || o instanceof long[]
                || o instanceof float[] || o instanceof double[]
                || o instanceof boolean[]) {
            json.append(array2json(o));
        } else if (o instanceof Object[]) {
            json.append(array2json((Object[]) o));
        } else if (o instanceof Collection<?>) {
            json.append(collection2json((Collection<?>) o));
        } else if (o instanceof Map<?, ?>) {
            json.append(map2json((Map<?, ?>) o));
        } else if (o.getClass().getName().indexOf("java.") == 0 || o.getClass().getName().indexOf("javax.") == 0) {
            json.append("\"").append(string2json(o.toString())).append("\"");
        } else {
            json.append(bean2json(o));
        }
        return json.toString();
    }

    private static String bean2json(Object o) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();
            for (int i = 0; i < props.length; i++) {
                String name = props[i].getName();
                String value = object2json(props[i].getReadMethod().invoke(o));
                json.append("\"").append(name).append("\"").append(":").append(value);
                if (i != props.length - 1) {
                    json.append(",");
                }
            }
            json.append("}");
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static String string2json(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\'':
                    sb.append("\\'");
                    break;
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {//控制字符
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }

    private static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * 基本类型数组转化成json
     *
     * @param array
     * @return
     */
    private static String array2json(Object array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array instanceof byte[]) {
            byte[] data = (byte[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof short[]) {
            short[] data = (short[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof int[]) {
            int[] data = (int[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof long[]) {
            long[] data = (long[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof float[]) {
            float[] data = (float[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof double[]) {
            double[] data = (double[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        } else if (array instanceof boolean[]) {
            boolean[] data = (boolean[]) array;
            for (int i = 0; i < data.length; i++) {
                json.append("\"").append(data[i]).append("\"");
                if (i != data.length - 1) {
                    json.append(",");
                }
            }
        }
        json.append("]");
        return json.toString();
    }

    private static String collection2json(Collection<?> collection) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (collection.size() > 0) {
            for (Object obj : collection) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    private static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static void main(String args[]) {
        System.out.println(string2json("\'"));
    }
}

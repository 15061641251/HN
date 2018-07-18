package com.sbq.tools.json;

import com.alibaba.fastjson.JSON;
import com.sbq.common.Constants;
import com.sbq.common.Strings;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Jsons {

    public static String toJson(Object bean) {
        try {
            return JSON.toJSONString(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {

        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(byte[] json, Class<T> clazz) {
        return fromJson(new String(json, Constants.UTF_8), clazz);
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> type) {
        try {
            return JSON.parseArray(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return JSON.parseObject(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean mayJson(String json) {
        if (Strings.isBlank(json))
            return false;
        if (json.charAt(0) == '{' && json.charAt(json.length() - 1) == '}')
            return true;
        if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']')
            return true;
        return false;
    }

    public static String toJson(Map<String, String> map) {
        if (map == null || map.isEmpty())
            return "{}";
        StringBuilder sb = new StringBuilder(64 * map.size());
        sb.append('{');
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            append(it.next(), sb);
        }
        while (it.hasNext()) {
            sb.append(',');
            append(it.next(), sb);
        }
        sb.append('}');
        return sb.toString();
    }

    private static void append(Map.Entry<String, String> entry, StringBuilder sb) {
        String key = entry.getKey(), value = entry.getValue();
        if (value == null)
            value = Strings.EMPTY;
        sb.append('"').append(key).append('"');
        sb.append(':');
        sb.append('"').append(value).append('"');
    }
}

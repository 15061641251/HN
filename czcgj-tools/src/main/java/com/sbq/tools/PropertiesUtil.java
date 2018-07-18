package com.sbq.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * 属性配置文件工具类
 * Created by zhangyuan on 2017/2/15.
 */
public class PropertiesUtil {

    private Properties prop = new Properties();

    /**
     * 指定文件路径的文件流
     *
     * @param inputStream
     * @throws Exception
     */
    public PropertiesUtil(InputStream inputStream) throws Exception {

        prop.load(inputStream);
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLong(String key, int defaultValue) {
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Long.parseLong(value);
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = prop.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new Boolean(value);
    }

    public String getString(String key) {
        try {
            return prop.getProperty(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}

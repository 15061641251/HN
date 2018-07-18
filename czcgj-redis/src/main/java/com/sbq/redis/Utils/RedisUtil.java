package com.sbq.redis.Utils;

import com.sbq.tools.KryoSerializeUtil;
import com.sbq.tools.PropertiesUtil;
import com.sbq.tools.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Redis连接池
 * Created by zhangyuan on 2017/2/15.
 */
public class RedisUtil {

    //Redis服务器IP
    private static String HOST = "127.0.0.1";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "admin";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    private static int MIN_IDLE = 5;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private static int MAX_TOTAL = 100;

    private static int EXPIRE = 600;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            //读取配置文件内容
            InputStream inputStream = RedisUtil.class.getResourceAsStream("/redis.properties");
            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

            String host = propertiesUtil.getString("redis.host");
            String auth = propertiesUtil.getString("redis.auth");
            int port = propertiesUtil.getInt("redis.port", 6379);
            int maxActive = propertiesUtil.getInt("redis.maxActive", -1);
            int minIdle = propertiesUtil.getInt("redis.minIdle", 5);
            int maxIdle = propertiesUtil.getInt("redis.maxIdle", 20);
            int maxTotal = propertiesUtil.getInt("redis.maxTotal", 100);
            int expiration = propertiesUtil.getInt("redis.expiration", 600);
            int maxWait = propertiesUtil.getInt("redis.maxWait", -1);
            int timeout = propertiesUtil.getInt("redis.timeout", 1000);
            boolean testOnBorrow = propertiesUtil.getBoolean("redis.testOnBorrow", false);

            EXPIRE = expiration;
            HOST = host;
            PORT = port;
            AUTH = auth;
            MAX_ACTIVE = maxActive;
            MAX_IDLE = maxIdle;
            MIN_IDLE = minIdle;
            MAX_WAIT = maxWait;
            TIMEOUT = timeout;
            TEST_ON_BORROW = testOnBorrow;
            MAX_TOTAL = maxTotal;

            JedisPoolConfig config = new JedisPoolConfig();

            config.setMinIdle(MIN_IDLE);
            config.setMaxIdle(MAX_IDLE);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxWaitMillis(maxWait);

            if (StringUtils.isBlank(AUTH)) {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
            } else {
                jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * 获取Jedis实例
     * * @return
     */
    public synchronized static Jedis getJedis() {

        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public synchronized static void returnResource(final Jedis jedis) {

        try {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set值
     * <p>
     * 如果多次set，建议手动获取连接,调用jedis的set后,手动关闭连接
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = getJedis();

            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * set值
     * <p>
     * 如果多次set，建议手动获取连接,调用jedis的set后,手动关闭连接
     *
     * @param key
     * @param object
     */
    public static void set(String key, Object object) {


        Jedis jedis = null;
        try {
            jedis = getJedis();

            jedis.set(key, KryoSerializeUtil.serializationObject(object));
//        jedis.set(key.getBytes(), SerializeUtil.serialize(object));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 删除对应的缓存
     *
     * @param keys
     */
    public static void del(String... keys) {

        Jedis jedis = null;
        try {
            jedis = getJedis();

            jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }

    }

    /**
     * 判断对应的key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {

        Jedis jedis = null;
        try {
            jedis = getJedis();

            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return false;

    }

    /**
     * 在某段时间后失效
     *
     * @return
     */
    public static final Long expire(final String key, final int seconds) {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    public static final Long expireAt(final String key, final long unixTime) {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return 0L;

    }

    /**
     * get 值
     * <p>
     * 如果多次get，建议手动获取连接,调用jedis的get后,手动关闭连接
     *
     * @param key
     * @return
     */
    public static Object get(String key) {

        Jedis jedis = null;

        try {

            jedis = getJedis();
            String result = jedis.get(key);
            //return SerializeUtil.deserialize(value_byte);
            return KryoSerializeUtil.deserializationObject(result, Object.class);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    /**
     * get值
     * <p>
     * 如果多次get，建议手动获取连接,调用jedis的get后,手动关闭连接
     *
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> cls) {

        Jedis jedis = null;

        try {

            jedis = getJedis();
            String result = jedis.get(key);

            //return SerializeUtil.deserialize(value_byte, cls);
            return (T) KryoSerializeUtil.deserializationObject(result, cls);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return null;
    }

}


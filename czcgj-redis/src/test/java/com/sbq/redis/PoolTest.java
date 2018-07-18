package com.sbq.redis;

import com.sbq.redis.Utils.RedisUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangyuan on 2017/2/15.
 */
public class PoolTest {


    Jedis jedis;

    @Before
    public void before() {
        jedis = RedisUtil.getJedis();
    }

    @After
    public void after() {
        RedisUtil.returnResource(jedis);
    }

    @Test
    public void test() {

        try {

//            InputStream inputStream = PoolTest.class.getResourceAsStream("/redis.properties");
//            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);
//
//            String host = propertiesUtil.getString("redis.host");
//
//            System.out.println(host);

//            Jedis jedis = RedisUtil.getJedis();

//            System.out.println(jedis.hkeys("user"));//返回map对象中的所有key

            for (int i = 0; i < 20; i++) {
                jedis.get("testConcurrent");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

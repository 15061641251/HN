package com.sbq.mq.rabbitmq.Producer;

/**
 * Created by zhangyuan on 2017/2/25.
 */
public interface MQProducer {

    /**
     * 发送消息到指定队列
     *
     * @param queueKey
     * @param object
     */
    public void sendDataToQueue(String queueKey, Object object);
}

package com.sbq.mq.rabbitmq.tools;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.sbq.common.Constants;
import com.sbq.common.MQEntity;
import com.sbq.push.hwpush.HWPushUtil;
import com.sbq.tools.DateUtil;
import com.sbq.tools.JsonUtil;
import com.sbq.tools.PropertiesUtil;
import net.jodah.lyra.ConnectionOptions;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.RecoveryPolicy;
import net.jodah.lyra.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangyuan on 2017/2/23.
 */
public class RabbitMQUtil {


    private static ConnectionOptions options = null;

    static {

        InputStream inputStream = RabbitMQUtil.class.getResourceAsStream("/mq/mq.properties");

        try {

            PropertiesUtil propertiesUtil = new PropertiesUtil(inputStream);

            String host = propertiesUtil.getString("rabbitmq.host");
            int port = propertiesUtil.getInt("rabbitmq.port", 5672);
            String account = propertiesUtil.getString("rabbitmq.account");
            String pwd = propertiesUtil.getString("rabbitmq.pwd");

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setPort(port);
            connectionFactory.setUsername(account);
            connectionFactory.setPassword(pwd);
            connectionFactory.setAutomaticRecoveryEnabled(true);
            connectionFactory.setNetworkRecoveryInterval(10000);
            connectionFactory.useNio();

            options = new ConnectionOptions(connectionFactory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {

        Config config = new Config().withRecoveryPolicy(new RecoveryPolicy().withBackoff(Duration.seconds(1), Duration.seconds(30)).withMaxAttempts(20));

        Connection connection = null;

        try {
            connection = Connections.create(options, config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 关闭连接
     *
     * @param channel
     * @param connection
     */
    public static void realeaseConnection(Channel channel, Connection connection) {
        try {
            try {
                channel.close();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     *
     * @param connection
     */
    public static void realeaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送信息到队列中
     *
     * @param queue_name
     * @param message
     */
    public static void sendMessage(String queue_name, String message) {

        Connection connection = RabbitMQUtil.getConnection();

        try {

            Channel channel = connection.createChannel();
            channel.queueDeclare(queue_name, true, false, false, null);
            channel.basicPublish("", queue_name, null, message.getBytes());

            realeaseConnection(channel, connection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送消息到队列中
     *
     * @param queue_name  队列名
     * @param messageList msgList
     */
    public static void sendMessageList(String queue_name, List<String> messageList) {

        Connection connection = RabbitMQUtil.getConnection();

        try {

            Channel channel = connection.createChannel();
            channel.queueDeclare(queue_name, true, false, false, null);

            for (String msg : messageList) {
                channel.basicPublish("", queue_name, null, msg.getBytes());
            }
            realeaseConnection(channel, connection);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void receiveMessage(String queue_name) {

        Connection connection = RabbitMQUtil.getConnection();

        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue_name, true, false, false, null);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queue_name, true, consumer);

            while (true) {
                //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" Received '" + message + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        RabbitMQUtil.sendMessage("push", "helloWorld");
//        List<String> list = new ArrayList<>();
//        list.add("123");
//        list.add("1233");
//        list.add("12312");
//        list.add("12333");
//        RabbitMQUtil.sendMessageList("hello", list);


        Map<String, Object> ext = new HashMap<>();
        ext.put("title", "测试申请");
        ext.put("formType", "1016");
        ext.put("foreign_int_id", "1");
        ext.put("user_int_id", "102");

        MQEntity mqEntity = new MQEntity();
        mqEntity.setType(Constants.TIP_SYSTEM);
        mqEntity.setExt(ext);
        RabbitMQUtil.sendMessage("push", JsonUtil.object2json(mqEntity));

//        RabbitMQUtil.receiveMessage("push");
//
//        String content = HWPushUtil.msg_str(DateUtil.getCurrentDateString("yyyyMMddHHmmss"), "1", "1016", "测试标题", "测试内容", "", "", DateUtil.getCurrentDateString("yyyy-MM-dd"));
//        HWPushUtil.pushSingleDeviceMessage("测试标题", "0861451035208324300000337100CN01", content);

    }
}

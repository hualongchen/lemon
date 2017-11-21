package com.lemon.chen;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * Created by chenhualong on 2017/8/4.
 */
public class RabbitMqProviderTest {


    /**
     * 步骤：
     *
     * 1. 连接到rabbitmq
     *
     * 2. 获得信道
     *
     * 3. 声明交换器
     *
     * 4. 声明队列
     *
     * 5. 队列与交换器进行绑定
     *
     * 6. 消费消息
     *
     * 7. 关闭信道
     *
     * 8.关闭连接
     */

    public final static String EXCHANGE_NAME = "test-exchange";


    public static void main(String[] args) throws Throwable{


        /**
         * 配置amqp broker 连接信息
         */
        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setUsername("root");
        facotry.setPassword("root");
        facotry.setVirtualHost("/");
        facotry.setHost("10.55.120.247");

        Connection conn = facotry.newConnection(); //获取一个链接
        //通过Channel进行通信
        Channel channel = conn.createChannel();

        // channel.exchangeDeclare(Send.EXCHANGE_NAME, "direct", true); //如果消费者已创建，这里可不声明

        //需要显示调用，否则函数无法回调
        channel.confirmSelect(); //Enables publisher acknowledgements on this channel
        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("[handleNack] :" + deliveryTag + "," + multiple);

            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("[handleAck] :" + deliveryTag + "," + multiple);
            }
        });

        String message = "lkl-";
        //消息持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
        //发送多条信息，每条消息对应routekey都不一致
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(EXCHANGE_NAME, message + (i % 2), MessageProperties.PERSISTENT_TEXT_PLAIN,
                    (message + i).getBytes());
            System.out.println("[send] msg " + (message + i) + " of routingKey is " + (message + (i % 2)));
        }


    }
}

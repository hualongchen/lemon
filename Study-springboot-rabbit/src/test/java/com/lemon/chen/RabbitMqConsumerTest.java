package com.lemon.chen;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * Created by chenhualong on 2017/8/4.
 */
public class RabbitMqConsumerTest {


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

        ConnectionFactory facotry = new ConnectionFactory();
        facotry.setUsername("root");
        facotry.setPassword("root");
        facotry.setVirtualHost("/");
        facotry.setHost("10.55.120.247");

        Connection conn = facotry.newConnection(); //获取一个链接
        //通过Channel进行通信
        Channel channel = conn.createChannel();
        int prefetchCount = 1;
        channel.basicQos(prefetchCount); //保证公平分发

        boolean durable = true;
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", durable); //按照routingKey过滤
        //声明队列
        String queueName = channel.queueDeclare("queue-01", true, true, false, null).getQueue();
        //将队列和交换机绑定
        String routingKey = "lkl-0";
        //队列可以多次绑定，绑定不同的交换机或者路由key
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

        //创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //将消费者和队列关联
        channel.basicConsume(queueName, false, consumer); // 设置为false表面手动确认消息消费

        //获取消息

        System.out.println(" Wait message ....");
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            String key = delivery.getEnvelope().getRoutingKey();

            System.out.println("  Received '" + key + "':'" + msg + "'");
            System.out.println(" Handle message");
            TimeUnit.SECONDS.sleep(3); //mock handle message
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); //确定该消息已成功消费
        }
    }
}

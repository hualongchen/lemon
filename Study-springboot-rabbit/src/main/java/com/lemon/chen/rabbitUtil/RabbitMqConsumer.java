package com.lemon.chen.rabbitUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhualong on 2017/8/7.
 */
public class RabbitMqConsumer {

    public static void main(String[] args) throws Throwable{


         String queueName ="";
        /**
         * 第一步，建立broker，连接rabbitmq
         */
        Connection connection  = RabbitmqFactoryUtil.getConnection();

        /**
         * 第二步，获取信道
         */

        Channel channel = connection.createChannel(); //声明消息通道

        /**
         * 第三步，声明交换器
         */

        RabbitMqExchange exchange = RabbitMqExchange.DIRECT;


        /**
         * 第四步，把队列和交换器进行绑定起来
         */
        switch (exchange){

            case DEFAULT:
                //队列的相关参数需要与第一次定义该队列时相同，否则会出错，
                // 使用channel.queueDeclarePassive()可只被动绑定已有队列，而不创建
                channel.queueDeclare(RabbitMqQueue.DEFAULT_QUEUE, true, false, true, null);
                break ;

            case FANOUT:
                //广播给所有队列  接收方也必须通过fanout交换机获取消息,所有连接到该交换机的consumer均可获取消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃


                //定义一个交换机
                //参数1：交换机名称
                //参数2：交换机类型
                //参数3：交换机持久性，如果为true则服务器重启时不会丢失
                //参数4：交换机在不被使用时是否删除
                //参数5：交换机的其他属性
                channel.exchangeDeclare(RabbitMqQueue.FANOUT_MONEY_QUEUE, "fanout", false, true, null);
                //声明一个临时队列，该队列会在使用完比后自动销毁
                //将队列绑定到交换机,参数3无意义此时
                channel.queueBind(channel.queueDeclare().getQueue(), RabbitMqQueue.FANOUT_MONEY_QUEUE, "");
                break ;

            case DIRECT:
                //向所有绑定了相应routing key的队列发送消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃
                //如果有多个consumer监听了相同的routing key  则他们都会受到消息
                channel.exchangeDeclare(RabbitMqQueue.DIRECT_MONEY_QUEUE, "direct", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                channel.queueBind(queueName, RabbitMqQueue.DIRECT_MONEY_QUEUE, "info"); //绑定一个routing key，可以绑定多个
                channel.queueBind(queueName, RabbitMqQueue.DIRECT_MONEY_QUEUE, "warning");
                break ;

            case TOPIC:
                //与direct模式有类似之处，都使用routing key作为路由
                //不同之处在于direct模式只能指定固定的字符串，而topic可以指定一个字符串模式

                channel.exchangeDeclare(RabbitMqQueue.TOPIC_MONEY_QUEUE, "topic", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                channel.queueBind(queueName, RabbitMqQueue.TOPIC_MONEY_QUEUE, "warning.#"); //监听两种模式 #匹配一个或多个单词 *匹配一个单词
                channel.queueBind(queueName, RabbitMqQueue.TOPIC_MONEY_QUEUE, "*.blue");
                break ;

            case HEADERS:
                //与topic和direct有一定相似之处，但不是通过routing key来路由消息
                //通过headers中词的匹配来进行路由

                channel.exchangeDeclare(RabbitMqQueue.HEADER_MONEY_QUEUE, "headers", false, true, null);
                queueName = channel.queueDeclare().getQueue();
                Map<String, Object> headers = new HashMap<String, Object>() {{
                    put("name", "test");
                    put("sex", "male");
                    put("x-match", "any");//all==匹配所有条件，any==匹配任意条件
                }};
                channel.queueBind(queueName, RabbitMqQueue.HEADER_MONEY_QUEUE, "", headers);

                break ;

        }

        // 在同一时间不要给一个worker一个以上的消息。
        // 不要将一个新的消息分发给worker知道它处理完了并且返回了前一个消息的通知标志（acknowledged）
        // 替代的，消息将会分发给下一个不忙的worker。
        channel.basicQos(1); //server push消息时的队列长度

        //用来缓存服务器推送过来的消息
        QueueingConsumer consumer = new QueueingConsumer(channel);

        //为channel声明一个consumer，服务器会推送消息
        //参数1:队列名称
        //参数2：是否发送ack包，不发送ack消息会持续在服务端保存，直到收到ack。  可以通过channel.basicAck手动回复ack
        //参数3：消费者
        channel.basicConsume(queueName, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("Received " + new String(delivery.getBody()));

            //回复ack包，如果不回复，消息不会在服务器删除
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            //channel.basicReject(); channel.basicNack(); //可以通过这两个函数拒绝消息，可以指定消息在服务器删除还是继续投递给其他消费者
        }



    }
}

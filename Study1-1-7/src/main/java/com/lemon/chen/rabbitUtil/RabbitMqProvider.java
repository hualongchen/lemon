package com.lemon.chen.rabbitUtil;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenhualong on 2017/8/7.
 */
public class RabbitMqProvider {


    public static void main(String[] args) throws Throwable{


        /**
         * 如果消息体有空格，尽量的全部去除，不然一条消息体读不完全
         */
        String message ="info def ghi jkl mno pqr stu vwx yz";

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
                //默认，向指定的队列发送消息，
                // 消息只会被一个consumer处理,多个消费者消息会轮训处理,
                // 消息发送时如果没有consumer，消息不会丢失

                //为消息通道绑定一个队列
                //队列的相关参数需要与第一次定义该队列时相同，否则会出错
                //参数1：队列名称
                //参数2：为true时server重启队列不会消失
                //参数3：队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
                //参数4：队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
                //参数5：建立队列时的其他参数
                channel.queueDeclare(RabbitMqQueue.DEFAULT_QUEUE, true, false, true, null);
                /**
                 * 开始发送信息
                 */

                //向server发布一条消息
                //参数1：exchange名字，若为空则使用默认的exchange
                //参数2：routing key
                //参数3：其他的属性
                //参数4：消息体
                //RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
                //任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
                channel.basicPublish("", RabbitMqQueue.DEFAULT_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()); //设置消息为持久化，服务器重启不会丢失
                System.out.println("发送default信息:" + message);
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
                /**
                 * 开始消费信息
                 */
                channel.exchangeDeclare(RabbitMqQueue.FANOUT_MONEY_QUEUE, "fanout", false, true, null);

                channel.basicPublish(RabbitMqQueue.FANOUT_MONEY_QUEUE, "", null, message.getBytes());

                System.out.println("发送fanout信息:" + message);

                break ;

            case DIRECT:
                //向所有绑定了相应routing key的队列发送消息
                //如果producer在发布消息时没有consumer在监听，消息将被丢弃
                //如果有多个consumer监听了相同的routing key  则他们都会受到消息
                channel.exchangeDeclare(RabbitMqQueue.DIRECT_MONEY_QUEUE, "direct", false, true, null);

                /**
                 * 设置路由
                 */
                String[] temp = StringUtils.splitString(message, " ");
                channel.basicPublish(RabbitMqQueue.DIRECT_MONEY_QUEUE, temp[0], null, temp[1].getBytes());
                System.out.println("发送direct信息: " + message);

                /**
                 * 开始消费信息
                 */
                break ;

            case TOPIC:
                //与direct模式有类似之处，都使用routing key作为路由
                //不同之处在于direct模式只能指定固定的字符串，而topic可以指定一个字符串模式
                /**
                 * 开始消费信息
                 */

                channel.exchangeDeclare(RabbitMqQueue.TOPIC_MONEY_QUEUE, "topic", false, true, null);

                String[] temp2 = StringUtils.splitString(message, " ");
                channel.basicPublish(RabbitMqQueue.TOPIC_MONEY_QUEUE, temp2[0], null, temp2[1].getBytes());
                System.out.println("发送topic信息: " + message);

                break ;

            case HEADERS:
                //与topic和direct有一定相似之处，但不是通过routing key来路由消息
                //通过headers中词的匹配来进行路由
                /**
                 * 开始消费信息
                 */

                channel.exchangeDeclare(RabbitMqQueue.HEADER_MONEY_QUEUE, "headers", false, true, null);

                String[] temp3 = StringUtils.splitString(message, " ");

                Map<String, Object> headers = new HashMap<String, Object>();
                headers.put("name", temp3[0]); //定义headers
                headers.put("sex", temp3[1]);
                AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder().headers(headers);

                channel.basicPublish(RabbitMqQueue.HEADER_MONEY_QUEUE, "", builder.build(), temp3[2].getBytes()); //根据headers路由到相应的consumer
                System.out.println("发送headers信息: " + message);
                break ;
        }

        /**
         * 第五步， 关闭信道，关闭连接
         */
        RabbitmqFactoryUtil.closeConnection(connection,channel);


    }
}

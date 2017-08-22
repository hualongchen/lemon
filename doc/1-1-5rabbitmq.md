# 1-1-4 rabbitmq学习教程

>   ***此教程是基于乌班图进行试验成功***

### 1. 0  安装 rabbit mq 的依赖

```
sudo apt-get install erlang-nox
```

### 2.0 安装 mq server

```
 sudo apt-get update
 sudo apt-get install rabbitmq-server
```


### 3.0 启动/关闭  mq
```
关闭q
ps aux|grep erl

sudo kill -9 xxxx
第二种关闭方式：

sudo rabbitmqctl stop

开启
sudo  rabbitmq-server start

查看状态

sudo rabbitmqctl status

守护形式启动mq,相当于后台运行

sudo rabbitmq-server - detached start

```


###  4.0 增加用户

```
sudo rabbitmqctl add_user lemon tongrong

增加读写权限

sudo rabbitmqctl set_permissions lemon ".*" ".*" ".*"
```

### 5.0 增加管理页面插件

```
sudo rabbitmq-plugins enable rabbitmq_management

过程处理：
因为rabbitmq3.3以后，默认账号guest/guest只能以localhost来访问。
所以需要增加其他的账户来执行
首先， 增加第四步的操作。
然后赋权
sudo rabbitmqctl set_user_tags lemon administrator
访问插件：
http://10.55.120.247:15672/
```

### 6.0 mq的日志文件和数据文件

```
日志地址是：  /var/log/rabbitmq
数据地址： /var/lib/rabbitmq/mnesia/rabbit
```

###  7. 0 mq相关概念简介

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/091356482496388.png "在这里输入图片标题")

```
Broker：简单来说就是消息队列服务器实体。
Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列。
Queue：消息队列载体，每个消息都会被投入到一个或多个队列。
Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来。
Routing Key：路由关键字，exchange根据这个关键字进行消息投递。
vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离。
producer：消息生产者，就是投递消息的程序。
consumer：消息消费者，就是接受消息的程序。
channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务。
```

### 8.0 rabbitmq使用流程

```
AMQP模型中，消息在producer中产生，发送到MQ的exchange上，exchange根据配置的路由方式发到相应的Queue上，Queue又将消息发送给consumer，消息从queue到consumer有push和pull两种方式。 消息队列的使用过程大概如下：

客户端连接到消息队列服务器，打开一个channel。
客户端声明一个exchange，并设置相关属性。
客户端声明一个queue，并设置相关属性。
客户端使用routing key，在exchange和queue之间建立好绑定关系。
客户端投递消息到exchange。
exchange接收到消息后，就根据消息的key和已经设置的binding，进行消息路由，将消息投递到一个或多个队列里。 exchange也有几个类型，完全根据key进行投递的叫做Direct交换机，例如，绑定时设置了routing key为”abc”，那么客户端提交的消息，只有设置了key为”abc”的才会投递到队列。
```
### 9.0 exchange 的属性简介
```
1. Direct exchange:(Empty string) and amq.direct.根据Binding指定的Routing Key，将符合Key的消息发送到Binding的Queue

2.Fanout exchange:amq.fanout.将同一个message发送到所有同该Exchange bingding的queue

3.Topic exchange: amq.topic;根据Binding指定的Routing Key，Exchange对key进行模式匹配后路由到相应的Queue，模式匹配时符号”#”匹配一个或多个词，符号”*”匹配正好一个词。

4.Headers exchange : amq.match (and amq.headers in RabbitMQ): 同direct exchange类似，不同之处是不再使用Routing Key路由，而是使用headers（message attributes）进行匹配路由到指定Queue。
```
![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/091413531249592.png "在这里输入图片标题")
### 10. 各个方式的代码demo
#### 10.1 消息提供方
```
**
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

```

#### 10.2 消息消费方

```

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

```








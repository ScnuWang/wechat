package net.imwork.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Jason.
 * Date 2017/7/28 16:46
 */
public class Comsumer {

    public static void main(String[] args) throws JMSException {
        //消息目的地
        String destinationurl = "tcp://localhost:61616";
        //队列的名称
        String TopicName = "demoTopicName";
        //创建连接工厂，通过连接工厂创建连接
        ConnectionFactory connectionFactory  = new ActiveMQConnectionFactory(destinationurl);
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //创建会话：
        /**
         * 1、通过会话创建队列对象
         * 2、通过会话创建生成者对象
         * 3、通过会话创建消息对象
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列对象，创建时设置队列名称
        Topic topic = session.createTopic(TopicName);//Topic继承自Destionation
        //根据队列对象创建消费者
        MessageConsumer consumer = session.createConsumer(topic);


        //接受消息
        connection.start();
        for (int i = 0; i < 100; i++) {
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println("接受到消息:"+message.getText());
        }
       //关闭连接
//        connection.close();
    }

}

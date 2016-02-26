package com.selonj.activemq;

import com.selonj.activemq.supports.ActiveMQ;
import com.selonj.activemq.supports.Consumer;
import com.selonj.activemq.supports.Producer;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by L.x on 16-2-26.
 */
public class ActiveMQTest {
    private ActiveMQ activeMQ = new ActiveMQ();
    private ActiveMQTopic event = new ActiveMQTopic("event");

    private Producer producer = new Producer(activeMQ, event);
    private Consumer consumer1 = new Consumer(activeMQ, event);
    private Consumer consumer2 = new Consumer(activeMQ, event);
    private Consumer other = new Consumer(activeMQ, new ActiveMQTopic("other"));

    @Before
    public void setUp() throws Exception {
        activeMQ.start();
        consumer1.start();
        consumer2.start();
        other.start();
        producer.start();
    }

    @Test
    public void notifyMessageToAllConsumersWhichTopicIsSame() throws Exception {
        producer.sendMessage("foo");

        consumer1.hasReceivedMessage("foo");
        consumer2.hasReceivedMessage("foo");
    }

    @Test
    public void cannotToBeNotifiedIfTopicNotTheSame() throws Exception {
        producer.sendMessage("foo");

        other.hasNoMessageReceived();
    }

    @Test
    public void notifyMessagesInOrder() throws Exception {
        int numberOfMessages = 100;

        for (int i = 0; i < numberOfMessages; i++) {
            producer.sendMessage("message-" + i);
        }

        for (int i = 0; i < numberOfMessages; i++) {
            consumer1.hasReceivedMessage("message-" + i);
            consumer2.hasReceivedMessage("message-" + i);
        }
    }

    @After
    public void tearDown() throws Exception {
        producer.close();
        consumer1.close();
        consumer2.close();
        other.close();
        activeMQ.close();
    }
}

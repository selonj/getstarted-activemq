package com.selonj.activemq.supports;

import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 16-2-26.
 */
public class Consumer {
    public static final int ONE_SECOND = 1000;
    private final ActiveMQ activeMQ;
    private Destination destination;
    private MessageConsumer consumer;

    public Consumer(ActiveMQ activeMQ, ActiveMQTopic destination) {
        this.activeMQ = activeMQ;
        this.destination = destination;
    }

    public void start() throws JMSException, URISyntaxException {
        Session session = activeMQ.createNewSession();
        consumer = session.createConsumer(destination);
    }

    public void hasReceivedMessage(String content) throws InterruptedException, JMSException {
        TextMessage message = (TextMessage) consumer.receive(ONE_SECOND);
        assertThat(message.getText(), equalTo(content));
    }

    public void hasNoMessageReceived() throws JMSException {
        TextMessage message = (TextMessage) consumer.receive(ONE_SECOND);
        assertThat(message, nullValue());
    }

    public void close() throws JMSException {
        if (consumer != null) {
            consumer.close();
        }
    }
}

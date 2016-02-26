package com.selonj.activemq.supports;

import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.net.URISyntaxException;

/**
 * Created by L.x on 16-2-26.
 */
public class Producer {

    private final ActiveMQ activeMQ;
    private MessageProducer producer;
    private Session session;
    private Destination destination;

    public Producer(ActiveMQ activeMQ, ActiveMQTopic destination) {
        this.activeMQ = activeMQ;
        this.destination = destination;
    }

    public void start() throws JMSException, URISyntaxException {
        session = activeMQ.createNewSession();

        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void sendMessage(String content) throws JMSException {
        TextMessage message = session.createTextMessage(content);
        producer.send(message);
    }

    public void close() throws JMSException {
        if (session != null) {
            session.close();
        }
    }
}

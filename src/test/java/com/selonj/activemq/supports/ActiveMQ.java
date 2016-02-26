package com.selonj.activemq.supports;

import org.apache.activemq.ActiveMQConnection;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import java.net.URISyntaxException;

/**
 * Created by L.x on 16-2-26.
 */
public class ActiveMQ {
    private Connection connection;

    public Session createNewSession() throws JMSException, URISyntaxException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void start() throws JMSException, URISyntaxException {
        connection = ActiveMQConnection.makeConnection("tcp://localhost:61616");
        connection.start();
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException ignored) {
            }
        }
    }
}

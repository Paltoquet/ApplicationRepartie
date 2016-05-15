package Client;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;


/**
 * Created by user on 14/05/2016.
 */
public class ThreadConsume extends Thread{



    ConnectionFactory f;
    Connection c;
    Session session;
    Queue q;
    MessageConsumer consumer;

    public ThreadConsume(String name, String url, String topic){
        ConnectionFactory f=new ActiveMQConnectionFactory(name,name,url);
        try {
            c = f.createConnection("user", "user");
            c.start();
            session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            q = session.createQueue(topic);
            consumer = session.createConsumer(q);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            TextMessage t;
            while(true) {
                t=(TextMessage)consumer.receive();
                System.out.println("Message reçu");
                System.out.println(t.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package ServerUniversel;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;


/**
 * Created by user on 14/05/2016.
 */
public class ThreadProducer extends Thread{

    ConnectionFactory f;
    Connection c;
    Session session;
    Queue q;
    MessageProducer producer;

    public ThreadProducer(String name,String url,String topic){
        ConnectionFactory f=new ActiveMQConnectionFactory(name,name,url);
        try {
            c = f.createConnection("user", "user");
            c.start();
            session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
            q = session.createQueue(topic);
            producer=session.createProducer(q);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            System.out.println("Entrer des messages pour la queue");
            String message="";
            Scanner sc = new Scanner(System.in);
            while(!(message=sc.nextLine()).equals("quit")) {
                System.out.println();
                TextMessage m = session.createTextMessage();
                m.setText(message);
                producer.send(m);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * HelloWorld for JMS.<br>
 * <p>The source is taken from <a href="https://github.com/tabish121/ActiveMQ-HowTo"> GitHub</a> and I refered to the
 * book online on SafariBooks <a href="https://learning.oreilly.com/library/view/instant-apache-activemq/9781782169413/">
 * Instant Apache ActiveMQ Application Development How-To </a> (right click "Open in a New Tab").</p>
 * <p>This class sends a message "We sent a message" to tcp://localhost:61616 and then retrieve it.</p>
 * Note: ActiveMQ should be active beforehand. Otherwise, an error is raised stating that you cannot connect to the port
 * 61616.
 */
public class SimpleJMS {

    private final String connectionUri = "tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public void before() throws Exception {
        connectionFactory = new ActiveMQConnectionFactory(connectionUri);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("MyQueue");
    }

    public void after() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public void run() throws Exception {

        MessageProducer producer = session.createProducer(destination);
        try {
            TextMessage message = session.createTextMessage();
            message.setText("We sent a Message!");
            producer.send(message);
        } finally {
            producer.close();
        }

        MessageConsumer consumer = session.createConsumer(destination);
        try {
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        SimpleJMS example = new SimpleJMS();
        System.out.print("\n\n\n");
        System.out.println("Starting SimpleJMS example now...");
        try {
            example.before();
            example.run();
            example.after();
        } catch (Exception e) {
            System.out.println("Caught an exception during the example: " + e.getMessage());
        }
        System.out.println("Finished running the SimpleJMS example.");
        System.out.print("\n\n\n");
    }
}

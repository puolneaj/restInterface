package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

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
        destination = session.createQueue("TRADEQ.Work");
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
            message.setText("<input xmlns=\"http://www.visual-rules.com/vrpath/BPRequest/MainRequest/\" xmlns:ns1=\"http://www.visual-rules.com/vrpath/BPRequest/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <request> <docId>6</docId> <ProductCategory>Share - Bearer</ProductCategory> <Client>Johm Smith</Client> <Domicile>Switzerland</Domicile> <TradePlace>XETRA, Deutschland</TradePlace> </request> </input>");
            producer.send(message);
        } finally {
            producer.close();
        }

        MessageConsumer consumer = session.createConsumer(destination);
        try {
            TextMessage message = (TextMessage) consumer.receive();
            String text=message.getText();
            System.out.println(text);
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        SimpleJMS example = new SimpleJMS();
        System.out.print("\n\n\n");
        System.out.println("Starting Actico SimpleJMS example now...\n");
        try {
            example.before();
            example.run();
            example.after();
        } catch (Exception e) {
            System.out.println("Caught an exception during the example: " + e.getMessage());
        }
        System.out.println("\nFinished running the Actico SimpleJMS example.");
        System.out.print("\n\n\n");
    }
}

package Middleware;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * handle the message between ActiveMQ and Actico servers.
 *
 */
public class JMS {

    private final String connectionUri = "tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    /**
     * Establish connection to ActiveMQ port (i.e. port 61616).
     *
     * Create and start connection to the URI tcp://localhost:61616. Create a queue Trade.Work.
     * @throws Exception when any generic issue comes up.
     */
    public void establishConnection() throws Exception {
        connectionFactory = new ActiveMQConnectionFactory(connectionUri);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("TRADEQ.Work");
    }

    /**
     * Close connection to ActiveMQ port (i.e. port 61616).
     * @throws Exception when any generic issue comes up.
     */
    public void closeConnection() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Produce and consume a XML message.
     *
     * Create a producer attached to the queue TRADEQ.Work, then a message with text in the body and send it. Close the procucer.<br>
     * Create a consumer and get the message from the queue TRADEQ.Work. Print the message and close the consumer.
     *
     * @throws Exception when any generic issue comes up.
     */
    public void produceAndConsumeMessage() throws Exception {

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

    /**
     * Produce message.
     *
     * Produce message and insert text in its body. Send the message. Close the producer.
     * @param XML string following XML standard
     * @throws Exception when any generic issue comes up.
     */
    public void produceMessage(String XML) throws Exception {
        TextMessage message;
        MessageProducer producer = session.createProducer(destination);
        try {
            message = session.createTextMessage();
            message.setText(XML);
            producer.send(message);
        } finally {
            producer.close();
        }
    }

    /**
     * Consume message.
     *
     * Create a consumer for a specific queue. Retrieve the message body and return it. Also print it for debugging purpose.
     * @return body of the message in text format.
     * @throws Exception
     */
    public String consumeMessage() throws Exception{
        String text="";
        MessageConsumer consumer = session.createConsumer(destination);
        try {
            TextMessage message = (TextMessage) consumer.receive();
            text=message.getText();
            System.out.println(text);
        } finally {
            consumer.close();
        }
        return text;
    }

    /**
     * Generic process handling a message.
     * @deprecated
     * @param args is obsolete
     */
    public static void main(String[] args) {
        JMS example = new JMS();
        System.out.print("\n");
        System.out.println("Starting Actico JMS example now...\n");
        try {
            example.establishConnection();
            example.produceAndConsumeMessage();
            example.closeConnection();
        } catch (Exception e) {
            System.out.println("Caught an exception during the example: " + e.getMessage());
        }
        System.out.println("\nFinished running the Actico JMS example.");
        System.out.print("\n");
    }
}

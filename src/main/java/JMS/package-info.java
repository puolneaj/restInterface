/**
 * Consume a message.
 *
 * <p>This package aims to retrieve the message out (in XML format or JSON format) and feed the ACTICO package (which does
 * a REST call).Once the message broker is in place (ActiveMQ, OracleAQ, IBM MQ), this package is consuming the message that is
 * in the queue.</p>
 * <p>At the moment, the development of this part is in progress. The class {@link JMS.SimpleJMS} doesn't consume message.
 * It publishes a dummy message to a specific port (i.e., 61616) and consumes it. Take it as a HelloWorld for JMS.</p>
 * <p>The source is taken from <a href="https://github.com/tabish121/ActiveMQ-HowTo"> GitHub</a> and I refered to the
 * book online on SafariBooks <a href="https://learning.oreilly.com/library/view/instant-apache-activemq/9781782169413/">
 * Instant Apache ActiveMQ Application Development How-To </a> (right click "Open link in a New Tab").</p>
 *
 * <p>Whoever it taking over the project should implement the JMS to connect with AMI.</p>
 */
package JMS;
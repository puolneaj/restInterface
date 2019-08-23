package ACTICO;
import Model.JMS;

import javax.xml.transform.TransformerConfigurationException;
import Model.*;

import com.actico.restInterface.RequestDaoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;

import jlibs.xml.xsd.XSParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b> Backbone of the project.</b>
 */
public class ActicoInterface {
    /**
     * <b>Main Method</b><br>
     * Trigger Actico Server (1) with a message coming from Avaloq AMI or (2) with an hardcoded request.
     * Print the output generated.
     * @param args argument not required
     */
    public static void main(String args[]) {
        final Logger logger;
        logger = LogManager.getLogger(ActicoInterface.class);
        /*Hardcoded request*/
        String XML="<input xmlns=\"http://www.visual-rules.com/vrpath/BPRequest/MainRequest/\" xmlns:ns1=\"http://www.visual-rules.com/vrpath/BPRequest/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "<request>\n" +
                "<docId>?</docId>\n" +
                "<client>?</client>\n" +
                "<tradePlace>?</tradePlace>\n" +
                "<productCategory>?</productCategory>\n" +
                "<domicile>?</domicile>\n" +
                "</request>\n" +
                "</input>";
        String JMSrequest="";
        JMS requestExample = new JMS();
        /*Consuming Message on ActiveMQ*/
        logger.info("Starting Actico JMS requestExample now...");
        try {
            requestExample.establishInConnection();
//            requestExample.produceMessage(XML);
            JMSrequest=requestExample.consumeMessage();
//            requestExample.closeConnection();
        } catch (Exception e) {
            logger.error("Caught an exception with requestExample: " + e.getMessage());
        }
        logger.info("Finished running the Actico JMS requestExample.");

        /*Trigger Actico Execution Server with JMS request*/
        HttpURLConnection con = ServerManager.launchServer();
        XMLFeature.sendXMLRequest(con, JMSrequest);
        StringBuilder response = XMLFeature.readXMLResponse(con);
        logger.debug("Response from ACTICO : "+ response);

        String inadequateOutputNode="<output .*\">";
        String adequateOutputNode="<output>";
        Pattern pattern=Pattern.compile(inadequateOutputNode);
        Matcher matcher=pattern.matcher(response.toString());
        String responseToString=matcher.replaceAll(adequateOutputNode);
        logger.debug("Proceed to Regex on the response XML");
        logger.info("response with new regex pattern: " +responseToString);

        /*Produce Message on ActiveMQ*/
        JMS responseExample = new JMS();
        logger.info("Starting Actico JMS responseExample now...");
        try {
            responseExample.establishOutConnection();
            responseExample.produceMessage(responseToString);
//            responseExample.produceMessage(response.toString());
            responseExample.closeConnection();
        } catch (Exception e) {
            logger.error("Caught an exception with responseExample: " + e.getMessage());
        }
        logger.info("Finished running the Actico JMS responseExample.");
    }


    /**
     * Trigger Actico server - Backbone of the project.
     *
     *<p>Set of nested methods used in this method</p>
     * <ul>
     *     <li>{@link XSDFeature#loadXSDDocument(String) loadXSDDocument}</li>
     *     <li>{@link XMLFeature#buildXMLInstance(XSModel, String, String) buildXMLInstance}</li>
     *     <li>{@link XMLFeature#modifyXMLRequest(StringWriter, Input) modifyXMLRequest }</li>
     *     <li>{@link XMLFeature#getXMLDocument(Document) getXMLDocument}</li>
     *     <li>{@link XMLFeature#sendXMLRequest(HttpURLConnection, String) sendXMLRequest}</li>
     *     <li>{@link XMLFeature#readXMLResponse(HttpURLConnection) readXMLResponse}</li>
     *     <li>{@link XMLFeature#castXMLintoOutput(StringBuilder, String) castXMLintoOutput}</li>
     * </ul>
     *
     * <p>Called in {@link #main(String[]) ActicoInterface#main} method for troubleshoot purpose.<br>
     *  Called in REST interface Application within {@link RequestDaoService#acticoResponse(Request) RequestDaoService#acticoResponse} method.</p>
     *
     * @param input the model on which the XML file request is based upon
     * @return the output model on which the XML file response is based upon
     * @throws TransformerConfigurationException
     * @deprecated as RestInterface.class is deprecated.
     */
    //TODO check if there is no better way to chose the parameters of the method -> remove input
    public static Output getResponse(Input input) throws TransformerConfigurationException {
        final String filename = "MainRequest.xsd";              /* XSD file governing the XML structure  */
        final Document doc = XSDFeature.loadXSDDocument(filename);

        // Parse the file into an XSModel object
        XSModel xsModel = new XSParser().parse(filename);

        StringWriter stringWriter = XMLFeature.buildXMLInstance(xsModel, XSDFeature.getTargetnamespace(doc), "input");

        System.out.println("\n - - - - - Modify the XML attributes - - - - - -  \n");
        Document document = XMLFeature.modifyXMLRequest(stringWriter, input);
        String XMLRequest = XMLFeature.getXMLDocument(document);
        System.out.println("- - - - - XML REQUEST IS: \n\n" + XMLRequest);
        HttpURLConnection con = ServerManager.launchServer();
        XMLFeature.sendXMLRequest(con, XMLRequest);
        StringBuilder response = XMLFeature.readXMLResponse(con);
        //print the whole file XML in URL address
        System.out.println("\n - - - - - -  RECEIVE XML FROM ACTICO EXE SERVER - - - - \n"+response);
        return XMLFeature.castXMLintoOutput(response, "output");
    }

}

package ACTICO;
import JMS.SimpleJMS;

import javax.xml.transform.TransformerConfigurationException;
import Model.*;

import com.actico.restInterface.RequestDaoService;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;

import jlibs.xml.xsd.XSParser;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * <b> Backbone of the project.</b>
 */
public class ActicoInterface {
    /**
     * <b>Main Method</b><br>
     * Trigger Actico Server and {@link #getResponse(Input) getResponse} from an hardcoded request.
     *
     * Print the output generated
     * @param args argument not required
     * @throws TransformerConfigurationException
     */
    public static void main(String args[]) throws TransformerConfigurationException {
        String XML="<input xmlns=\"http://www.visual-rules.com/vrpath/BPRequest/MainRequest/\" xmlns:ns1=\"http://www.visual-rules.com/vrpath/BPRequest/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <request> <docId>6</docId> <ProductCategory>Share - Bearer</ProductCategory> <Client>Johm Smith</Client> <Domicile>Switzerland</Domicile> <TradePlace>XETRA, Deutschland</TradePlace> </request> </input>";
        String JMSrequest="";
        SimpleJMS requestExample = new SimpleJMS();
        System.out.print("\n\n\n");
        System.out.println("Starting Actico SimpleJMS requestExample now...\n");
        try {
            requestExample.before();
            requestExample.SrunProducer(XML);
            JMSrequest=requestExample.SrunConsumer();
            requestExample.after();
        } catch (Exception e) {
            System.out.println("Caught an exception during the requestExample: " + e.getMessage());
        }
        System.out.println("\nFinished running the Actico SimpleJMS requestExample.");
        System.out.print("\n\n\n");

        HttpURLConnection con = ServerManager.launchServer();
        XMLFeature.sendXMLRequest(con, JMSrequest);
        StringBuilder response = XMLFeature.readXMLResponse(con);
        System.out.println("\n - - - - - -  RECEIVE XML FROM ACTICO EXE SERVER - - - - \n"+response);


        // ------- CALL JMS WITH RESPONSE FROM ACTICO SERVER -----------
        SimpleJMS responseExample = new SimpleJMS();
        System.out.print("\n\n\n");
        System.out.println("Starting Actico SimpleJMS responseExample now...\n");
        try {
            responseExample.before();
            responseExample.SrunProducer(response.toString());
            responseExample.after();
        } catch (Exception e) {
            System.out.println("Caught an exception during the responseExample: " + e.getMessage());
        }
        System.out.println("\nFinished running the Actico SimpleJMS responseExample.");
        System.out.print("\n\n\n");

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

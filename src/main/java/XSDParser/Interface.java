package XSDParser;

import javax.xml.transform.TransformerConfigurationException;
import Model.*;

import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;

import jlibs.xml.xsd.XSParser;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * main class which uses the functionalities of XML and XSD
 */
public class Interface {
    /**
     * trigger Actico Server and get a response from an hardcoded request
     * print the output generated
     * @param args
     * @throws TransformerConfigurationException
     */
    public static void main(String args[]) throws TransformerConfigurationException {

        Request request = new Request();
        Input input = new Input();
        Output output = new Output();

        request.setCode("VR32");
        request.setMobile_priv("00336758697");
        request.setName("Durand");
        request.setTel_priv("00297616881");
        request.setObj_id("100");

        input.setRequest(request);

        System.out.println(getResponse(input).toString());
    }

    /**
     * main method which triggers Actico server
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

        StringWriter stringWriter = XMLFeature.getXML(xsModel, XSDFeature.getTargetnamespace(doc), "input");

        System.out.println("\n - - - - - Modify the XML attributes - - - - - -  \n");
        Document document = XMLFeature.modifyXMLRequest(stringWriter, input);
        String XMLRequest = XMLFeature.getXmlDocument(document);
        System.out.println("- - - - - XML REQUEST IS: \n\n" + XMLRequest);
        HttpURLConnection con = serverManager.launchServer();
        XMLFeature.sendXML(con, XMLRequest);
        StringBuilder response = XMLFeature.readXML(con);
        //print the whole file XML in URL address
        System.out.println("\n - - - - - -  RECEIVE XML FROM ACTICO EXE SERVER - - - - \n"+response);
        return XMLFeature.getXMLResponse(response, "output");
    }

}

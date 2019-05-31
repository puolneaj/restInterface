package XSDParser;

import javax.xml.transform.TransformerConfigurationException;

import Model.*;

import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;

import jlibs.xml.xsd.XSParser;

import java.io.*;
import java.net.HttpURLConnection;


public class XsdToXml {
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
        output=getBackRequest(input);
        System.out.println(output.toString());
    }
    //TODO turn StringBuffer into Output
    public static Output getBackRequest(Input input) throws TransformerConfigurationException {
        final String filename = "MainRequest.xsd";
        final Document doc = XSDParser.loadXsdDocument(filename);

        String targetNamespace = XSDParser.getTargetnamespace(doc);

        // Parse the file into an XSModel object
        XSModel xsModel = new XSParser().parse(filename);

        StringWriter stringWriter = XMLFunctionalities.getXML(xsModel, targetNamespace);

        System.out.println("\n - - - - - Modify the XML attributes - - - - - -  \n");
        Document document = XMLFunctionalities.modifyXMLRequest(stringWriter, input);
        String XMLRequest = XMLFunctionalities.getXmlDocument(document);
        System.out.println("- - - - - XML REQUEST IS: \n\n" + XMLRequest);
        HttpURLConnection con = serverManager.launchServer();
        XMLFunctionalities.sendXML(con, XMLRequest);
        StringBuffer response = XMLFunctionalities.readXXML(con);

        //print the whole file XML in URL address
        System.out.println("\n - - - - - -  RECEIVE XML FROM ACTICO EXE SERVER - - - - \n");
        System.out.println("Receive the XML from Actico Execution Server: \n" + response);

        return XMLFunctionalities.getNodeXMLResponse(response);
    }

}

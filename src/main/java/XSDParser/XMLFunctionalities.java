package XSDParser;

import Model.Input;
import Model.Output;
import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;

public class XMLFunctionalities {

    public static StringBuffer readXXML(HttpURLConnection con) {

        System.out.println("\n------READ THE WHOLE DOCUMENT USING BUFFEREDREADER-------\n");
        //set the variables
        String inputLine = "";
        StringBuffer response = new StringBuffer();
        try {
            //BufferedReader simplifies reading text from a character input stream
            //opening the bufferedReader
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            //read line by line the document
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            //close the buffered reader
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getXmlDocument(Document document) {
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        String string = lsSerializer.writeToString(document);
        return string;
    }

    public static Document modifyXMLRequest(StringWriter stringWriter, Input input) {
        //parse document with respect to XML nodes
        Document document = null;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(stringWriter.toString())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("ns:Request");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            Node nodeName = elem.getElementsByTagName("ns1:name").item(0).getFirstChild();
            Node nodeId = elem.getElementsByTagName("ns1:obj_id").item(0).getFirstChild();
            Node nodeCode = elem.getElementsByTagName("ns1:code").item(0).getFirstChild();
            Node nodeTel = elem.getElementsByTagName("ns1:tel_priv").item(0).getFirstChild();
            Node nodeMobile = elem.getElementsByTagName("ns1:mobile_priv").item(0).getFirstChild();

            nodeName.setNodeValue(input.getRequest().getName());
            nodeId.setNodeValue(input.getRequest().getObj_id());
            nodeCode.setNodeValue(input.getRequest().getCode());
            nodeTel.setNodeValue(input.getRequest().getTel_priv());
            nodeMobile.setNodeValue(input.getRequest().getMobile_priv());

        }
        return document;

    }

    public static Output getNodeXMLResponse(StringBuffer stringWriter) {
        //parse document with respect to XML nodes
        Document document = null;
        Output output = new Output();

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(stringWriter.toString())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("output");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            Node nodeIDKey = elem.getElementsByTagName("obj_id_key").item(0).getFirstChild();
            Node nodeStatus = elem.getElementsByTagName("status").item(0).getFirstChild();

            output.setObj_id_key(nodeIDKey.getNodeValue());
            output.setStatus(nodeStatus.getNodeValue());
        }
        return output;

    }

    public static void sendXML(HttpURLConnection con, String XMLRequest) {
        int responseCode = 0;
        try {
            con.setDoOutput(true);
            OutputStream outStream = con.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
            outStreamWriter.write(XMLRequest);

            outStreamWriter.flush();
            outStreamWriter.close();
            outStream.close();
            System.out.println("\n - - - - - - SEND XML- - - - - \n");
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //get the response code of the HTTP protocol

        System.out.println("Response Code : " + responseCode);
    }

    public static StringWriter getXML(XSModel xsModel, String targetNamespace) throws TransformerConfigurationException {
        // Define defaults for the XML generation
        XSInstance instance = new XSInstance();
        instance.minimumElementsGenerated = 1;
        instance.maximumElementsGenerated = 1;
        instance.generateDefaultAttributes = true;
        instance.generateOptionalAttributes = true;
        instance.maximumRecursionDepth = 0;
        instance.generateAllChoices = true;
        instance.showContentModel = true;
        instance.generateOptionalElements = true;
        instance.generateFixedAttributes = true;

// Build the sample xml doc
// Replace first param to XMLDoc with a file input stream to write to file
        QName rootElement = new QName(targetNamespace, "input");
        StringWriter stringWriter = new StringWriter();
        XMLDocument sampleXml = new XMLDocument(new StreamResult(stringWriter), true, 4, null);
        instance.generate(xsModel, rootElement, sampleXml);

        System.out.println(stringWriter.toString());
        return stringWriter;
    }
}

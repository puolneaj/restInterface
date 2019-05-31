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

/**
 * repository of methods related to XML
 */
public class XMLFunctionalities {

    /**
     * read the XML using Bufferedreader
     * @param con connection Http from which the stream is read
     * @return response, a string value of what the contains the Http connection
     */
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

    /**
     * Allow to manipulate a document
     * @param document
     * @return a string with the value of the document
     */
    public static String getXmlDocument(Document document) {
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        String string = lsSerializer.writeToString(document);
        return string;
    }

    /**
     * change a string into a XML document
     * parse the XML document
     * set the XML nodes following the object input
     * @param stringWriter string corresponding to the future XML file
     * @param input object model used to set the attributes value of XML file
     * @return
     */
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

    /**
     * take out attributes values of XML node
     * @param stringWriter string corresponding to the XML file
     * @return output object with the XML attributes values in the object fields
     */
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

    /**
     * send a given XML file on a given connection Http
     * access the response code
     * @param con Http Connection
     * @param XMLRequest XML file with the request
     */
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

    /**
     * get an XML Document
     * turn the XML Document into a string
     * @param xsModel structure of the XML (XML Schema Definition)
     * @param targetNamespace --no idea
     * @return return a string which represents the XML Document
     * @throws TransformerConfigurationException
     */
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

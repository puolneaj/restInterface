package ACTICO;

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
 * Repository of methods related to <b>XML files</b>.
 */
public class XMLFeature {

    /**
     * <b>Generic Method</b><br>
     * Read the XML using Bufferedreader.
     *
     * <b>BufferedReader</b> simplifies reading text from a character input stream.<br>
     * The BufferReader insert the whole content of the connection inside a string of type <B>StringBuilder</B>.<br>
     * The reading process stops at the end of the file.
     * <p>Note: The connection HTTP in the parameter is already open.</p>
     * @param con Connection HTTP from which the stream is read.
     * @return string value of what the contains the Http connection.
     */
    public static StringBuilder readXMLResponse(HttpURLConnection con) {
        //System.out.println("\n------READ THE WHOLE DOCUMENT USING BUFFEREDREADER-------\n");
        String inputLine = "";
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * <b>Generic Method</b><br>
     * Turn a document into a String.
     *
     * The document represents the XML file which is sent to ACTICO server.<br>
     * The string is used by the <b>outStreamWriter</b> (from method {@link #sendXMLRequest(HttpURLConnection, String) sendXMLRequest}) to send out the information to ACTICO server
     *
     * <p><b>DOMImplementationLS</b> is an extended DOMImplementation interface that provides the factory methods for creating the objects required for loading and saving.</p>
     *
     * <p>LSSerializer is provides an API for serializing (writing) a DOM document out into XML.<br>
     * The XML data is written to a string or an output stream.</p>
     *
     * <p>Method {@link #modifyXMLRequest(StringWriter, Input) modifyXMLRequest} precedes this method.<br>
     * ethod {@link #sendXMLRequest(HttpURLConnection, String) sendXMLRequest} follows this method.</p>
     *
     * @param document
     * @return String with the content of an XML file
     */
    public static String getXMLDocument(Document document) {
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        return lsSerializer.writeToString(document);
    }

    /**
     * <b>Generic Method</b><br>
     * Parse document with respect to XML nodes.
     *
     * This method is nested in {@link #modifyXMLRequest(StringWriter, Input) modifyXMLRequest}.
     *
     * <p>DocumentBuilderFactory defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents.</p>
     *
     * @param stringWriter string with the content of the XML
     * @return document using document builder
     */
    public static Document buildDocumentFromString(StringWriter stringWriter) {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(stringWriter.toString())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * <b>Specific Method</b><br>
     * Set the XML nodes following the structure object {@link Output}.
     *
     * <ul>
     *     <li>Use the method {@link #buildDocumentFromString(StringWriter) buildDocumentFromString} to create a document from stringWriter.</li>
     *     <li>Point the XML nodes of the XML Request</li>
     *     <li>Set the values inside XML nodes of XML Request</li>
     * </ul>
     *
     * <p>Method {@link #buildXMLInstance(XSModel, String, String) buildXMLInstance} precedes this method.<br>
     *  Method {@link #getXMLDocument(Document) getXMLDocument} follows this method.</p>
     *
     * @param stringWriter XML file of type String
     * @param input        object model used to set the attributes value of XML file
     * @return             document with XML attributes values corresponding to the request
     */
    public static Document modifyXMLRequest(StringWriter stringWriter, Input input) {

        Document document = XMLFeature.buildDocumentFromString(stringWriter);

        NodeList nodeList = document.getElementsByTagName("ns:Request");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            Node nodeName = elem.getElementsByTagName("ns1:name").item(0).getFirstChild();
            Node nodeId = elem.getElementsByTagName("ns1:docId").item(0).getFirstChild();
            Node nodeCode = elem.getElementsByTagName("ns1:code").item(0).getFirstChild();
            Node nodeTel = elem.getElementsByTagName("ns1:telPriv").item(0).getFirstChild();
            Node nodeMobile = elem.getElementsByTagName("ns1:mobilePriv").item(0).getFirstChild();

            nodeName.setNodeValue(input.getRequest().getName());
            nodeId.setNodeValue(input.getRequest().getDocId());
            nodeCode.setNodeValue(input.getRequest().getCode());
            nodeTel.setNodeValue(input.getRequest().getTelPriv());
            nodeMobile.setNodeValue(input.getRequest().getMobilePriv());
        }
        return document;
    }

    /**
     * <b>Specific Method</b><br>
     * Take out attributes values of XML node.
     *
     * <ul>
     *     <li>Declare and initialize an instance of type {@link Output}</li>
     *     <li>Assign the outcoming of {@link #buildDocumentFromBuffer(StringBuilder) buildDocumentFromBuffer} to Document </li>
     *     <li>Retrieve the node attributes from the Document</li>
     *     <li>Set the {@link Output} attributes with the nodes attributes</li>
     * </ul>
     *
     * <p>Method {@link #readXMLResponse(HttpURLConnection) readXMLResponse} is preceding this method.<br>
     * This method is the last method of {@link ActicoInterface#getResponse(Input) ActicoInterface#getResponse}.<br>
     * Method {@link #buildDocumentFromBuffer(StringBuilder) buildDocumentFromBuffer} is nested in this method.</p>
     *
     * @param stringBuilder string corresponding to the XML file.
     * @param nodeName
     * @return output object with the XML attributes values in the object fields.
     */
    public static Output castXMLintoOutput(StringBuilder stringBuilder, String nodeName) {

        Output output = new Output();

        //parse document with respect to XML nodes
        Document document = buildDocumentFromBuffer(stringBuilder);

        NodeList nodeList = document.getElementsByTagName(nodeName);    /* nodeName = 'output' */

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            Node nodeIDKey = elem.getElementsByTagName("key").item(0).getFirstChild();
            Node nodeStatus = elem.getElementsByTagName("status").item(0).getFirstChild();

            output.setKey(nodeIDKey.getNodeValue());
            output.setStatus(nodeStatus.getNodeValue());
        }
        return output;

    }

    /**
     * <b>Generic Method</b><br>
     * Parse document with respect to XML nodes.
     *
     * <ul>
     *     <li>Declare and initialize a Document</li>
     *     <li>Parse and insert String in the Document using DocumentBuilder from DocumentBuilderFactory </li>
     * </ul>
     *
     * <p>This method is nested inside {@link #castXMLintoOutput(StringBuilder, String) castXMLintoOutput}.</p>
     *
     * @param stringBuilder string with the content of the XML.
     * @return document using DocumentBuilder from DocumentBuilderFactory.
     */
    public static Document buildDocumentFromBuffer(StringBuilder stringBuilder) {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(stringBuilder.toString())));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * <b>Specific Method</b><br>
     * Send a XML file on a connection HTTP.
     *
     * <ul>
     *     <li>Create an output stream on the connection and open an OutputStreamWriter on it.</li>
     *     <li>Write a string to the stream</li>
     *     <li>Get Response Code - here 201 if HTTP POST is successful</li>
     * </ul>
     * access the response code
     *
     * <p>Method {@link #getXMLDocument(Document) getXMLDocument} precedes this method.<br>
     * Method {@link #readXMLResponse(HttpURLConnection)} readXMLResponse} follows this method.</p>
     *
     * <p>Note: The HTTP Connection is already open.</p>
     *
     * @param con        Http Connection
     * @param XMLRequest XML file with the request of type String.
     */
    public static void sendXMLRequest(HttpURLConnection con, String XMLRequest) {
        int responseCode = 0;
        try {
            con.setDoOutput(true);                  /* Send the request body itself, specific to POST and PUT requests */
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
     * <b>Generic Method</b><br>
     * Get an XML Document.
     *
     * The root element depends on the two parameters, the target namespace and the name of the node.
     *
     * <ul>
     *     <li>Declare and instanciate an XSInstance</li>
     *     <li>Set the parameters of the XSInstance instance</li>
     *     <li>Declare and instanciate a String of type StringWriter</li>
     *     <li>Declare and assign a XML Document</li>
     *     <li>Generate an XML instance with default values in the attributes</li>
     *     <li>Funnel XML in String of type stringWriter</li>
     * </ul>
     *
     * <p>Method {@link XSDFeature#loadXSDDocument(String) loadXSDDocument} preceded this method.<br>
     * Method {@link #modifyXMLRequest(StringWriter, Input) modifyXMLRequest} following this method.</p>
     *
     *
     * @param xsModel         structure of the XML (XML Schema Definition)
     * @param targetNamespace XML Namespace is a mechanism to avoid name conflicts by differentiating elements
     *                        or attributes within an XML document that may have identical names,
     *                        but different definitions.
     * @param nodeName        name of the node from which the information is taken in the method.
     * @return return a string which represents the XML Document
     * @throws TransformerConfigurationException if a fatal error of configuration appears
     */
    public static StringWriter buildXMLInstance(XSModel xsModel, String targetNamespace, String nodeName) throws TransformerConfigurationException {
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
        QName rootElement = new QName(targetNamespace, nodeName);
        StringWriter stringWriter = new StringWriter();
        XMLDocument sampleXml = new XMLDocument(new StreamResult(stringWriter), true, 4, null);
        //instance.generate feeds the xml into stringwriter
        instance.generate(xsModel, rootElement, sampleXml);

        System.out.println("XMLFeature.buildXMLInstance: \n" + stringWriter.toString());

        return stringWriter;
    }
}

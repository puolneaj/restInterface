package XSDParser;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import Model.*;

import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class XsdToXml {
    public static void main(String args[]) throws TransformerConfigurationException {
        final String filename = "MainRequest.xsd";
        final Document doc = loadXsdDocument(filename);

        Request request = new Request();
        Input input = new Input();
        Output output = new Output();

        request.setCode("VR32");
        request.setMobile_priv("00336758697");
        request.setName("Durand");
        request.setTel_priv("00297616881");
        request.setObj_id("100");

        input.setRequest(request);

// Find the docs root element and use it to find the targetNamespace
        final Element rootElem = doc.getDocumentElement();
        String targetNamespace = null;
        if (rootElem != null && rootElem.getNodeName().equals("schema")) {
            targetNamespace = rootElem.getAttribute("targetNamespace");
        }

// Parse the file into an XSModel object
        XSModel xsModel = new XSParser().parse(filename);

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

        System.out.println("\n - - - - - Modify the XML attributes - - - - - -  \n");

        //parse document with respect to XML nodes
        Document document = null;
        Document newXmlDocument = null;

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(stringWriter.toString())));
            newXmlDocument = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("ns:Request");


        Element root = newXmlDocument.createElement("ns:Request");
        newXmlDocument.appendChild(root);

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
        String XMLRequest = getXmlDocument(document);

        //url is fixed and stated
        //TODO Get all configuration data from .properties file
        ExecutionServer execution = new ExecutionServer();
        execution.setUsername("DEFAULT\\Admin");
        execution.setPassword("Admin");
        execution.setUserpassword();

        URI uri = new URI();
        uri.setRuleService("BPRequest");
        uri.setRule("BPRequest/MainRequest");
        uri.setVersion("0.0.1-SNAPSHOT");

        //open the connection
        HttpURLConnection con;
        try {
            URL obj = new URL(uri.toString());
            con = (HttpURLConnection) obj.openConnection();

            //HTTP POST
            con.setRequestMethod("POST");
            // Basic authentication
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String encodedAuthorization = enc.encode(execution.getUserpassword().getBytes());
            con.setRequestProperty("Authorization", "Basic " +
                    encodedAuthorization);
            //set the body of the request
            con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
            con.setRequestProperty("Expect", "100-continue");
            //send the XML
            con.setDoOutput(true);
            OutputStream outStream = con.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
            outStreamWriter.write(input.toString());
            outStreamWriter.flush();
            outStreamWriter.close();
            outStream.close();

            //get the response code of the HTTP protocol
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            System.out.println("\n------READ THE WHOLE DOCUMENT USING BUFFEREDREADER-------\n");
            //BufferedReader simplifies reading text from a character input stream
            //opening the bufferedReader
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            //set the variables
            String inputLine = "";
            StringBuffer response = new StringBuffer();
            //read line by line the document
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            //close the buffered reader
            in.close();

            //read the whole file XML in URL address
            System.out.println("Send the XML to Actico Execution Server: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Document loadXsdDocument(String inputName) {
        final String filename = inputName;

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        Document doc = null;

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final File inputFile = new File(filename);
            doc = builder.parse(inputFile);
        } catch (final Exception e) {
            e.printStackTrace();
            // throw new ContentLoadException(msg);
        }

        return doc;
    }

    public static String getXmlDocument(Document document) {
        DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        String string = lsSerializer.writeToString(document);
        return string;
    }
}

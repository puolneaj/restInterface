package XSDParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Gather the functionalities of XSD file
 */
public class XSDFeature {
    /**
     * Generic Method
     * Build a Document from and XSD file
     * create a Document Builder from a document builder factory
     * Set the parameters document builder factory
     * The document builder parse the XSD file into a document
     * @param inputName location of the XSD file
     * @return XSD with a document type
     */
    public static Document loadXSDDocument(String inputName) {
        final String filename = inputName;              /* location of the XSD file */
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);

        Document doc = null;        /* instantiate the document */

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

    /**
     * Generic method
     *
     * retrieve the targetname space from a document
     * Definition: XML Namespace is a mechanism to avoid name conflicts by differentiating elements or
     * attributes within an XML document that may have identical names, but different definitions.
     * catch the root element 'schema'
     * check the root element is not null
     * the target name is the attribute value of the targetNamespace node
     *
     * @param doc XSD of type document
     *            XSD is provided by Actico Modeler
     * @return the targetname of type String
     * targetNamespace is of type 'http://www.visual-rules.com/vrpath/RuleServiceName/RuleName/'
     */
    public static String getTargetnamespace(Document doc){
        // Find the docs root element and use it to find the targetNamespace
        final Element rootElem = doc.getDocumentElement();
        String targetNamespace = null;
        if (rootElem != null && rootElem.getNodeName().equals("schema")) {
            targetNamespace = rootElem.getAttribute("targetNamespace");
        }
        return targetNamespace;

    }
}

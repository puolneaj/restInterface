package ACTICO;

import Model.Input;
import org.apache.xerces.xs.XSModel;
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
     * <b>Generic Method</b><br>
     * Build a Document from and XSD file.
     *
     *
     * <ul>
     *     <li>Create a Document Builder from a Document Builder Factory.</li>
     *     <li>Set the parameters of Document Builder Factory.</li>
     *     <li>Document Builder parse the XSD file into a Document.</li>
     * </ul>
     *
     * <p>This is the first method of {@link ActicoInterface#getResponse(Input) ActicoInterface#getResponse}.<br>
     * Method {@link XMLFeature#buildXMLInstance(XSModel, String, String) buildXMLInstance} follows this method.<br>
     *
     * @param inputName location of the XSD file.
     * @return XSD of type Document.
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
     * <b>Generic Method</b><br>
     * Retrieve the targetname space from a document.
     *
     * <ul>
     *     <li>Catch the root element 'schema'</li>
     *     <li>Check if the root element is not null</li>
     * </ul>
     *
     * <p>XML Namespace is a mechanism to avoid name conflicts by differentiating elements or
     * attributes within an XML document that may have identical names, but different definitions.<br>
     * Note: the target name is the attribute value of the targetNamespace node.</p>
     *
     * <p>This method is used within {@link XMLFeature#buildXMLInstance(XSModel, String, String) buildXMLInstance}.</p>
     *
     * @param doc XSD of type document - provided by Actico Modeler
     * @return target name of type String - of type 'http://www.visual-rules.com/vrpath/RuleServiceName/RuleName/'
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

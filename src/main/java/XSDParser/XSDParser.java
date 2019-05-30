package XSDParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XSDParser {
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

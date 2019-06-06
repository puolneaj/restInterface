package XSDParser;

import Model.Request;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import static java.lang.System.*;

/**
 * Gather the functionalities of XSD file
 */
public class XSDFeature {
    /**
     * <b>Generic Method</b><br>
     * Build a Document from and XSD file.
     *
     * <ul>
     *     <li>Create a Document Builder from a Document Builder Factory.</li>
     *     <li>Set the parameters of Document Builder Factory.</li>
     *     <li>Document Builder parse the XSD file into a Document.</li>
     * </ul>
     *
     * <p>This is the first method of {@link ActicoInterface#getResponse(Request) ActicoInterface#getResponse}.<br>
     * Method  follows this method.<br>
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
     * <p>This method is used within  buildXMLInstance.</p>
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
    /**
     * Create instance of ObjectMapper with JAXB introspector
     * and default type factory.
     *
     * @return Instance of ObjectMapper with JAXB introspector
     *    and default type factory.
     */
    private ObjectMapper createJaxbObjectMapper()
    {
        final ObjectMapper mapper = new ObjectMapper();
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(typeFactory);
        // make deserializer use JAXB annotations (only)
        mapper.getDeserializationConfig().with(introspector);
        // make serializer use JAXB annotations (only)
        mapper.getSerializationConfig().with(introspector);
        return mapper;
    }

    /**
     * Write JSON Schema to standard output based upon Java source
     * code in class whose fully qualified package and class name
     * have been provided.
     *
     * @param mapper Instance of ObjectMapper from which to
     *     invoke JSON schema generation.
     * @param fullyQualifiedClassName Name of Java class upon
     *    which JSON Schema will be extracted.
     */
    private void writeToStandardOutputWithDeprecatedJsonSchema(final ObjectMapper mapper, final String fullyQualifiedClassName)
    {
        try
        {
            final JsonSchema jsonSchema = mapper.generateJsonSchema(Class.forName(fullyQualifiedClassName));
            out.println(jsonSchema);
        }
        catch (ClassNotFoundException cnfEx)
        {
            err.println("Unable to find class " + fullyQualifiedClassName);
        }
        catch (JsonMappingException jsonEx)
        {
            err.println("Unable to map JSON: " + jsonEx);
        }
    }
}

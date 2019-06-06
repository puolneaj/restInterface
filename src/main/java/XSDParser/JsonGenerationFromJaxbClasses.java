package XSDParser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import static java.lang.System.out;
import static java.lang.System.err;
/**
 * Generates JavaScript Object Notation (JSON) from Java classes
 * with Java API for XML Binding (JAXB) annotations.
 */
public class JsonGenerationFromJaxbClasses
{
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
     * Write out JSON Schema based upon Java source code in
     * class whose fully qualified package and class name have
     * been provided.
     *
     * @param mapper Instance of ObjectMapper from which to
     *     invoke JSON schema generation.
     * @param fullyQualifiedClassName Name of Java class upon
     *    which JSON Schema will be extracted.
     */
    private void writeToStandardOutputWithDeprecatedJsonSchema(
            final ObjectMapper mapper, final String fullyQualifiedClassName)
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
    /**
     * Accepts the fully qualified (full package) name of a
     * Java class with JAXB annotations that will be used to
     * generate a JSON schema.
     *
     * @param arguments One argument expected: fully qualified
     *     package and class name of Java class with JAXB
     *     annotations.
     */
    public static void main(final String[] arguments)
    {
        /*if (arguments.length < 1)
        {
            err.println("Need to provide the fully qualified name of the highest-level Java class with JAXB annotations.");
            System.exit(-1);
        }*/
        final JsonGenerationFromJaxbClasses instance = new JsonGenerationFromJaxbClasses();
        //final String fullyQualifiedClassName = arguments[0];
        final String fullyQualifiedClassName ="com.repository.xsd";
        final ObjectMapper objectMapper = instance.createJaxbObjectMapper();
        instance.writeToStandardOutputWithDeprecatedJsonSchema(objectMapper, fullyQualifiedClassName);
    }
}

/**
 * Deal with the connection with ACTICO Servers, sending and receiving information.
 *
 * <p>Actico Execution Server is a runtime repository with a collection of business rules/DMN. Using .wadl file, the business rules <br>
 * are triggered with a specific input and send back an output - the input and output follow the specifications of .wadl file.<br>
 * The input comes in the shape of a XML file or a JSON file, the execution server sends back with the same media type (e.g. XML or JSON).</p>
 *
 * <p>The set of methods embedded in {@link ACTICO.ActicoInterface#getResponse(Model.Request) ActicoInterface#getResponse} triggers Actico Execution Server.<br>
 * It mimics the action of Postman (or SoapUI) when calling the Execution Server with (1) a request body containing the {@link Model.Request}<br>
 * and a (2) a header with the media type (e.g. application/XML or application/JSON) and the authentication.</p>
 *
 */
package ACTICO;
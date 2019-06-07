package XSDParser;

import Model.ExecutionServer;
import Model.Request;
import Model.URI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Set server settings for the class {@link ActicoInterface}<br>
 * Select the appropriate ruleService on Actico Execution Server.
 */
public class ServerManager {
    /**
     * <b>Specific Method</b>
     * Launch the connection with Actico Server
     *
     *
     * <p>Method is called in {@link ActicoInterface#getResponse(Request) ActicoInterface#getResponse}.</p>
     *
     * <ul>
     *     <li>set the authentication criteria.</li>
     *     <li>set URI with a RuleService name and rule name to access a specific Actico .wadl </li>
     *     <li>open a HTTP connection with the URI provided.</li>
     *     <li>set a HTTP protocol of type POST</li>
     *     <li>set the authorization for the request body.</li>
     *     <li>encode the request body</li>
     * </ul>
     * @return the HTTP connection to the <b>.wadl</b> file on ACTICO Execution Server.
     */
    public static HttpURLConnection launchServer() throws IOException {
        //TODO Get all configuration data from .properties file
        //url is fixed and stated
        ExecutionServer execution = new ExecutionServer();
        execution.setUsername("DEFAULT\\Admin");
        execution.setPassword("Admin");
        execution.setUserpassword();

        URI uri = new URI();
        uri.setRuleService("BPRequest");
        uri.setRule("BPRequest/MainRequest");
        uri.setVersion("0.0.1-SNAPSHOT");

        //create the object URL
        URL obj = new URL(uri.toString());
        //open the connection
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //HTTP POST
        con.setRequestMethod("POST");
        //set the body of the request
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String basicAuth = Base64.getEncoder().encodeToString(execution.getUserpassword().getBytes(StandardCharsets.UTF_8));
        con.setRequestProperty("Authorization", "Basic " + basicAuth);

        return con;
    }

}

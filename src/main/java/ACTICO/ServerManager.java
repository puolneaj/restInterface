package ACTICO;

import Model.ExecutionServer;
import Model.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Set server settings for the class {@link ActicoInterface}<br>
 * Select the appropriate ruleService on Actico Execution Server.
 */
public class ServerManager {
    final static Logger logger= LogManager.getLogger(ServerManager.class);
    /**
     * Launch the connection with Actico Server
     *
     * <p>Method is called in {@link ActicoInterface}.</p>
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
    public static HttpURLConnection launchServer() {
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
        HttpURLConnection con=null;
        try {
            URL obj = new URL(uri.toString());
            con = (HttpURLConnection) obj.openConnection();

            //HTTP POST
            con.setRequestMethod("POST");
            // Basic authentication
            BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String encodedAuthorization = enc.encode(execution.getUserpassword().getBytes());
            con.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            //set the body of the request
            con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
//            con.setRequestMethod("accept:application/xml");
            con.setRequestProperty("Expect", "100-continue");
            logger.debug("Launch the connection with Actico Server");

        } catch (IOException e) {
            e.printStackTrace();
        }return con;
    }

}

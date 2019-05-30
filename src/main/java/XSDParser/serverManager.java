package XSDParser;

import Model.ExecutionServer;
import Model.URI;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class serverManager {
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
            con.setRequestProperty("Authorization", "Basic " +
                    encodedAuthorization);
            //set the body of the request
            con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
            con.setRequestProperty("Expect", "100-continue");

        } catch (IOException e) {
            e.printStackTrace();
        }return con;
    }

}

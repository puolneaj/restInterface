package XSDParser;

import Model.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Focus on the connection point with <b>Actico Server</b>, i.e. localhost:8087.
 *
 */
public class ActicoServer {
    /**
     * Call the method <b>getXMLResponse()</b>.
     * <ul>
     * <li>read an XML file.</li>
     * <li>send the Request in XML format.</li>
     * <li>read and parse the XML response from Actico Server.</li>
     * <li>insert the XML nodes attributes into Output object.</li>
     * </ul>
     *
     * @param args argument not in use
     */
    /*public static void main(String[] args) {
        ActicoServer localhost = new ActicoServer();
        localhost.getResponse();
    }*/

    /**
     * Deal with Actico Server.
     * <ul>
     * <li>connect with Actico Server for a given rule.</li>
     * <li>pass the authentication gate.</li>
     * <li>set a hardcoded request via the <b>toString()</b> method of {@link Input}.</li>
     * <li>get a response from Actico server.</li>
     * <li>insert the attributes value of XML nodes in {@link Output} attributes.</li>
     * </ul>
     */
    public static Output getJSONResponse(Request request) {

        Output output=new Output();
        try {
            Gson g = new Gson();

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

            String JSONinput = g.toJson(request);
            System.out.println(JSONinput);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = JSONinput.getBytes();
                os.write(input, 0, input.length);
            }

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

            System.out.println("ANSWER to ACTICO Execution Server: " + response);

            System.out.println("---------------INSERT INFORMATION INTO OUTPUT OBJECT-----------------\n");

            output = g.fromJson(response.toString(), Output.class);
            System.out.println(output);

        } catch (Exception e) {
            System.out.println(e);
        }

        return output;
    }
}

package XMLReader;

import Model.Request;
import Model.Input;
import Model.Output;
import Model.ExecutionServer;
import Model.URI;

//Parse an XML file
import org.w3c.dom.*;
import org.xml.sax.*;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.*;

//Read a file
import java.io.*;

//HTTP Protocol
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadXML {
    public static void main(String[] args) {
        ReadXML localhost = new ReadXML();
        localhost.getResponse();
    }

    public void getResponse() {

        try {
            //url is fixed and stated
            ExecutionServer execution = new ExecutionServer();
            execution.setUsername("DEFAULT\\Admin");
            execution.setPassword("Admin");
            execution.setUserpassword();

            URI uri = new URI();
            uri.setRuleService("BPRequest");
            uri.setRule("BPRequest/MainRequest");
            uri.setVersion("0.0.1-SNAPSHOT");


            Request request = new Request();
            Input input = new Input();
            Output output = new Output();

            request.setCode("VR32");
            request.setMobile_priv("00336758697");
            request.setName("Durand");
            request.setTel_priv("00297616881");
            request.setObj_id("100");

            input.setRequest(request);

            //create the object URL
            URL obj = new URL(uri.toString());
            //open the connection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
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
            //send the XML
            con.setDoOutput(true);
            OutputStream outStream = con.getOutputStream();
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
            outStreamWriter.write(input.toString());
            outStreamWriter.flush();
            outStreamWriter.close();
            outStream.close();

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

            //read the whole file XML in URL address
            System.out.println("Send the XML to Actico Execution Server: " + response);

            System.out.println("---------------READ A SPECIFIC XML NODE-----------------\n");

            //following the bufferedReader, DocumentBuilderFactory parses the response.
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(new InputSource(new StringReader(response.toString())));
            //declare and instantiate a nodelist
            NodeList errNodesStatus = doc.getElementsByTagName("status");
            NodeList errNodesKey = doc.getElementsByTagName("obj_id_key");
            if (errNodesStatus.getLength() > 0 && errNodesKey.getLength() > 0) {
                Element errStatus = (Element) errNodesStatus.item(0);
                Element errKey = (Element) errNodesKey.item(0);
                output.setStatus(errStatus.getTextContent());
                output.setObj_id_key(errKey.getTextContent());
                System.out.println("Response values from Actico Execution Server: " + output.toString());
            } else {
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

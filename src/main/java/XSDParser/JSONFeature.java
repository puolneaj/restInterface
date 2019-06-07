package XSDParser;

import Model.Output;
import Model.Request;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *
 */
public class JSONFeature {
    /**
     * <b>Generic Method</b><br>
     * Read data from a source using Bufferedreader.
     *
     * <b>BufferedReader</b> simplifies reading text from a character input stream.<br>
     * The BufferReader insert the whole content of the connection inside a string of type <B>StringBuilder</B>.<br>
     * The reading process stops at the end of the file.
     *
     * <p>Method {@link #writeRequest(HttpURLConnection, Request) writeRequest} precedes this method.<br>
     * Method {@link #buildResponseObject(StringBuffer) buildResponseObject} precedes this method.</p>
     *
     * <p>Note: The connection HTTP in the parameter is already open.</p>
     * @param con Connection HTTP from which the stream is read.
     * @return string value of what the contains the Http connection.
     * @throws IOException if an issue with I.O operations
     */
    public static StringBuffer readJSONResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine="";
        StringBuffer response = new StringBuffer();
        //read line by line the document
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + "\n");
        }
        in.close();
        return response;
    }

    /**
     *  <b>Specific Method</b><br>
     *  Build the {@link Output}.
     *
     * <ul>
     *     <li>Create an object of type <b>Gson</b></li>
     *     <li>Insert the response of Actico into {@link Output} using the method <b>fromJson</b></li>
     * </ul>
     *
     * <p>Method {@link #readJSONResponse(HttpURLConnection) readJSONResponse} precedes this method.<br>
     *  This Method is the last method embedded in  {@link ActicoInterface#getResponse(Request) ActicoInterface#getResponse}.</p>
     *
     * @param response string with the content of the JSON response
     * @return the object model {@link Output}
     */
    public static Output buildResponseObject(StringBuffer response) {
        Gson g = new Gson();
        Output output = g.fromJson(response.toString(), Output.class);
        return output;
    }

    /**
     * <b>Specific Method</b><br>
     *  Write and send a request.
     *
     *  <ul>
     *      <li>Cast {@link Request} into a String</li>
     *      <li>Open a connection for writing</li>
     *  </ul>
     *
     * <p>Method {@link ServerManager#launchServer() launchServer} precedes this method.<br>
     *  Method {@link #readJSONResponse(HttpURLConnection) readJSONResponse} precedes this method.</p>
     *
     *  <p>Note: the HTTP connection is open at his stage of the process</p>
     *
     * @param con Http connection in which the request information is sent
     * @param request request attributes
     */
    public static void writeRequest(HttpURLConnection con, Request request) {
        Gson g = new Gson();
        String JSONrequest = g.toJson(request);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = JSONrequest.getBytes();
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

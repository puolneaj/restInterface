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
     *
     * @param con
     * @return
     * @throws IOException
     */
    public static StringBuffer readJSONResponse(HttpURLConnection con) throws IOException {
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
        return response;
    }

    /**
     *
     * @param response
     * @return
     */
    public static Output buildResponseObject(StringBuffer response) {
        Gson g = new Gson();
        Output output = g.fromJson(response.toString(), Output.class);
        return output;
    }

    /**
     *
     * @param con
     * @param request
     */
    public static void writeRequest(HttpURLConnection con, Request request) {
        Gson g = new Gson();
        String JSONinput = g.toJson(request);
        System.out.println(JSONinput);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = JSONinput.getBytes();
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

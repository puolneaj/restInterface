package ACTICO;
import Model.*;

import com.actico.restInterface.RequestDaoService;
import java.io.*;
import java.net.HttpURLConnection;

/**
 * <b> Backbone of the project.</b>
 */
public class ActicoInterface {
    /**
     * <b>Main Method</b><br>
     * Trigger Actico Server and {@link #getResponse(Request) getResponse} from an hardcoded request.
     *
     * <p>Print the output generated.</p>
     * @param args argument not required.
     * @throws IOException if I/O operations fail or interrupt.
     */
     public static void main(String args[]) throws IOException {

        Request request = new Request();

        request.setProductCategory("Share - Bearer");
        request.setClient("John Smith");
        request.setDomicile("Switzerland");
        request.setTradePlace("XETRA, Deutschland");
        request.setDocId("7875827");

        System.out.println(getResponse(request).toString());
    }

    /**
     * Trigger Actico server - Backbone of the project.
     *
     *<p>Set of nested methods used in this method.</p>
     * <ul>
     *     <li>{@link ServerManager#launchServer ServerManager.launchServer}</li>
     *     <li>{@link JSONFeature#writeRequest JSONFeature.writeRequest }</li>
     *     <li>{@link JSONFeature#readJSONResponse JSONFeature.readJSONResponse}</li>
     *     <li>{@link JSONFeature#buildResponseObject JSONFeature.buildResponseObject}</li>
     * </ul>
     *
     * <p>Called by {@link #main(String[]) ActicoInterface#main} method for troubleshoot purpose.<br>
     *  Called by REST interface Application within {@link RequestDaoService#acticoResponse(Request) RequestDaoService#acticoResponse} method.</p>
     *
     * @param request the model on which the JSON file request is based upon
     * @return the output model on which the JSON file response is based upon
     * @throws IOException if I/O operations fail or interrupt.
     */
    public static Output getResponse(Request request) throws IOException {
        HttpURLConnection con =ServerManager.launchServer();
        JSONFeature.writeRequest(con,request);
        StringBuffer response=JSONFeature.readJSONResponse(con);
        return JSONFeature.buildResponseObject(response);
    }

}

package ACTICO;

import javax.xml.transform.TransformerConfigurationException;
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
     * Print the output generated
     * @param args argument not required
     * @throws TransformerConfigurationException
     */
    public static void main(String args[]) throws TransformerConfigurationException, IOException {

        Request request = new Request();

        request.setCode("VR32");
        request.setMobilePriv("00336758697");
        request.setName("Durand");
        request.setTelPriv("00297616881");
        request.setDocId("100");

        System.out.println(getResponse(request).toString());
    }

    /**
     * Trigger Actico server - Backbone of the project.
     *
     *<p>Set of nested methods used in this method</p>
     * <ul>
     *     <li>{@link XSDFeature#loadXSDDocument(String) loadXSDDocument}</li>
     * </ul>
     *
     * <p>Called in {@link #main(String[]) ActicoInterface#main} method for troubleshoot purpose.<br>
     *  Called in REST interface Application within {@link RequestDaoService#acticoResponse(Request) RequestDaoService#acticoResponse} method.</p>
     *
     * @param request the model on which the XML file request is based upon
     * @return the output model on which the XML file response is based upon
     */
    public static Output getResponse(Request request) throws IOException {
        HttpURLConnection con =ServerManager.launchServer();
        JSONFeature.writeRequest(con,request);
        StringBuffer response=JSONFeature.readJSONResponse(con);
        return JSONFeature.buildResponseObject(response);
    }

}

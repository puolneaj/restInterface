package com.actico.restInterface;

import Model.Input;
import Model.Output;
import Model.Request;
import ACTICO.ActicoInterface;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerConfigurationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gather the methods to manipulate the requests and trigger Actico Server.
 * @deprecated
 */
@Component
public class RequestDaoService {
    /**
     * list of {@link Request} - hardcoded
     */
    public static List<Request> requests = new ArrayList<>();
    /**
     * empty list of {@link Output}
     */
    public static List<Output> outputs = new ArrayList<>();

    private static int requestsCount = 3;

    /**
     * set the attributes of the object {@link Request} using the constructor of the class {@link Request}.
     */
    static {
        requests.add(new Request("1", "A1", "0011", "A", "001"));
        requests.add(new Request("2", "B2", "0022", "B", "002"));
        requests.add(new Request("3", "C3", "0033", "C", "003"));
    }

    /**
     * Return the entire list of <b>requests.</b>.
     * <p>Method called in the class {@link RequestResource}.</p>
     * @return entire list of requests.
     */
    public List<Request> findAll() {
        return requests;
    }

    /**
     * Add a {@link Request} item to the list <b>requests</b>.
     * <p>Method is called in the class {@link RequestResource}.<br>
     *If the {@link Request} item doesn't have a identifier, its identifier is set to the next value.</p>
     *
     * @param request of type {@link Request}.
     * @return request once it has been added to the list requests.
     */
    public Request save(Request request) {
        if (request.getDocId() == null) {
            request.setDocId(String.valueOf(++requestsCount));
        }
        requests.add(request);
        return request;
    }

    /**
     * Return a specific {@link Request}.
     * <p>Method is called in the class {@link RequestResource}.<br>
     * Matching criteria is the attribute <b>obj_id</b> of the object {@link Request}.</p>
     * @param id obj_id of the 'Request' object.
     * @return corresponding request.
     */
    public Request findOne(String id) {
        for (Request request : requests) {
            if (request.getDocId().equals(id)) {
                return request;
            }
        }
        return null;
    }

    /**
     * Delete a specific {@link Request} from the list <b>requests</b>.
     * <p>Method is called in the class {@link RequestResource}</p>
     * @param id obj_id of the request
     * @return corresponding {@link Request}
     */
    public Request deleteById(String id) {
        Iterator<Request> iterator = requests.iterator();
        while (iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getDocId().equals(id)) {
                iterator.remove();
                return request;
            }
        }
        return null;
    }

    /**
     * Trigger a response for a given {@link Request}.<br>
     *     Link the localhost:8080 to localhost:8087.
     * <p>Method is called in the class {@link RequestResource}.<br>
     * Call Actico Execution Server via class {@link ActicoInterface} in the piece of code :<br>
     * <pre>{@code Output output = ActicoInterface.getResponse(input);}</pre><br>
     * Add the output to the list '<b>outputs</b>.
     * @param request {@link Request}
     * @return {@link Output}
     * @throws TransformerConfigurationException if there is an fatal error of configuration
     */
    public Output acticoResponse(Request request) throws TransformerConfigurationException {
        Input input = new Input();
        input.setRequest(request);

        Output output = ActicoInterface.getResponse(input);
        outputs.add(output);
        return output;
    }

    /**
     * Get all <b>outputs</b> from Actico Model.
     * <p>Method is called in the class {@link RequestResource}</p>.
     * @return the list '<b>outputs</b>' of composed of {@link Output}
     */
    public List<Output> findAllResponses() {
        return outputs;

    }

    /**
     * Get a specific response of type {@link Output} among the responses from Actico server.
     * <p>Method is called in the class {@link RequestResource}</p>.
     * @param id obj_id_key of output object.
     * @return output {@link Output} with the key id corresponding to the input parameter <b>id</b>.
     */
    public Output findOneResponse(String id) {
        for (Output output : outputs) {
            if (output.getKey().equals(id)) {
                return output;
            }
        }
        return null;
    }

    /**
     * Delete a specific response from <b>Actico Server</b>.
     * <p>Method is called in the class {@link RequestResource}<br>.
     * Use an <b>Iterator</b> to go through the element of the list <b>outputs</b></p>
     * @param id obj_id_key from output object.
     * @return {@link Output} object .
     */
    public Output deleteResponseById(String id) {
        Iterator<Output> iterator = outputs.iterator();
        while (iterator.hasNext()) {
            Output output = iterator.next();
            if (output.getKey().equals(id)) {
                iterator.remove();
                return output;
            }
        }
        return null;
    }

}

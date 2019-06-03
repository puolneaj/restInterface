package com.actico.restInterface;

import Model.Input;
import Model.Output;
import Model.Request;
import XSDParser.Interface;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerConfigurationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * instantiate and fill a list of requests
 * instantiate a list of outputs
 */
@Component
public class requestDaoService {
    public static List<Request> requests = new ArrayList<>();
    public static List<Output> outputs = new ArrayList<>();

    private static int requestsCount = 3;

    static {
        requests.add(new Request("1", "A1", "0011", "A", "001"));
        requests.add(new Request("2", "B2", "0022", "B", "002"));
        requests.add(new Request("3", "C3", "0033", "C", "003"));
    }

    /**
     * @return the full list of requests
     */
    public List<Request> findAll() {
        return requests;
    }

    /**
     *
     * @param request
     * @return the request once it has been added to the list requests
     */
    public Request save(Request request) {
        if (request.getObj_id() == null) {
            request.setObj_id(String.valueOf(++requestsCount));
        }
        requests.add(request);
        return request;
    }

    /**
     *
     * @param id obj_id of the request
     * @return the corresponding request
     */
    public Request findOne(String id) {
        for (Request request : requests) {
            if (request.getObj_id().equals(id)) {
                return request;
            }
        }
        return null;
    }

    /**
     *
     * @param id obj_id of the request
     * @return the corresponding id
     */
    public Request deleteById(String id) {
        Iterator<Request> iterator = requests.iterator();
        while (iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getObj_id().equals(id)) {
                iterator.remove();
                return request;
            }
        }
        return null;
    }

    /**
     * Trigger a response for a given request
     * Call Actico Execution Server
     * Add the output to the list
     * @param request
     * @return the output
     * @throws TransformerConfigurationException
     */
    public Output acticoResponse(Request request) throws TransformerConfigurationException {
        Input input = new Input();
        input.setRequest(request);

        Output output = Interface.getResponse(input);
        outputs.add(output);
        return output;
    }

    /**
     * Get all the responses/output from Actico
     * @return
     */
    public List<Output> findAllResponses() {
        return outputs;

    }

    /**
     * Get a specific response among the responses from Acctico server
     * @param id obj_id_key of output object
     * @return output
     */
    public Output findOneResponse(String id) {
        for (Output output : outputs) {
            if (output.getObj_id_key().equals(id)) {
                return output;
            }
        }
        return null;
    }

    /**
     * Delete a specific response from Actico Server
     * @param id obj_id_key from output object
     * @return output object
     */
    public Output deleteResponseById(String id) {
        Iterator<Output> iterator = outputs.iterator();
        while (iterator.hasNext()) {
            Output output = iterator.next();
            if (output.getObj_id_key().equals(id)) {
                iterator.remove();
                return output;
            }
        }
        return null;
    }

}

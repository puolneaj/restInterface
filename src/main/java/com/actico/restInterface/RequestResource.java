package com.actico.restInterface;

import Model.Output;
import Model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.transform.TransformerConfigurationException;
import java.net.URI;
import java.util.List;

@RestController
public class RequestResource {

    @Autowired
    private requestDaoService service;

    //retrieveAllRequests
    @GetMapping(path = "/requests")
    public List<Request> retrieveAllRequests() {
        return service.findAll();
    }

    //retrieveRequest
    @GetMapping(path = "/requests/{id}")
    public Request retrieveRequest(@PathVariable String id) {
        Request request = service.findOne(id);
        if (request == null) {
            throw new RequestNotFoundException("id-" + id);
        }
        return request;
    }

    @PostMapping(path = "/requests")
    public ResponseEntity<Object> createRequest(@RequestBody Request request) {
        Request savedRequest = service.save(request);
        //CREATED
        // /Request/{id} savedRequest.getId()
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRequest.getObj_id()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/requests/{id}")
    public @ResponseBody
    void removeRequest(@PathVariable String id) {
        Request Request = service.deleteById(id);
        if (Request == null) {
            throw new RequestNotFoundException("id - " + id);
        }
    }

    @PostMapping(path = "/requestsACTICO")
    public ResponseEntity<Object> triggerExecutionServer(@RequestBody Request request) throws TransformerConfigurationException {
        Output output = service.acticoResponse(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(output.getObj_id_key()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/requestsACTICO")
    public List<Output> retrieveAllACTICOResponses() {
        return service.findAllResponses();
    }

    //retrieveRequest
    @GetMapping(path = "/requestsACTICO/{id}")
    public Output retrieveResponse(@PathVariable String id) {
        Output output = service.findOneResponse(id);
        if (output == null) {
            throw new RequestNotFoundException("id-" + id);
        }
        return output;
    }

    @DeleteMapping(path = "/requestsACTICO/{id}")
    public @ResponseBody
    void removeResponse(@PathVariable String id) {
        Output output = service.deleteResponseById(id);
        if (output == null) {
            throw new RequestNotFoundException("id - " + id);
        }
    }

}

package com.actico.restInterface;

import Model.Output;
import Model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * <b>Endpoints and HTTP protocols</b>
 * <p>Architecture of the endpoints is displayed in this class.</p>
 *
 * <b>@RestController Annotation</b> corresponds to @Controller and @ResponseBody annotations altogether
 */
@RestController
public class RequestResource {
    /**
     * <p>Java Object RequestDaoService is set a field of {@link RequestResource}</p>
     * <p>@Autowired annotation is enabled</p>
     * Used directly on properties, autowiring eliminates the need for getters and setters
     */
    @Autowired
    private RequestDaoService service;

    /**
     * Display all the requests available on the endpoint
     * @see <a href="http://localhost:8080/requests">http://localhost:8080/requests displays the requests</a>
     * <p><b>@GetMapping annotation</b> maps HTTP GET requests onto specific handler methods.
     * It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).</p>
     *
     * @return a list of all the requests in JSON format using the method <b>findAll()</b>
     * from the class {@link RequestDaoService}
     */
    @GetMapping(path = "/requests")
    public List<Request> retrieveAllRequests() {
        return service.findAll();
    }

    /**
     * Display all the requests available on the endpoint
     *
     * <p><b>@GetMapping annotation</b> maps HTTP GET requests onto specific handler methods.
     * It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).</p>
     *
     * <p><b>@PathVariable annotation</b> handles dynamic URIs where one or more of the URI value works as a parameter.</p>
     *
     * @throws RequestNotFoundException if the request is null.
     *
     * @return a specific request in JSON format using the method <b>findOne()</b>
     * from the class {@link RequestDaoService}
     */
    @GetMapping(path = "/requests/{id}")
    public Request retrieveRequest(@PathVariable String id) {
        Request request = service.findOne(id);
        if (request == null) {
            throw new RequestNotFoundException("id-" + id);
        }
        return request;
    }

     /**
      * Push a new 'Request' object on the server.
      *
     * <p><b>@PostMapping annotation</b> is specialized version of @RequestMapping annotation that acts as a shortcut
     * for @RequestMapping(method = RequestMethod.POST). @PostMapping annotated methods handle the
     * HTTP POST requests matched with given URI expression.</p>
     *
     * <p><b>@RequestBody annotation</b> indicating a method parameter should be bound to the body of the web request.</p>
     *
     * The location of the object (i.e. using @GetMapping(path="/requests/{id}") is correlated to the obj_id of the request
      * (see below)
     * <pre>{@code URI location = ServletUriComponentsBuilder
     *                 .fromCurrentRequest()
     *                 .path("/{id}")
     *                 .buildAndExpand(savedRequest.getDocId()).toUri();}</pre>
     *
     * @return a ResponseEntity, e.g. represents the whole HTTP response: status code, headers, and body. The method
      * builds the object (JSON format) at a given location.
     */
    @PostMapping(path = "/requests")
    public ResponseEntity<Object> createRequest(@RequestBody Request request) {
        Request savedRequest = service.save(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRequest.getDocId()).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Delete a request on the localhost:8080 with the parameter id given.
     * Using the method deleteByid from class {@link RequestDaoService}.
     *
     * <p><b>@DeleteMapping annotation</b> is a composed annotation that acts as a shortcut
     * for @RequestMapping(method = RequestMethod.DELETE).</p>
     *
     * @throws RequestNotFoundException if request is null
     * @param id corresponds to the identifier of 'Request' object.
     */
    @DeleteMapping(path = "/requests/{id}")
    public @ResponseBody
    void removeRequest(@PathVariable String id) {
        Request Request = service.deleteById(id);
        if (Request == null) {
            throw new RequestNotFoundException("id - " + id);
        }
    }

    /**
     * Trigger Actico Server with a given Request. It calls the method triggerExecutionServer
     * from {@link RequestDaoService}.
     *
     * <p><b>@PostMapping annotation</b> is specialized version of @RequestMapping annotation that acts as a shortcut
     * for @RequestMapping(method = RequestMethod.POST). @PostMapping annotated methods handle the
     * HTTP POST requests matched with given URI expression.<br>
     * <b>@RequestBody annotation</b> indicating a method parameter should be bound to the body of the web request.</p>
     *
     *The location of the object (i.e. using @GetMapping(path="/requests/{id}") is correlated to the obj_id of the request
     * (see below)<br>
     * <pre>{@code URI location = ServletUriComponentsBuilder
     *                 .fromCurrentRequest()
     *                  .path("/{id}")
     *               .buildAndExpand(savedRequest.getKey()).toUri();}</pre>
     * @param request is the object is fed to Actico Server
     * @return a ResponseEntity, e.g. represents the whole HTTP response: status code, headers, and body. The method
     * builds the object (JSON format) at a given location.
     */
    @PostMapping(path = "/responses")
    public ResponseEntity<Object> triggerExecutionServer(@RequestBody Request request)
            throws IOException {
        Output output = service.acticoResponse(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(output.getKey()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Display all the responses from ACTICO Server. Responses are gathered in JSON format
     * following past requests.<br>
     * The method retrieves findAllResponses from {@link RequestDaoService}.
     *
     * <p><b>@GetMapping annotation</b> maps HTTP GET requests onto specific handler methods.<br>
     * It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).</p>
     *
     * @see <a href="http://localhost:8080/requestsACTICO">http://localhost:8080/requestsACTICO displays the requests</a>
     *
     * @return a list of all the answers in JSON format using the method <b>findAllResponses()</b>
     * from the class {@link RequestDaoService}. The results are in the shape of 'Output' objects.
     */
    @GetMapping(path = "/responses")
    public List<Output> retrieveAllACTICOResponses() {
        return service.findAllResponses();
    }

    /**
     * Display a specific response from ACTICO server.
     *
     * <p><b>@GetMapping annotation</b> maps HTTP GET requests onto specific handler methods.<br>
     * It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
     * <b>@PathVariable annotation</b> handles dynamic URIs where one or more of the URI value works as a parameter.</p>
     *
     * @throws RequestNotFoundException if the output (i.e. response) from ACTICO is null.
     *
     * @return a specific request in JSON format using the method <b>findOneResponse(id)</b>
     * from the class {@link RequestDaoService}
     */
    @GetMapping(path = "/responses/{id}")
    public Output retrieveResponse(@PathVariable String id) {
        Output output = service.findOneResponse(id);
        if (output == null) {
            throw new RequestNotFoundException("id-" + id);
        }
        return output;
    }

    /**
     * Delete an output (i.e. the response from ACTICO server) on the localhost:8080 with the parameter id given.
     * Using the method <b>deleteResponseById(id)</b> from class {@link RequestDaoService}.
     *
     * <p><b>@DeleteMapping annotation</b> is a composed annotation that acts as a shortcut
     * for @RequestMapping(method = RequestMethod.DELETE).</p>
     *
     * @throws RequestNotFoundException if request is null
     * @param id corresponds to the identifier of 'Request' object.
     */
    @DeleteMapping(path = "/responses/{id}")
    public @ResponseBody
    void removeResponse(@PathVariable String id) {
        Output output = service.deleteResponseById(id);
        if (output == null) {
            throw new RequestNotFoundException("id - " + id);
        }
    }

}

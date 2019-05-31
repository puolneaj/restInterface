package com.actico.restInterface;

import Model.Request;
import XSDParser.XsdToXml;
import org.springframework.stereotype.Component;

import javax.xml.transform.TransformerConfigurationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class requestDaoService {
    public static List<Request> requests=new ArrayList<>();

    private static int requestsCount=3;

    static{
        requests.add(new Request("1","A1","0011","A","001"));
        requests.add(new Request("2","B2","0022","B","002"));
        requests.add(new Request("3","C3","0033","C","003"));
    }

    public List<Request> findAll(){
        return requests;
    }

    public Request save(Request request){
        if(request.getObj_id() == null){
            request.setObj_id(String.valueOf(++requestsCount));
        }
        requests.add(request);
        return request;
    }

    public Request findOne(String id) {
        for (Request request : requests) {
            if (request.getObj_id().equals(id)) {
                return request;
            }
        }
        return null;
    }

    public Request deleteById(String id){
        Iterator<Request> iterator=requests.iterator();
        while(iterator.hasNext()) {
            Request request = iterator.next();
            if (request.getObj_id().equals(id)) {
                iterator.remove();
                return request;
            }
        }
        return null;
    }

    public StringBuffer acticoResponse() throws TransformerConfigurationException {
        StringBuffer answer= XsdToXml.getBackRequest();
        return answer;
    }

}

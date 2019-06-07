package Model;

import XSDParser.ActicoInterface;

/**
 * Structure of the request
 */
public class Request {

    private String docId;

    private String code;

    private String mobilePriv;

    private String name;

    private String telPriv;

    public String getDocId() {
        return docId;
    }

    //TODO possible decision for attribute name mapping
//    public String getObj_idAttributeName() {
//        return "ns1:name";
//    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobilePriv() {
        return mobilePriv;
    }

    public void setMobilePriv(String mobilePriv) {
        this.mobilePriv = mobilePriv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPriv() {
        return telPriv;
    }

    public void setTelPriv(String telPriv) {
        this.telPriv = telPriv;
    }

    /**
     * Constructor used in {@link XSDParser.ActicoServer} to build the request sent to <b>Actico Server</b>.
     */
    public Request(){}

    /**
     * Constructor used in {@link ActicoInterface} to build the hardcoded request sent to <b>Actico Server</b>.
     * @param docId please refer to Model BPRequest in Actico Modeler
     * @param code please refer to Model BPRequest in Actico Modeler
     * @param mobilePriv please refer to Model BPRequest in Actico Modeler
     * @param name please refer to Model BPRequest in Actico Modeler
     * @param telPriv please refer to Model BPRequest in Actico Modeler
     */
    public Request(String docId, String code, String mobilePriv, String name, String telPriv) {
        this.docId = docId;
        this.code = code;
        this.mobilePriv = mobilePriv;
        this.name = name;
        this.telPriv = telPriv;
    }

    /**
     * @deprecated - used to troubleshoot
     * @return a standard toString()
     */
    @Override
    public String toString() {
        return "{ \"docId\" : "+this.getDocId()+", \"code\" : "+
                this.getCode()+", \"name\" : "+this.getName()+
                ", \"telPriv\" : "+this.getTelPriv()+", \"mobilePriv\" : "+
                this.getTelPriv()+" }";
    }
}

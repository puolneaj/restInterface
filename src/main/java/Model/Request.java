package Model;

import XSDParser.ActicoInterface;

/**
 * Structure of the request embedded in {@link Input}
 */
public class Request {

    private String obj_id;

    private String code;

    private String mobile_priv;

    private String name;

    private String tel_priv;

    public String getObj_id() {
        return obj_id;
    }

    //TODO possible decision for attribute name mapping
//    public String getObj_idAttributeName() {
//        return "ns1:name";
//    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile_priv() {
        return mobile_priv;
    }

    public void setMobile_priv(String mobile_priv) {
        this.mobile_priv = mobile_priv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel_priv() {
        return tel_priv;
    }

    public void setTel_priv(String tel_priv) {
        this.tel_priv = tel_priv;
    }

    /**
     * Constructor used in {@link XSDParser.ActicoServer} to build the request sent to <b>Actico Server</b>.
     */
    public Request(){}

    /**
     * Constructor used in {@link ActicoInterface} to build the hardcoded request sent to <b>Actico Server</b>.
     * @param obj_id please refer to Model BPRequest in Actico Modeler
     * @param code please refer to Model BPRequest in Actico Modeler
     * @param mobile_priv please refer to Model BPRequest in Actico Modeler
     * @param name please refer to Model BPRequest in Actico Modeler
     * @param tel_priv please refer to Model BPRequest in Actico Modeler
     */
    public Request(String obj_id, String code, String mobile_priv, String name, String tel_priv) {
        this.obj_id = obj_id;
        this.code = code;
        this.mobile_priv = mobile_priv;
        this.name = name;
        this.tel_priv = tel_priv;
    }

    /**
     * @deprecated - used to troubleshoot
     * @return a standard toString()
     */
    @Override
    public String toString() {
        return "ClassPojo [obj_id = " + obj_id + ", code = " + code + ", mobile_priv = " + mobile_priv + ", name = " + name + ", tel_priv = " + tel_priv + "]";
    }
}

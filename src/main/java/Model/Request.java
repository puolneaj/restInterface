package Model;

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
    public String getObj_idAttributeName() {
        return "ns1:name";
    }

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

    @Override
    public String toString() {
        return "ClassPojo [obj_id = " + obj_id + ", code = " + code + ", mobile_priv = " + mobile_priv + ", name = " + name + ", tel_priv = " + tel_priv + "]";
    }
}

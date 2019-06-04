package Model;

/**
 * Model of the response from Actico Server.<br>
 * Match Actico model outputs with the fields <b>obj_id_key</b> and <b>status</b>
 */
public class Output {

    private String obj_id_key;
    private String status;

    public String getObj_id_key() {
        return obj_id_key;
    }

    public void setObj_id_key(String obj_id_key) {
        this.obj_id_key = obj_id_key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Hardcoded response from Actico Server.
     * @deprecated
     * @return Hardcoded response from Actico Server.
     */
    @Override
    public String toString() {
        return "<output xmlns=\"http://www.visual-rules.com/vrpath/BPRequest/MainRequest/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                "   <obj_id_key>"+this.getObj_id_key()+"</obj_id_key>" +
                "   <status>"+this.getStatus()+"</status>" +
                "</output>";
    }
}

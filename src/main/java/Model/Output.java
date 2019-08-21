package Model;

/**
 * Model of the response from Actico Server.<br>
 * Match Actico model outputs with the fields <b>key</b> and <b>status</b>
 * @deprecated
 */
public class Output {

    private String key;
    private String status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
                "   <key>"+this.getKey()+"</key>" +
                "   <status>"+this.getStatus()+"</status>" +
                "</output>";
    }
}

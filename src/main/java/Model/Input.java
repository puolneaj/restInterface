package Model;

/**
 * Model of the Request sent to Actico Execution Server
 * <p>{@link Request} Model is embedded inside.</p>
 */
public class Input {
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Hardcoded JSON Request to Actico Server.
     *
     * @return Hardcoded {@link Request} to Actico Server.
     */
    @Override
    public String toString() {
        return "{ \"obj_id\" : "+this.getRequest().getObj_id()+", \"code\" : "+
                this.getRequest().getCode()+", \"name\" : "+this.getRequest().getName()+
                ", \"tel_priv\" : "+this.getRequest().getTel_priv()+", \"mobile_priv\" : "+
                this.getRequest().getTel_priv()+" }";
    }
}


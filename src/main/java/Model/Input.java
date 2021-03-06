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
     * Hardcoded Request to Actico Server.
     * @deprecated - the XSD file is used to structure the XML file instead
     * @return Hardcoded {@link Request} to Actico Server.
     */
    @Override
    public String toString() {
        return  "<input xmlns=\"http://www.visual-rules.com/vrpath/BPRequest/MainRequest/\" xmlns:ns1=\"http://www.visual-rules.com/vrpath/BPRequest/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                    " <request> " +
                        "<docId>"+this.getRequest().getDocId()+"</docId> " +
                        "<ProductCategory>"+this.getRequest().getProductCategory()+"</ProductCategory>" +
                        " <Client>"+this.getRequest().getClient()+"</Client> " +
                        "<Domicile>"+this.getRequest().getTradePlace()+"</Domicile> " +
                        "<TradePlace>"+this.getRequest().getTradePlace()+"</TradePlace>" +
                    " </request> " +
                "</input>";
    }
}


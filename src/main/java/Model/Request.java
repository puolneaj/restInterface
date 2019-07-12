package Model;

import ACTICO.ActicoInterface;

/**
 * Structure of the request
 */
public class Request {

    private String docId;

    private String ProductCategory;

    private String Client;

    private String Domicile;

    private String TradePlace;

    public String getDocId() {
        return docId;
    }

    //TODO possible decision for attribute Domicile mapping
//    public String getObj_idAttributeName() {
//        return "ns1:Domicile";
//    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        this.ProductCategory = productCategory;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        this.Client = client;
    }

    public String getDomicile() {
        return Domicile;
    }

    public void setDomicile(String domicile) {
        this.Domicile = domicile;
    }

    public String getTradePlace() {
        return TradePlace;
    }

    public void setTradePlace(String tradePlace) {
        this.TradePlace = tradePlace;
    }

    /**
     * Constructor used in {@link ACTICO.ActicoInterface} to build the request sent to <b>Actico Server</b>.
     */
    public Request(){}

    /**
     * Constructor used in {@link ActicoInterface} to build the hardcoded request sent to <b>Actico Server</b>.
     * @param docId please refer to Model BPRequest in Actico Modeler
     * @param ProductCategory please refer to Model BPRequest in Actico Modeler
     * @param Client please refer to Model BPRequest in Actico Modeler
     * @param Domicile please refer to Model BPRequest in Actico Modeler
     * @param TradePlace please refer to Model BPRequest in Actico Modeler
     */
    public Request(String docId, String ProductCategory, String Client, String Domicile, String TradePlace) {
        this.docId = docId;
        this.ProductCategory = ProductCategory;
        this.Client = Client;
        this.Domicile = Domicile;
        this.TradePlace = TradePlace;
    }

    /**
     * @return a standard toString()
     */
    @Override
    public String toString() {
        return "{ \"docId\" : "+this.getDocId()+", \"ProductCategory\" : "+
                this.getProductCategory()+", \"Domicile\" : "+this.getDomicile()+
                ", \"TradePlace\" : "+this.getTradePlace()+", \"Client\" : "+
                this.getTradePlace()+" }";
    }
}

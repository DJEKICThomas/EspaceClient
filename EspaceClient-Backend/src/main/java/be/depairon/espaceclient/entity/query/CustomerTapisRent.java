package be.depairon.espaceclient.entity.query;

public class CustomerTapisRent {

    private int nda;
    private String ncs;
    private String cusName;
    private int quantity;
    private String art;
    private String artDes;

    public int getNda() {
        return nda;
    }

    public void setNda(int nda) {
        this.nda = nda;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getArtDes() {
        return artDes;
    }

    public void setArtDes(String artDes) {
        this.artDes = artDes;
    }
}

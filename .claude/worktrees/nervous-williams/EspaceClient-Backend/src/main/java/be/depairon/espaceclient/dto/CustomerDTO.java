package be.depairon.espaceclient.dto;

public class CustomerDTO {

    private long id;
    private String cusNcs;
    private int cusNda;
    private String cusNm1;
    private String cusNm2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCusNcs() {
        return cusNcs;
    }

    public void setCusNcs(String cusNcs) {
        this.cusNcs = cusNcs;
    }

    public int getCusNda() {
        return cusNda;
    }

    public void setCusNda(int cusNda) {
        this.cusNda = cusNda;
    }

    public String getCusNm1() {
        return cusNm1;
    }

    public void setCusNm1(String cusNm1) {
        this.cusNm1 = cusNm1;
    }

    public String getCusNm2() {
        return cusNm2;
    }

    public void setCusNm2(String cusNm2) {
        this.cusNm2 = cusNm2;
    }
}

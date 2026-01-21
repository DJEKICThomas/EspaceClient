package be.depairon.espaceclient.dto;

public class FilterDTO {

    private Integer site;
    private String activity;
    private String carrier;
    private String article;
    private Integer cupboard;
    private Integer cassette;
    private FilterDateDTO date;

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Integer getCupboard() {
        return cupboard;
    }

    public void setCupboard(Integer cupboard) {
        this.cupboard = cupboard;
    }

    public Integer getCassette() {
        return cassette;
    }

    public void setCassette(Integer cassette) {
        this.cassette = cassette;
    }

    public FilterDateDTO getDate() {
        return date;
    }

    public void setDate(FilterDateDTO date) {
        this.date = date;
    }
}

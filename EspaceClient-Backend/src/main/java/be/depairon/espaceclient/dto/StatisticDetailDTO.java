package be.depairon.espaceclient.dto;

public class StatisticDetailDTO {

    private int year1Count;
    private int year2Count;
    private int year3Count;
    private String month;

    public int getYear1Count() {
        return year1Count;
    }

    public void setYear1Count(int year1Count) {
        this.year1Count = year1Count;
    }

    public int getYear2Count() {
        return year2Count;
    }

    public void setYear2Count(int year2Count) {
        this.year2Count = year2Count;
    }

    public int getYear3Count() {
        return year3Count;
    }

    public void setYear3Count(int year3Count) {
        this.year3Count = year3Count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}

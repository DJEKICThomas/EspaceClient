package be.depairon.espaceclient.entity.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticHeader {
    private long ncsId;
    private long artId;
    private String site;
    private String art_des;
    private long sum_year1;
    private long sum_year2;
    private long sum_year3;

    public StatisticHeader(long ncsId, long artId, String site, String art_des, long sum_year1, long sum_year2, long sum_year3) {
        this.ncsId = ncsId;
        this.artId = artId;
        this.site = site;
        this.art_des = art_des;
        this.sum_year1 = sum_year1;
        this.sum_year2 = sum_year2;
        this.sum_year3 = sum_year3;
    }
}
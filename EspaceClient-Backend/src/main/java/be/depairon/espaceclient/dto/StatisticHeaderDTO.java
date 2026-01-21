package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticHeaderDTO {
    private Long ncsId;
    private Long artId;
    private String site;
    private String artDes;
    private int year1;
    private Long sumYear1;
    private int year2;
    private Long sumYear2;
    private int year3;
    private Long sumYear3;
}

package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.enumeration.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetailGestDTO {

    private long id;
    private int cupboard;
    private int cassette;
    private String carrier;
    private String site;
    private String texte;
    private String rep;
    private RequestType typ;
    private String typDes;
    private String textGes;
    private LocalDate date;
    private String demandeur;
    private String barcodeList;
    private String ticket;
    private String statusDes;
    private String cusNcs;
    private Integer cusNda;
}

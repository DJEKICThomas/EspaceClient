package be.depairon.espaceclient.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class DotationDTO {

    private Long id;
    private String client;
    private int adresse;
    private String nomClient;
    private long barcode;
    private String porteur;
    private int armoire;
    private int cassette;
    private String nature;
    private String article;
    private String description;
    private String miseService;
    private Timestamp dernierPassage;
    private String stop;
    private Date dateIn;
    private Date dateOUT;
    private String displayDate;
}

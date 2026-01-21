package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BarcodeStopDTO {

    private long id;
    private String barcode;
    private String stop;
    private String rem;
    private String article;
    private String porteur;
    private String persvet;
    private LocalDate dtb;
    private LocalDate dtf;
    private LocalDate dtr;
    private String usc;
    private String site;
}

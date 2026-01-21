package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NettoyageDTO {

    private long id;
    private String site;
    private String carrier;
    private LocalDate date;
    private int cupboard;
    private int cassette;
    private long barcode;
    private String artDes;
    private String nature;
    private String name;
}

package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InfocentreDTO {

    private long id;
    private String ncs;
    private String info;
    private String reference;
    private LocalDate dateDoc;
}
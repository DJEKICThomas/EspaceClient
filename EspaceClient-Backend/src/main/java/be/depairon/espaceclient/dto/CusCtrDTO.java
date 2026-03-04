package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CusCtrDTO {

    private Long id;
    private String ncs;
    private String reference;
    private String nature;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}

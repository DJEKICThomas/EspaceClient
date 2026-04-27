package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DsHeadDTO {

    private long id;
    private String com;
    private String ncs;
    private String doc;
    private Integer slv;
}

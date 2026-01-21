package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewRequestDetailDTO {
    private long nominId;
    private String artDes;
    private int qty;
    private String nature;
}

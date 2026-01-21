package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewRequestDTO {
    private String site;
    private String carrier;
    private String carrierName;
    private String cupboard;
    private String cassette;
    private long ncsId;
    List<NewRequestDetailDTO> newRequestDetailDTOList;
}

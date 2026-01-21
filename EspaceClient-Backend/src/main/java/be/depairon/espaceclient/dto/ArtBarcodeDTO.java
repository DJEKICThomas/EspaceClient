package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArtBarcodeDTO {
    private Long id;
    private Integer artIin;
    private String description;
    private List<BarcodeDTO> barcodeList;
}

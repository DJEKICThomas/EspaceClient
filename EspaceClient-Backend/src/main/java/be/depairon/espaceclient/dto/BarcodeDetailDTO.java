package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarcodeDetailDTO {
    private long id;
    private String barcode;
    private long porteurId;
    private ArtBarcodeDTO artBarcodeDTO;
}

package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.entity.Barcode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarcodeDTO {
    private long id;
    private String barcode;
    private long porteurId;
    private ArtDTO artDTO;

    public Barcode convert() {
        Barcode barcode = new Barcode();
        barcode.setId(this.id);
        try {
            barcode.setBarcode(Long.parseLong(this.barcode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return barcode;
    }
}

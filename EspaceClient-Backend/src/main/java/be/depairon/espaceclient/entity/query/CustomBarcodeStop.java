package be.depairon.espaceclient.entity.query;

import be.depairon.espaceclient.entity.BarcodeStop;
import be.depairon.espaceclient.entity.Stops;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomBarcodeStop {
    private BarcodeStop bs;
    private Stops stops;
    private String site;

    public CustomBarcodeStop(BarcodeStop bs, Stops stops, String site) {
        this.bs = bs;
        this.stops = stops;
        this.site = site;
    }
}

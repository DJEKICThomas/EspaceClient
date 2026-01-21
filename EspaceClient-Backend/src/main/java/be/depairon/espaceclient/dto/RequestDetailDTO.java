package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.entity.RequestDetail;
import be.depairon.espaceclient.enumeration.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.*;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Getter
@Setter
@NoArgsConstructor
public class RequestDetailDTO implements Serializable {
    private long id;
    private String text;
    private String typ;
    private LocalDate returnDate;
    private List<BarcodeDTO> barcodeList;
    private ArtDTO articleDTO;

    public RequestDetail convert() {
        RequestDetail requestDetail = new RequestDetail();
        requestDetail.setText(this.text);
        try {
            requestDetail.setTyp(RequestType.valueOf(this.typ));
        } catch (Exception e) {
            e.printStackTrace();
            requestDetail.setTyp(RequestType.UNKNOWN);
        }

        if (this.returnDate != null) {
            this.returnDate = this.returnDate.plusDays(1);
        }
        requestDetail.setReturndate(this.returnDate);

        return requestDetail;
    }
}

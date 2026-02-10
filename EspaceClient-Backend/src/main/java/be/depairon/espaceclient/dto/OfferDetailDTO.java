package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OfferDetailDTO {

    private String article;
    private String articleLab;
    private BigDecimal quantity;
    private BigDecimal priceUnit;
    private BigDecimal lineTotal; //total de la ligne
}

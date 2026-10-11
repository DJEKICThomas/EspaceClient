package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ScmAllDocDetDTO {

    private String article;
    private String articleLab;
    private BigDecimal quantity;
    private BigDecimal priceUnit;
    private BigDecimal lineTotal;
}

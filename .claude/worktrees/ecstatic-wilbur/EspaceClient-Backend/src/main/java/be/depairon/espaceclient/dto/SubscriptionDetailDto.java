package be.depairon.espaceclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDetailDto {

    private String articleCode;   // DOC_DET_ART
    private String description;   // DOC_DET_LIB
    private BigDecimal price;     // DOC_DET_PRC_NET
    private BigDecimal quantity;  // DOC_DET_QWH
    private LocalDate startDate;     // DOC_DET_DDT (formaté en dd/MM/yyyy)
    private LocalDate nextDueDate;   // DOC_DET_RDT (formaté en dd/MM/yyyy)

    private String status;        // Valeurs possibles : "Actif", "Périmé", "Bientôt expiré"
}
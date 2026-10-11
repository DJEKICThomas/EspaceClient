package be.depairon.espaceclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTotalDTO {

    private String month;           // Format "YYYY-MM"
    private BigDecimal ordersTotal;  // Montant total commandes
    private BigDecimal invoicesTotal; // Montant total factures
}

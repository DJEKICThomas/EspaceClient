package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ScmAllDocDTO {

    private String document;
    private String nda;
    private String ncs;
    private String docDes;
    private String reference;
    private LocalDate docCreation;
    private BigDecimal montantTotal;
    private String statut;
    private String fournisseur;

    private String typeDoc = "Offre";
}

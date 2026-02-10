package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "SCMALLDOC", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class ScmAllDoc implements Serializable {

    @Id
    @Column(name = "DOCUMENT")
    private String document;

    @Column(name = "TYPE_DOC")
    private String typeDoc;

    @Column(name = "DOC_TYC")
    private String docTyc;

    @Column(name = "NDA")
    private String nda;

    @Column(name = "NCS")
    private String ncs;

    @Column(name = "DOC_DES")
    private String docDes;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "DATE_CREATION")
    private int dateCreation;

    @Column(name = "MONTANT_TOTAL")
    private BigDecimal montantTotal;

    @Column(name = "FOURNISSEUR")
    private String fournisseur;

    @Column(name = "STATUT")
    private String statut;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScmAllDoc that = (ScmAllDoc) o;
        return Objects.equals(document, that.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }
}
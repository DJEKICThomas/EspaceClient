package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "DOC_DET", schema = "YG51TT")
@Getter
@Setter
@NoArgsConstructor
public class DocDet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "DOC_DET_DOK")
    private String docDetDok; // Lien avec l'abonnement (N° Document)

    @Column(name = "DOC_DET_IIN")
    private String docDetIin; // Code Article

    @Column(name = "DOC_DET_LN0")
    private String docDetLn0; // Description / Libellé

    @Column(name = "DOC_DET_PRC_NET")
    private BigDecimal docDetPrcNet; // Prix Net

    @Column(name = "DOC_DET_QWH")
    private BigDecimal docDetQwh; // Quantité

    @Column(name = "DOC_DET_RDT")
    private int docDetRdt; // Prochaine échéance (YYYYMMDD)

    @Column(name = "DOC_DET_DTC")
    private int docDetDtc; // Début contrat (YYYYMMDD)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocDet docDet = (DocDet) o;
        return id == docDet.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
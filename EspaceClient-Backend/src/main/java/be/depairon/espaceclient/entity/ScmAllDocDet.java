package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "SCMALLDOCDET", schema = "YGS01")
@Getter
@Setter
@NoArgsConstructor
public class ScmAllDocDet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "DOCUMENT")
    private String document; // Clé de liaison avec ScmAllDoc

    @Column(name = "ARTICLE")
    private String article;

    @Column(name = "ARTICLE_LAB")
    private String articleLab;

    @Column(name = "QUANTITE_TOTAL")
    private BigDecimal quantiteTotal;

    @Column(name = "PRICE_UNIT")
    private BigDecimal priceUnit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScmAllDocDet that = (ScmAllDocDet) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
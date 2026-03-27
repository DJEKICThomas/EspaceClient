package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "FMOV", schema = "YG51TT")
@Getter
@Setter
@NoArgsConstructor
public class Fmov implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "FMOV_TYP")
    private String fmovTyp;

    @Column(name = "FMOV_NCS")
    private String fmovNcs;

    @Column(name = "FMOV_CCYY")
    private int fmovCcyy;

    @Column(name = "FMOV_PER")
    private int fmovPer;

    @Column(name = "FMOV_DAT_IMP")
    private int fmovDatImp;

    @Column(name = "FMOV_NUM")
    private String fmovNum;

    @Column(name = "FMOV_ACC")
    private String fmovAcc;

    @Column(name = "FMOV_LET")
    private String fmovLet;

    @Column(name = "FMOV_VAL_CUR")
    private BigDecimal fmovValCur;

    @Column(name = "FMOV_CUR")
    private String fmovCur;

    @Column(name = "FMOV_DC")
    private String fmovDc;

    @Column(name = "FMOV_VAL_LOC")
    private BigDecimal fmovValLoc;

    @Column(name = "FMOV_CUR_RAT")
    private BigDecimal fmovCurRat;

    @Column(name = "FMOV_DES")
    private String fmovDes;

    @Column(name = "FMOV_DAT_DUE")
    private int fmovDatDue;

    @Column(name = "FMOV_DAT_DOC")
    private int fmovDatDoc;

    @Column(name = "FMOV_REF")
    private String fmovRef;

    @Column(name = "FMOV_JNL")
    private String fmovJnl;

    @Column(name = "FMOV_ANA1") private String fmovAna1;
    @Column(name = "FMOV_ANA2") private String fmovAna2;
    @Column(name = "FMOV_ANA3") private String fmovAna3;
    @Column(name = "FMOV_ANA4") private String fmovAna4;
    @Column(name = "FMOV_ANA5") private String fmovAna5;
    @Column(name = "FMOV_ANA6") private String fmovAna6;
    @Column(name = "FMOV_ANA7") private String fmovAna7;
    @Column(name = "FMOV_ANA8") private String fmovAna8;
    @Column(name = "FMOV_ANA9") private String fmovAna9;

    @Column(name = "FMOV_QT1") private BigDecimal fmovQt1;
    @Column(name = "FMOV_QT2") private BigDecimal fmovQt2;

    @Column(name = "FMOV_DAT_REF")
    private int fmovDatRef;

    @Column(name = "FMOV_NAT")
    private String fmovNat;

    @Column(name = "FMOV_DAT_LET")
    private int fmovDatLet;

    @Column(name = "FMOV_REC")
    private String fmovRec;

    @Column(name = "FMOV_TYP2")
    private String fmovTyp2;

    @Column(name = "FMOV_NCS2")
    private String fmovNcs2;

    @Column(name = "FMOV_FNC")
    private String fmovFnc;

    @Column(name = "FMOV_AL2")
    private String fmovAl2;

    @Column(name = "FMOV_PAY")
    private String fmovPay;

    @Column(name = "FMOV_USC")
    private String fmovUsc;

    @Column(name = "FMOV_NATDOC")
    private String fmovNatdoc;

    @Column(name = "FMOV_VTB")
    private String fmovVtb;

    @Column(name = "FMOV_REG")
    private String fmovReg;

    // On garde equals/hashCode à la main (souvent plus sûr pour JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fmov fmove = (Fmov) o;
        return id == fmove.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "CUS_SAL", schema = "YG51TT")
@Getter
@Setter
@NoArgsConstructor
public class CusSal implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "CUS_SAL_NCS")
    private String cusSalNcs;

    @Column(name = "CUS_SAL_NDA")
    private int cusSalNda;

    @Column(name = "CUS_SAL_TYP")
    private String cusSalTyp;

    @Column(name = "CUS_SAL_COM")
    private String cusSalCom;

    @Column(name = "CUS_SAL_DEP")
    private String cusSalDep;

    @Column(name = "CUS_SAL_CNA")
    private String cusSalCna;

    @Column(name = "CUS_SAL_CSE")
    private String cusSalCse;

    @Column(name = "CUS_SAL_CAC")
    private String cusSalCac;

    @Column(name = "CUS_SAL_EXC")
    private String cusSalExc;

    @Column(name = "CUS_SAL_REP")
    private String cusSalRep;

    @Column(name = "CUS_SAL_CFR")
    private String cusSalCfr;

    @Column(name = "CUS_SAL_TAR")
    private String cusSalTar;

    @Column(name = "CUS_SAL_DIN")
    private String cusSalDin;

    @Column(name = "CUS_SAL_DIG")
    private String cusSalDig;

    @Column(name = "CUS_SAL_DIO")
    private String cusSalDio;

    @Column(name = "CUS_SAL_DCW")
    private String cusSalDcw;

    @Column(name = "CUS_SAL_WHT")
    private String cusSalWht;

    @Column(name = "CUS_SAL_WHG")
    private String cusSalWhg;

    @Column(name = "CUS_SAL_DUTY")
    private BigInteger cusSalDuty;

    @Column(name = "CUS_SAL_INH")
    private String cusSalInh;

    @Column(name = "CUS_SAL_INT")
    private String cusSalInt;

    @Column(name = "CUS_SAL_INN")
    private BigInteger cusSalInn;

    @Column(name = "CUS_SAL_INP")
    private String cusSalInp;

    @Column(name = "CUS_SAL_DTM")
    private int cusSalDtm;

    @Column(name = "CUS_SAL_USM")
    private String cusSalUsm;

    @Column(name = "CUS_SAL_DTC")
    private int cusSalDtc;

    @Column(name = "CUS_SAL_USC")
    private String cusSalUsc;

    @Column(name = "CUS_SAL_TARRENT")
    private String cusSalTarrent;

    @Column(name = "CUS_SAL_DTYC")
    private String cusSalDtyc;

    @Column(name = "CUS_SAL_DTYR")
    private String cusSalDtyr;

    @Column(name = "CUS_SAL_DTYS")
    private String cusSalDtys;

    @Column(name = "CUS_SAL_DTYU")
    private String cusSalDtyu;

    @Column(name = "CUS_SAL_IBTW")
    private String cusSalIbtw;

    @Column(name = "CUS_SAL_CAT")
    private String cusSalCat;

    @Column(name = "CUS_SAL_BMA")
    private String cusSalBma;

    @Column(name = "CUS_SAL_HCD")
    private int cusSalHcd;

    @Column(name = "CUS_SAL_HCT")
    private int cusSalHct;

    @Column(name = "CUS_SAL_RES")
    private String cusSalRes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CusSal cusSal = (CusSal) o;
        return id == cusSal.id && cusSalNda == cusSal.cusSalNda && cusSalDtm == cusSal.cusSalDtm && cusSalDtc == cusSal.cusSalDtc && cusSalHcd == cusSal.cusSalHcd && cusSalHct == cusSal.cusSalHct && Objects.equals(cusSalNcs, cusSal.cusSalNcs) && Objects.equals(cusSalTyp, cusSal.cusSalTyp) && Objects.equals(cusSalCom, cusSal.cusSalCom) && Objects.equals(cusSalDep, cusSal.cusSalDep) && Objects.equals(cusSalCna, cusSal.cusSalCna) && Objects.equals(cusSalCse, cusSal.cusSalCse) && Objects.equals(cusSalCac, cusSal.cusSalCac) && Objects.equals(cusSalExc, cusSal.cusSalExc) && Objects.equals(cusSalRep, cusSal.cusSalRep) && Objects.equals(cusSalCfr, cusSal.cusSalCfr) && Objects.equals(cusSalTar, cusSal.cusSalTar) && Objects.equals(cusSalDin, cusSal.cusSalDin) && Objects.equals(cusSalDig, cusSal.cusSalDig) && Objects.equals(cusSalDio, cusSal.cusSalDio) && Objects.equals(cusSalDcw, cusSal.cusSalDcw) && Objects.equals(cusSalWht, cusSal.cusSalWht) && Objects.equals(cusSalWhg, cusSal.cusSalWhg) && Objects.equals(cusSalDuty, cusSal.cusSalDuty) && Objects.equals(cusSalInh, cusSal.cusSalInh) && Objects.equals(cusSalInt, cusSal.cusSalInt) && Objects.equals(cusSalInn, cusSal.cusSalInn) && Objects.equals(cusSalInp, cusSal.cusSalInp) && Objects.equals(cusSalUsm, cusSal.cusSalUsm) && Objects.equals(cusSalUsc, cusSal.cusSalUsc) && Objects.equals(cusSalTarrent, cusSal.cusSalTarrent) && Objects.equals(cusSalDtyc, cusSal.cusSalDtyc) && Objects.equals(cusSalDtyr, cusSal.cusSalDtyr) && Objects.equals(cusSalDtys, cusSal.cusSalDtys) && Objects.equals(cusSalDtyu, cusSal.cusSalDtyu) && Objects.equals(cusSalIbtw, cusSal.cusSalIbtw) && Objects.equals(cusSalCat, cusSal.cusSalCat) && Objects.equals(cusSalBma, cusSal.cusSalBma) && Objects.equals(cusSalRes, cusSal.cusSalRes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cusSalNcs, cusSalNda, cusSalTyp, cusSalCom, cusSalDep, cusSalCna, cusSalCse, cusSalCac, cusSalExc, cusSalRep, cusSalCfr, cusSalTar, cusSalDin, cusSalDig, cusSalDio, cusSalDcw, cusSalWht, cusSalWhg, cusSalDuty, cusSalInh, cusSalInt, cusSalInn, cusSalInp, cusSalDtm, cusSalUsm, cusSalDtc, cusSalUsc, cusSalTarrent, cusSalDtyc, cusSalDtyr, cusSalDtys, cusSalDtyu, cusSalIbtw, cusSalCat, cusSalBma, cusSalHcd, cusSalHct, cusSalRes);
    }
}

package be.depairon.espaceclient.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@Table(name = "CUS_XDC", schema = "YGS01")
public class CusXdc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "CUS_XDC_NCS")
    private String cusXdcNcs;

    @Column(name = "CUS_XDC_NDA")
    private Integer cusXdcNda;

    @Column(name = "CUS_XDC_COM")
    private String cusXdcCom;

    @Column(name = "CUS_XDC_DEP")
    private String cusXdcDep;

    @Column(name = "CUS_XDC_FRE")
    private String cusXdcFre;

    @Column(name = "CUS_XDC_RNE")
    private String cusXdcRne;

    @Column(name = "CUS_XDC_MOE")
    private String cusXdcMoe;

    @Column(name = "CUS_XDC_COT")
    private String cusXdcCot;

    @Column(name = "CUS_XDC_IPX")
    private BigDecimal cusXdcIpx;

    @Column(name = "CUS_XDC_EBL")
    private Date cusXdcEbl;

    @Column(name = "CUS_XDC_SPL")
    private BigInteger cusXdcSpl;

    @Column(name = "CUS_XDC_T1")
    private String cusXdcT1;

    @Column(name = "CUS_XDC_T2")
    private String cusXdcT2;

    @Column(name = "CUS_XDC_T3")
    private String cusXdcT3;

    @Column(name = "CUS_XDC_T4")
    private String cusXdcT4;

    @Column(name = "CUS_XDC_T5")
    private String cusXdcT5;

    @Column(name = "CUS_XDC_T6")
    private String cusXdcT6;

    @Column(name = "CUS_XDC_TS1")
    private String cusXdcTs1;

    @Column(name = "CUS_XDC_TS2")
    private String cusXdcTs2;

    @Column(name = "CUS_XDC_TS3")
    private String cusXdcTs3;

    @Column(name = "CUS_XDC_TS4")
    private String cusXdcTs4;

    @Column(name = "CUS_XDC_TS5")
    private String cusXdcTs5;

    @Column(name = "CUS_XDC_TS6")
    private String cusXdcTs6;

    @Column(name = "CUS_XDC_TF1")
    private String cusXdcTf1;

    @Column(name = "CUS_XDC_TF2")
    private String cusXdcTf2;

    @Column(name = "CUS_XDC_TF3")
    private String cusXdcTf3;

    @Column(name = "CUS_XDC_TF4")
    private String cusXdcTf4;

    @Column(name = "CUS_XDC_TF5")
    private String cusXdcTf5;

    @Column(name = "CUS_XDC_TF6")
    private String cusXdcTf6;

    @Column(name = "CUS_XDC_TO1")
    private String cusXdcTo1;

    @Column(name = "CUS_XDC_TO2")
    private String cusXdcTo2;

    @Column(name = "CUS_XDC_TO3")
    private String cusXdcTo3;

    @Column(name = "CUS_XDC_TO4")
    private String cusXdcTo4;

    @Column(name = "CUS_XDC_TO5")
    private String cusXdcTo5;

    @Column(name = "CUS_XDC_TO6")
    private String cusXdcTo6;

    @Column(name = "CUS_XDC_TT1")
    private String cusXdcTt1;

    @Column(name = "CUS_XDC_TT2")
    private String cusXdcTt2;

    @Column(name = "CUS_XDC_TT3")
    private String cusXdcTt3;

    @Column(name = "CUS_XDC_TT4")
    private String cusXdcTt4;

    @Column(name = "CUS_XDC_TT5")
    private String cusXdcTt5;

    @Column(name = "CUS_XDC_TT6")
    private String cusXdcTt6;

    @Column(name = "CUS_XDC_LJA")
    private String cusXdcLja;

    @Column(name = "CUS_XDC_TFO")
    private String cusXdcTfo;

    @Column(name = "CUS_XDC_INC")
    private String cusXdcInc;

    @Column(name = "CUS_XDC_PLA")
    private String cusXdcPla;

    @Column(name = "CUS_XDC_CT_LOC")
    private String cusXdcCtLoc;

    @Column(name = "CUS_XDC_CT_DDE")
    private Date cusXdcCtDde;

    @Column(name = "CUS_XDC_CT_DFI")
    private Date cusXdcCtDfi;

    @Column(name = "CUS_XDC_CT_DBL")
    private Date cusXdcCtDbl;

    @Column(name = "CUS_XDC_CT_DIVI")
    private int cusXdcCtDivi;

    @Column(name = "CUS_XDC_CT_BINO")
    private String cusXdcCtBino;

    @Column(name = "CUS_XDC_CT_DUR")
    private int cusXdcCtDur;

    @Column(name = "CUS_XDC_CT_IND1")
    private BigDecimal cusXdcCtInd1;

    @Column(name = "CUS_XDC_CT_IND2")
    private BigDecimal cusXdcCtInd2;

    @Column(name = "CUS_XDC_CT_IND3")
    private BigDecimal cusXdcCtInd3;

    @Column(name = "CUS_XDC_CT_IND4")
    private BigDecimal cusXdcCtInd4;

    @Column(name = "CUS_XDC_CT_IND5")
    private BigDecimal cusXdcCtInd5;

    @Column(name = "CUS_XDC_GEO")
    private BigInteger cusXdcGeo;

    @Column(name = "CUS_XDC_LAT")
    private String cusXdcLat;

    @Column(name = "CUS_XDC_LON")
    private String cusXdcLon;

    @Column(name = "CUS_XDC_TAL")
    private String cusXdcTal;

    @Column(name = "CUS_XDC_TTR")
    private String cusXdcTtr;

    @Column(name = "CUS_XDC_FACL")
    private BigInteger cusXdcFacl;

    @Column(name = "CUS_XDC_PRLL")
    private String cusXdcPrll;

    @Column(name = "CUS_XDC_FREVAL")
    private BigDecimal cusXdcFreval;

    @Column(name = "CUS_XDC_LU01DE")
    private int cusXdcLu01De;

    @Column(name = "CUS_XDC_LU01A")
    private int cusXdcLu01A;

    @Column(name = "CUS_XDC_LU02DE")
    private int cusXdcLu02De;

    @Column(name = "CUS_XDC_LU02A")
    private int cusXdcLu02A;

    @Column(name = "CUS_XDC_LU03DE")
    private int cusXdcLu03De;

    @Column(name = "CUS_XDC_LU03A")
    private int cusXdcLu03A;

    @Column(name = "CUS_XDC_LU04DE")
    private int cusXdcLu04De;

    @Column(name = "CUS_XDC_LU04A")
    private int cusXdcLu04A;

    @Column(name = "CUS_XDC_MA01DE")
    private int cusXdcMa01De;

    @Column(name = "CUS_XDC_MA01A")
    private int cusXdcMa01A;

    @Column(name = "CUS_XDC_MA02DE")
    private int cusXdcMa02De;

    @Column(name = "CUS_XDC_MA02A")
    private int cusXdcMa02A;

    @Column(name = "CUS_XDC_MA03DE")
    private int cusXdcMa03De;

    @Column(name = "CUS_XDC_MA03A")
    private int cusXdcMa03A;

    @Column(name = "CUS_XDC_MA04DE")
    private int cusXdcMa04De;

    @Column(name = "CUS_XDC_MA04A")
    private int cusXdcMa04A;

    @Column(name = "CUS_XDC_ME01DE")
    private int cusXdcMe01De;

    @Column(name = "CUS_XDC_ME01A")
    private int cusXdcMe01A;

    @Column(name = "CUS_XDC_ME02DE")
    private int cusXdcMe02De;

    @Column(name = "CUS_XDC_ME02A")
    private int cusXdcMe02A;

    @Column(name = "CUS_XDC_ME03DE")
    private int cusXdcMe03De;

    @Column(name = "CUS_XDC_ME03A")
    private int cusXdcMe03A;

    @Column(name = "CUS_XDC_ME04DE")
    private int cusXdcMe04De;

    @Column(name = "CUS_XDC_ME04A")
    private int cusXdcMe04A;

    @Column(name = "CUS_XDC_JE01DE")
    private int cusXdcJe01De;

    @Column(name = "CUS_XDC_JE01A")
    private int cusXdcJe01A;

    @Column(name = "CUS_XDC_JE02DE")
    private int cusXdcJe02De;

    @Column(name = "CUS_XDC_JE02A")
    private int cusXdcJe02A;

    @Column(name = "CUS_XDC_JE03DE")
    private int cusXdcJe03De;

    @Column(name = "CUS_XDC_JE03A")
    private int cusXdcJe03A;

    @Column(name = "CUS_XDC_JE04DE")
    private int cusXdcJe04De;

    @Column(name = "CUS_XDC_JE04A")
    private int cusXdcJe04A;

    @Column(name = "CUS_XDC_VE01DE")
    private int cusXdcVe01De;

    @Column(name = "CUS_XDC_VE01A")
    private int cusXdcVe01A;

    @Column(name = "CUS_XDC_VE02DE")
    private int cusXdcVe02De;

    @Column(name = "CUS_XDC_VE02A")
    private int cusXdcVe02A;

    @Column(name = "CUS_XDC_VE03DE")
    private int cusXdcVe03De;

    @Column(name = "CUS_XDC_VE03A")
    private int cusXdcVe03A;

    @Column(name = "CUS_XDC_VE04DE")
    private int cusXdcVe04De;

    @Column(name = "CUS_XDC_VE04A")
    private int cusXdcVe04A;

    @Column(name = "CUS_XDC_SA01DE")
    private int cusXdcSa01De;

    @Column(name = "CUS_XDC_SA01A")
    private int cusXdcSa01A;

    @Column(name = "CUS_XDC_SA02DE")
    private int cusXdcSa02De;

    @Column(name = "CUS_XDC_SA02A")
    private int cusXdcSa02A;

    @Column(name = "CUS_XDC_SA03DE")
    private int cusXdcSa03De;

    @Column(name = "CUS_XDC_SA03A")
    private int cusXdcSa03A;

    @Column(name = "CUS_XDC_SA04DE")
    private int cusXdcSa04De;

    @Column(name = "CUS_XDC_SA04A")
    private int cusXdcSa04A;

    @Column(name = "CUS_XDC_DI01DE")
    private int cusXdcDi01De;

    @Column(name = "CUS_XDC_DI01A")
    private int cusXdcDi01A;

    @Column(name = "CUS_XDC_DI02DE")
    private int cusXdcDi02De;

    @Column(name = "CUS_XDC_DI02A")
    private int cusXdcDi02A;

    @Column(name = "CUS_XDC_DI03DE")
    private int cusXdcDi03De;

    @Column(name = "CUS_XDC_DI03A")
    private int cusXdcDi03A;

    @Column(name = "CUS_XDC_DI04DE")
    private int cusXdcDi04De;

    @Column(name = "CUS_XDC_DI04A")
    private int cusXdcDi04A;

    @Column(name = "CUS_XDC_FREHYGVAL")
    private BigDecimal cusXdcFrehygval;

    @Column(name = "CUS_XDC_PCG")
    private String cusXdcPcg;

    @Column(name = "CUS_XDC_PERS")
    private int cusXdcPers;

    @Column(name = "CUS_XDC_NUMVET")
    private String cusXdcNumvet;

    @Column(name = "CUS_XDC_ANC")
    private int cusXdcAnc;

    @Column(name = "CUS_XDC_NBEMP")
    private int cusXdcNbemp;

    @Column(name = "CUS_XDC_PRREMP")
    private int cusXdcPrremp;

    @Column(name = "CUS_XDC_DEEBL")
    private Date cusXdcDeebl;

    @Column(name = "CUS_XDC_CT_DEDBL")
    private Date cusXdcCtDedbl;

    @Column(name = "CUS_XDC_CSC")
    private String cusXdcCsc;

    @Column(name = "CUS_XDC_NEONFAC")
    private String cusXdcNeonfac;

    @Column(name = "CUS_XDC_PRREMPA")
    private String cusXdcPrrempa;

    @Column(name = "CUS_XDC_PRREMN")
    private String cusXdcPrremn;

    @Column(name = "CUS_XDC_EBLTXT")
    private String cusXdcEbltxt;

    @Column(name = "CUS_XDC_CT_DBLTXT")
    private String cusXdcCtDbltxt;

    @Column(name = "CUS_XDC_PEETK")
    private String cusXdcPeetk;

    @Column(name = "CUS_XDC_REFOBL")
    private String cusXdcRefobl;

    @Column(name = "CUS_XDC_BLV")
    private String cusXdcBlv;

    @Column(name = "CUS_XDC_DBLV")
    private int cusXdcDblv;

    @Column(name = "CUS_XDC_DBLVTXT")
    private String cusXdcDblvtxt;

    @Column(name = "CUS_XDC_CLEX")
    private String cusXdcClex;

    @Column(name = "CUS_XDC_ENTWEB")
    private String cusXdcEntweb;

    @Column(name = "CUS_XDC_NEONENT")
    private String cusXdcNeonent;

    @Column(name = "CUS_MISE_EN_SERVICE")
    private String cusMiseEnService;

    @Column(name = "CUS_XDC_HIS")
    private BigInteger cusXdcHis;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CusXdc cusXdc = (CusXdc) o;
        return id == cusXdc.id && cusXdcCtDivi == cusXdc.cusXdcCtDivi && cusXdcCtDur == cusXdc.cusXdcCtDur && cusXdcLu01De == cusXdc.cusXdcLu01De && cusXdcLu01A == cusXdc.cusXdcLu01A && cusXdcLu02De == cusXdc.cusXdcLu02De && cusXdcLu02A == cusXdc.cusXdcLu02A && cusXdcLu03De == cusXdc.cusXdcLu03De && cusXdcLu03A == cusXdc.cusXdcLu03A && cusXdcLu04De == cusXdc.cusXdcLu04De && cusXdcLu04A == cusXdc.cusXdcLu04A && cusXdcMa01De == cusXdc.cusXdcMa01De && cusXdcMa01A == cusXdc.cusXdcMa01A && cusXdcMa02De == cusXdc.cusXdcMa02De && cusXdcMa02A == cusXdc.cusXdcMa02A && cusXdcMa03De == cusXdc.cusXdcMa03De && cusXdcMa03A == cusXdc.cusXdcMa03A && cusXdcMa04De == cusXdc.cusXdcMa04De && cusXdcMa04A == cusXdc.cusXdcMa04A && cusXdcMe01De == cusXdc.cusXdcMe01De && cusXdcMe01A == cusXdc.cusXdcMe01A && cusXdcMe02De == cusXdc.cusXdcMe02De && cusXdcMe02A == cusXdc.cusXdcMe02A && cusXdcMe03De == cusXdc.cusXdcMe03De && cusXdcMe03A == cusXdc.cusXdcMe03A && cusXdcMe04De == cusXdc.cusXdcMe04De && cusXdcMe04A == cusXdc.cusXdcMe04A && cusXdcJe01De == cusXdc.cusXdcJe01De && cusXdcJe01A == cusXdc.cusXdcJe01A && cusXdcJe02De == cusXdc.cusXdcJe02De && cusXdcJe02A == cusXdc.cusXdcJe02A && cusXdcJe03De == cusXdc.cusXdcJe03De && cusXdcJe03A == cusXdc.cusXdcJe03A && cusXdcJe04De == cusXdc.cusXdcJe04De && cusXdcJe04A == cusXdc.cusXdcJe04A && cusXdcVe01De == cusXdc.cusXdcVe01De && cusXdcVe01A == cusXdc.cusXdcVe01A && cusXdcVe02De == cusXdc.cusXdcVe02De && cusXdcVe02A == cusXdc.cusXdcVe02A && cusXdcVe03De == cusXdc.cusXdcVe03De && cusXdcVe03A == cusXdc.cusXdcVe03A && cusXdcVe04De == cusXdc.cusXdcVe04De && cusXdcVe04A == cusXdc.cusXdcVe04A && cusXdcSa01De == cusXdc.cusXdcSa01De && cusXdcSa01A == cusXdc.cusXdcSa01A && cusXdcSa02De == cusXdc.cusXdcSa02De && cusXdcSa02A == cusXdc.cusXdcSa02A && cusXdcSa03De == cusXdc.cusXdcSa03De && cusXdcSa03A == cusXdc.cusXdcSa03A && cusXdcSa04De == cusXdc.cusXdcSa04De && cusXdcSa04A == cusXdc.cusXdcSa04A && cusXdcDi01De == cusXdc.cusXdcDi01De && cusXdcDi01A == cusXdc.cusXdcDi01A && cusXdcDi02De == cusXdc.cusXdcDi02De && cusXdcDi02A == cusXdc.cusXdcDi02A && cusXdcDi03De == cusXdc.cusXdcDi03De && cusXdcDi03A == cusXdc.cusXdcDi03A && cusXdcDi04De == cusXdc.cusXdcDi04De && cusXdcDi04A == cusXdc.cusXdcDi04A && cusXdcPers == cusXdc.cusXdcPers && cusXdcAnc == cusXdc.cusXdcAnc && cusXdcNbemp == cusXdc.cusXdcNbemp && cusXdcPrremp == cusXdc.cusXdcPrremp && cusXdcDblv == cusXdc.cusXdcDblv && Objects.equals(cusXdcNcs, cusXdc.cusXdcNcs) && Objects.equals(cusXdcNda, cusXdc.cusXdcNda) && Objects.equals(cusXdcCom, cusXdc.cusXdcCom) && Objects.equals(cusXdcDep, cusXdc.cusXdcDep) && Objects.equals(cusXdcFre, cusXdc.cusXdcFre) && Objects.equals(cusXdcRne, cusXdc.cusXdcRne) && Objects.equals(cusXdcMoe, cusXdc.cusXdcMoe) && Objects.equals(cusXdcCot, cusXdc.cusXdcCot) && Objects.equals(cusXdcIpx, cusXdc.cusXdcIpx) && Objects.equals(cusXdcEbl, cusXdc.cusXdcEbl) && Objects.equals(cusXdcSpl, cusXdc.cusXdcSpl) && Objects.equals(cusXdcT1, cusXdc.cusXdcT1) && Objects.equals(cusXdcT2, cusXdc.cusXdcT2) && Objects.equals(cusXdcT3, cusXdc.cusXdcT3) && Objects.equals(cusXdcT4, cusXdc.cusXdcT4) && Objects.equals(cusXdcT5, cusXdc.cusXdcT5) && Objects.equals(cusXdcT6, cusXdc.cusXdcT6) && Objects.equals(cusXdcTs1, cusXdc.cusXdcTs1) && Objects.equals(cusXdcTs2, cusXdc.cusXdcTs2) && Objects.equals(cusXdcTs3, cusXdc.cusXdcTs3) && Objects.equals(cusXdcTs4, cusXdc.cusXdcTs4) && Objects.equals(cusXdcTs5, cusXdc.cusXdcTs5) && Objects.equals(cusXdcTs6, cusXdc.cusXdcTs6) && Objects.equals(cusXdcTf1, cusXdc.cusXdcTf1) && Objects.equals(cusXdcTf2, cusXdc.cusXdcTf2) && Objects.equals(cusXdcTf3, cusXdc.cusXdcTf3) && Objects.equals(cusXdcTf4, cusXdc.cusXdcTf4) && Objects.equals(cusXdcTf5, cusXdc.cusXdcTf5) && Objects.equals(cusXdcTf6, cusXdc.cusXdcTf6) && Objects.equals(cusXdcTo1, cusXdc.cusXdcTo1) && Objects.equals(cusXdcTo2, cusXdc.cusXdcTo2) && Objects.equals(cusXdcTo3, cusXdc.cusXdcTo3) && Objects.equals(cusXdcTo4, cusXdc.cusXdcTo4) && Objects.equals(cusXdcTo5, cusXdc.cusXdcTo5) && Objects.equals(cusXdcTo6, cusXdc.cusXdcTo6) && Objects.equals(cusXdcTt1, cusXdc.cusXdcTt1) && Objects.equals(cusXdcTt2, cusXdc.cusXdcTt2) && Objects.equals(cusXdcTt3, cusXdc.cusXdcTt3) && Objects.equals(cusXdcTt4, cusXdc.cusXdcTt4) && Objects.equals(cusXdcTt5, cusXdc.cusXdcTt5) && Objects.equals(cusXdcTt6, cusXdc.cusXdcTt6) && Objects.equals(cusXdcLja, cusXdc.cusXdcLja) && Objects.equals(cusXdcTfo, cusXdc.cusXdcTfo) && Objects.equals(cusXdcInc, cusXdc.cusXdcInc) && Objects.equals(cusXdcPla, cusXdc.cusXdcPla) && Objects.equals(cusXdcCtLoc, cusXdc.cusXdcCtLoc) && Objects.equals(cusXdcCtDde, cusXdc.cusXdcCtDde) && Objects.equals(cusXdcCtDfi, cusXdc.cusXdcCtDfi) && Objects.equals(cusXdcCtDbl, cusXdc.cusXdcCtDbl) && Objects.equals(cusXdcCtBino, cusXdc.cusXdcCtBino) && Objects.equals(cusXdcCtInd1, cusXdc.cusXdcCtInd1) && Objects.equals(cusXdcCtInd2, cusXdc.cusXdcCtInd2) && Objects.equals(cusXdcCtInd3, cusXdc.cusXdcCtInd3) && Objects.equals(cusXdcCtInd4, cusXdc.cusXdcCtInd4) && Objects.equals(cusXdcCtInd5, cusXdc.cusXdcCtInd5) && Objects.equals(cusXdcGeo, cusXdc.cusXdcGeo) && Objects.equals(cusXdcLat, cusXdc.cusXdcLat) && Objects.equals(cusXdcLon, cusXdc.cusXdcLon) && Objects.equals(cusXdcTal, cusXdc.cusXdcTal) && Objects.equals(cusXdcTtr, cusXdc.cusXdcTtr) && Objects.equals(cusXdcFacl, cusXdc.cusXdcFacl) && Objects.equals(cusXdcPrll, cusXdc.cusXdcPrll) && Objects.equals(cusXdcFreval, cusXdc.cusXdcFreval) && Objects.equals(cusXdcFrehygval, cusXdc.cusXdcFrehygval) && Objects.equals(cusXdcPcg, cusXdc.cusXdcPcg) && Objects.equals(cusXdcNumvet, cusXdc.cusXdcNumvet) && Objects.equals(cusXdcDeebl, cusXdc.cusXdcDeebl) && Objects.equals(cusXdcCtDedbl, cusXdc.cusXdcCtDedbl) && Objects.equals(cusXdcCsc, cusXdc.cusXdcCsc) && Objects.equals(cusXdcNeonfac, cusXdc.cusXdcNeonfac) && Objects.equals(cusXdcPrrempa, cusXdc.cusXdcPrrempa) && Objects.equals(cusXdcPrremn, cusXdc.cusXdcPrremn) && Objects.equals(cusXdcEbltxt, cusXdc.cusXdcEbltxt) && Objects.equals(cusXdcCtDbltxt, cusXdc.cusXdcCtDbltxt) && Objects.equals(cusXdcPeetk, cusXdc.cusXdcPeetk) && Objects.equals(cusXdcRefobl, cusXdc.cusXdcRefobl) && Objects.equals(cusXdcBlv, cusXdc.cusXdcBlv) && Objects.equals(cusXdcDblvtxt, cusXdc.cusXdcDblvtxt) && Objects.equals(cusXdcClex, cusXdc.cusXdcClex) && Objects.equals(cusXdcEntweb, cusXdc.cusXdcEntweb) && Objects.equals(cusXdcNeonent, cusXdc.cusXdcNeonent) && Objects.equals(cusMiseEnService, cusXdc.cusMiseEnService) && Objects.equals(cusXdcHis, cusXdc.cusXdcHis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cusXdcNcs, cusXdcNda, cusXdcCom, cusXdcDep, cusXdcFre, cusXdcRne, cusXdcMoe, cusXdcCot, cusXdcIpx, cusXdcEbl, cusXdcSpl, cusXdcT1, cusXdcT2, cusXdcT3, cusXdcT4, cusXdcT5, cusXdcT6, cusXdcTs1, cusXdcTs2, cusXdcTs3, cusXdcTs4, cusXdcTs5, cusXdcTs6, cusXdcTf1, cusXdcTf2, cusXdcTf3, cusXdcTf4, cusXdcTf5, cusXdcTf6, cusXdcTo1, cusXdcTo2, cusXdcTo3, cusXdcTo4, cusXdcTo5, cusXdcTo6, cusXdcTt1, cusXdcTt2, cusXdcTt3, cusXdcTt4, cusXdcTt5, cusXdcTt6, cusXdcLja, cusXdcTfo, cusXdcInc, cusXdcPla, cusXdcCtLoc, cusXdcCtDde, cusXdcCtDfi, cusXdcCtDbl, cusXdcCtDivi, cusXdcCtBino, cusXdcCtDur, cusXdcCtInd1, cusXdcCtInd2, cusXdcCtInd3, cusXdcCtInd4, cusXdcCtInd5, cusXdcGeo, cusXdcLat, cusXdcLon, cusXdcTal, cusXdcTtr, cusXdcFacl, cusXdcPrll, cusXdcFreval, cusXdcLu01De, cusXdcLu01A, cusXdcLu02De, cusXdcLu02A, cusXdcLu03De, cusXdcLu03A, cusXdcLu04De, cusXdcLu04A, cusXdcMa01De, cusXdcMa01A, cusXdcMa02De, cusXdcMa02A, cusXdcMa03De, cusXdcMa03A, cusXdcMa04De, cusXdcMa04A, cusXdcMe01De, cusXdcMe01A, cusXdcMe02De, cusXdcMe02A, cusXdcMe03De, cusXdcMe03A, cusXdcMe04De, cusXdcMe04A, cusXdcJe01De, cusXdcJe01A, cusXdcJe02De, cusXdcJe02A, cusXdcJe03De, cusXdcJe03A, cusXdcJe04De, cusXdcJe04A, cusXdcVe01De, cusXdcVe01A, cusXdcVe02De, cusXdcVe02A, cusXdcVe03De, cusXdcVe03A, cusXdcVe04De, cusXdcVe04A, cusXdcSa01De, cusXdcSa01A, cusXdcSa02De, cusXdcSa02A, cusXdcSa03De, cusXdcSa03A, cusXdcSa04De, cusXdcSa04A, cusXdcDi01De, cusXdcDi01A, cusXdcDi02De, cusXdcDi02A, cusXdcDi03De, cusXdcDi03A, cusXdcDi04De, cusXdcDi04A, cusXdcFrehygval, cusXdcPcg, cusXdcPers, cusXdcNumvet, cusXdcAnc, cusXdcNbemp, cusXdcPrremp, cusXdcDeebl, cusXdcCtDedbl, cusXdcCsc, cusXdcNeonfac, cusXdcPrrempa, cusXdcPrremn, cusXdcEbltxt, cusXdcCtDbltxt, cusXdcPeetk, cusXdcRefobl, cusXdcBlv, cusXdcDblv, cusXdcDblvtxt, cusXdcClex, cusXdcEntweb, cusXdcNeonent, cusMiseEnService, cusXdcHis);
    }
}

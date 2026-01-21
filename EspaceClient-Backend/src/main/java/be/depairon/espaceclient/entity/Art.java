package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ART", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Art implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int artIin;
    private String artCom;
    private String artDep;
    private String artSta;
    private double artRpl;
    private String artDco;
    private String artStk;
    private String artAsm;
    private String artUni;
    private double artDec;
    private String artSal;
    private String artPur;
    private String artPik;
    private String artRor;
    private String artBat;
    private String artBal;
    private String artBti;
    private String artBed;
    private String artBls;
    private double artBaq;
    private String artSrn;
    private String artSrl;
    private String artSti;
    private double artWgt;
    private double artWgq;
    private double artNwg;
    private double artNwq;
    private double artVol;
    private double artVoq;
    private String artSba;
    private String artSbw;
    private double artSbn;
    private double artRen;
    private double artRpn;
    private String artGr1;
    private String artGr2;
    private String artGr3;
    private String artGr4;
    private String artGr5;
    private String artGr6;
    private String artAcc;
    private String artCta;
    private double artDtc;
    private String artUsc;
    private double artDtm;
    private String artUsm;
    private double artGen;
    private String artDcol;
    private String artSopt;
    private String artBty;
    private String artSst;
    private String artVid;
    private String artTrc;
    private String artTrs;
    private String artVrg;
    private String artDsq;
    private double artIinrgp;
    private String artTyiv;
    private String artKey;
    private String artSpc1;
    private String artSpc2;
    private String artSpc3;
    private String artSpc4;
    private String artSpc5;
    private String artPrd;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "artDesIin", referencedColumnName = "artIin")
    private List<ArtDes> descriptions;
}

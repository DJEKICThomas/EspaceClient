package be.depairon.espaceclient.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GEN_SRC", schema = "YGDEPAIRON")
public class GenSrc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String genSrcTyp;
    private String genSrcSrc;
    private String genSrcExt;
    private String genSrcArl;
    private String genSrcKey;
    private String genSrcCom;
    private String genSrcDep;
    private String genSrcArg;
    private String genSrcMan;
    private double genSrcDtm;
    private String genSrcUsm;
    private double genSrcDtc;
    private String genSrcUsc;
    private String genSrcDb;
    private String genSrcSta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getGenSrcTyp() {
        return genSrcTyp;
    }

    public void setGenSrcTyp(String genSrcTyp) {
        this.genSrcTyp = genSrcTyp;
    }


    public String getGenSrcSrc() {
        return genSrcSrc;
    }

    public void setGenSrcSrc(String genSrcSrc) {
        this.genSrcSrc = genSrcSrc;
    }


    public String getGenSrcExt() {
        return genSrcExt;
    }

    public void setGenSrcExt(String genSrcExt) {
        this.genSrcExt = genSrcExt;
    }


    public String getGenSrcArl() {
        return genSrcArl;
    }

    public void setGenSrcArl(String genSrcArl) {
        this.genSrcArl = genSrcArl;
    }


    public String getGenSrcKey() {
        return genSrcKey;
    }

    public void setGenSrcKey(String genSrcKey) {
        this.genSrcKey = genSrcKey;
    }


    public String getGenSrcCom() {
        return genSrcCom;
    }

    public void setGenSrcCom(String genSrcCom) {
        this.genSrcCom = genSrcCom;
    }


    public String getGenSrcDep() {
        return genSrcDep;
    }

    public void setGenSrcDep(String genSrcDep) {
        this.genSrcDep = genSrcDep;
    }


    public String getGenSrcArg() {
        return genSrcArg;
    }

    public void setGenSrcArg(String genSrcArg) {
        this.genSrcArg = genSrcArg;
    }


    public String getGenSrcMan() {
        return genSrcMan;
    }

    public void setGenSrcMan(String genSrcMan) {
        this.genSrcMan = genSrcMan;
    }


    public double getGenSrcDtm() {
        return genSrcDtm;
    }

    public void setGenSrcDtm(double genSrcDtm) {
        this.genSrcDtm = genSrcDtm;
    }


    public String getGenSrcUsm() {
        return genSrcUsm;
    }

    public void setGenSrcUsm(String genSrcUsm) {
        this.genSrcUsm = genSrcUsm;
    }


    public double getGenSrcDtc() {
        return genSrcDtc;
    }

    public void setGenSrcDtc(double genSrcDtc) {
        this.genSrcDtc = genSrcDtc;
    }


    public String getGenSrcUsc() {
        return genSrcUsc;
    }

    public void setGenSrcUsc(String genSrcUsc) {
        this.genSrcUsc = genSrcUsc;
    }


    public String getGenSrcDb() {
        return genSrcDb;
    }

    public void setGenSrcDb(String genSrcDb) {
        this.genSrcDb = genSrcDb;
    }


    public String getGenSrcSta() {
        return genSrcSta;
    }

    public void setGenSrcSta(String genSrcSta) {
        this.genSrcSta = genSrcSta;
    }

}

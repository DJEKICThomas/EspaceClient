package be.depairon.espaceclient.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BARCODE", schema = "YGDEPAIRON")
public class Barcode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String com;
    private String dep;
    private long barcode;
    private String ncs;
    private int nda;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iin", referencedColumnName = "artIin")
    private Art article;
    private int arm;
    private int cass;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "porteurid", unique = true)
    private Porteur porteur;
    private String lovepe;
    private String usc;
    private int dtc;
    private int tmc;
    private String flag;
    private String flagrai;
    private String usd;
    private int dtd;
    private int tmd;
    private int lon;
    private String modif;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public int getNda() {
        return nda;
    }

    public void setNda(int nda) {
        this.nda = nda;
    }

    public Art getArticle() {
        return article;
    }

    public void setArticle(Art article) {
        this.article = article;
    }

    public int getArm() {
        return arm;
    }

    public void setArm(int arm) {
        this.arm = arm;
    }

    public int getCass() {
        return cass;
    }

    public void setCass(int cass) {
        this.cass = cass;
    }

    public Porteur getPorteur() {
        return porteur;
    }

    public void setPorteur(Porteur porteur) {
        this.porteur = porteur;
    }

    public String getLovepe() {
        return lovepe;
    }

    public void setLovepe(String lovepe) {
        this.lovepe = lovepe;
    }

    public String getUsc() {
        return usc;
    }

    public void setUsc(String usc) {
        this.usc = usc;
    }

    public int getDtc() {
        return dtc;
    }

    public void setDtc(int dtc) {
        this.dtc = dtc;
    }

    public int getTmc() {
        return tmc;
    }

    public void setTmc(int tmc) {
        this.tmc = tmc;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlagrai() {
        return flagrai;
    }

    public void setFlagrai(String flagrai) {
        this.flagrai = flagrai;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public int getDtd() {
        return dtd;
    }

    public void setDtd(int dtd) {
        this.dtd = dtd;
    }

    public int getTmd() {
        return tmd;
    }

    public void setTmd(int tmd) {
        this.tmd = tmd;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public String getModif() {
        return modif;
    }

    public void setModif(String modif) {
        this.modif = modif;
    }
}

package be.depairon.espaceclient.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "USR", schema = "YG51TT")
public class EglobalUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "USERID")
    private String userid;
    
    @Column(name = "USERCMP")
    private String usercmp;
    
    @Column(name = "USERDEP")
    private String userdep;
    
    @Column(name = "USERDES")
    private String userdes;
    
    @Column(name = "USERPASS")
    private String userpass;
    
    @Column(name = "USERTYP")
    private String usertyp;
    
    @Column(name = "USERLNG")
    private String userlng;
    
    @Column(name = "USERSTS")
    private String usersts;
    
    @Column(name = "USERVAL")
    private String userval;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "DATEACT")
    private int dateact;
    
    @Column(name = "USERDTC")
    private int userdtc;
    
    @Column(name = "USERUSC")
    private String userusc;
    
    @Column(name = "USERDTM")
    private int userdtm;
    
    @Column(name = "USERUSM")
    private String userusm;
    
    @Column(name = "ALLDEP")
    private String alldep;
    
    @Column(name = "LVL")
    private BigInteger lvl;
    
    @Column(name = "WDAY1")
    private int wday1;
    
    @Column(name = "WDAY2")
    private int wday2;
    
    @Column(name = "WDAYACT")
    private String wdayact;
    
    @Column(name = "WHOUR1")
    private int whour1;
    
    @Column(name = "WHOUR2")
    private int whour2;
    
    @Column(name = "MON")
    private String mon;
    
    @Column(name = "TUE")
    private String tue;
    
    @Column(name = "WED")
    private String wed;
    
    @Column(name = "THU")
    private String thu;
    
    @Column(name = "FRI")
    private String fri;
    
    @Column(name = "SAT")
    private String sat;
    
    @Column(name = "SUN")
    private String sun;
    
    @Column(name = "IDENTIFY")
    private String identify;
    
    @Column(name = "TEL")
    private String tel;
    
    @Column(name = "CUS")
    private String cus;
    
    @Column(name = "SUP")
    private String sup;
    
    @Column(name = "THIRD")
    private String third;
    
    @Column(name = "GACC")
    private String gacc;
    
    @Column(name = "ANA")
    private String ana;
    
    @Column(name = "LICENSE")
    private String license;
    
    @Column(name = "DEFC")
    private String defc;
    
    @Column(name = "DEFS")
    private String defs;
    
    @Column(name = "DEFT")
    private String deft;
    
    @Column(name = "DEFI")
    private String defi;
    
    @Column(name = "FAPM")
    private String fapm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsercmp() {
        return usercmp;
    }

    public void setUsercmp(String usercmp) {
        this.usercmp = usercmp;
    }

    public String getUserdep() {
        return userdep;
    }

    public void setUserdep(String userdep) {
        this.userdep = userdep;
    }

    public String getUserdes() {
        return userdes;
    }

    public void setUserdes(String userdes) {
        this.userdes = userdes;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public String getUsertyp() {
        return usertyp;
    }

    public void setUsertyp(String usertyp) {
        this.usertyp = usertyp;
    }

    public String getUserlng() {
        return userlng;
    }

    public void setUserlng(String userlng) {
        this.userlng = userlng;
    }

    public String getUsersts() {
        return usersts;
    }

    public void setUsersts(String usersts) {
        this.usersts = usersts;
    }

    public String getUserval() {
        return userval;
    }

    public void setUserval(String userval) {
        this.userval = userval;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDateact() {
        return dateact;
    }

    public void setDateact(int dateact) {
        this.dateact = dateact;
    }

    public int getUserdtc() {
        return userdtc;
    }

    public void setUserdtc(int userdtc) {
        this.userdtc = userdtc;
    }

    public String getUserusc() {
        return userusc;
    }

    public void setUserusc(String userusc) {
        this.userusc = userusc;
    }

    public int getUserdtm() {
        return userdtm;
    }

    public void setUserdtm(int userdtm) {
        this.userdtm = userdtm;
    }

    public String getUserusm() {
        return userusm;
    }

    public void setUserusm(String userusm) {
        this.userusm = userusm;
    }

    public String getAlldep() {
        return alldep;
    }

    public void setAlldep(String alldep) {
        this.alldep = alldep;
    }

    public BigInteger getLvl() {
        return lvl;
    }

    public void setLvl(BigInteger lvl) {
        this.lvl = lvl;
    }

    public int getWday1() {
        return wday1;
    }

    public void setWday1(int wday1) {
        this.wday1 = wday1;
    }

    public int getWday2() {
        return wday2;
    }

    public void setWday2(int wday2) {
        this.wday2 = wday2;
    }

    public String getWdayact() {
        return wdayact;
    }

    public void setWdayact(String wdayact) {
        this.wdayact = wdayact;
    }

    public int getWhour1() {
        return whour1;
    }

    public void setWhour1(int whour1) {
        this.whour1 = whour1;
    }

    public int getWhour2() {
        return whour2;
    }

    public void setWhour2(int whour2) {
        this.whour2 = whour2;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCus() {
        return cus;
    }

    public void setCus(String cus) {
        this.cus = cus;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getGacc() {
        return gacc;
    }

    public void setGacc(String gacc) {
        this.gacc = gacc;
    }

    public String getAna() {
        return ana;
    }

    public void setAna(String ana) {
        this.ana = ana;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDefc() {
        return defc;
    }

    public void setDefc(String defc) {
        this.defc = defc;
    }

    public String getDefs() {
        return defs;
    }

    public void setDefs(String defs) {
        this.defs = defs;
    }

    public String getDeft() {
        return deft;
    }

    public void setDeft(String deft) {
        this.deft = deft;
    }

    public String getDefi() {
        return defi;
    }

    public void setDefi(String defi) {
        this.defi = defi;
    }

    public String getFapm() {
        return fapm;
    }

    public void setFapm(String fapm) {
        this.fapm = fapm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EglobalUser that = (EglobalUser) o;
        return id == that.id && dateact == that.dateact && userdtc == that.userdtc && userdtm == that.userdtm && wday1 == that.wday1 && wday2 == that.wday2 && whour1 == that.whour1 && whour2 == that.whour2 && Objects.equals(userid, that.userid) && Objects.equals(usercmp, that.usercmp) && Objects.equals(userdep, that.userdep) && Objects.equals(userdes, that.userdes) && Objects.equals(userpass, that.userpass) && Objects.equals(usertyp, that.usertyp) && Objects.equals(userlng, that.userlng) && Objects.equals(usersts, that.usersts) && Objects.equals(userval, that.userval) && Objects.equals(email, that.email) && Objects.equals(userusc, that.userusc) && Objects.equals(userusm, that.userusm) && Objects.equals(alldep, that.alldep) && Objects.equals(lvl, that.lvl) && Objects.equals(wdayact, that.wdayact) && Objects.equals(mon, that.mon) && Objects.equals(tue, that.tue) && Objects.equals(wed, that.wed) && Objects.equals(thu, that.thu) && Objects.equals(fri, that.fri) && Objects.equals(sat, that.sat) && Objects.equals(sun, that.sun) && Objects.equals(identify, that.identify) && Objects.equals(tel, that.tel) && Objects.equals(cus, that.cus) && Objects.equals(sup, that.sup) && Objects.equals(third, that.third) && Objects.equals(gacc, that.gacc) && Objects.equals(ana, that.ana) && Objects.equals(license, that.license) && Objects.equals(defc, that.defc) && Objects.equals(defs, that.defs) && Objects.equals(deft, that.deft) && Objects.equals(defi, that.defi) && Objects.equals(fapm, that.fapm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, usercmp, userdep, userdes, userpass, usertyp, userlng, usersts, userval, email, dateact, userdtc, userusc, userdtm, userusm, alldep, lvl, wday1, wday2, wdayact, whour1, whour2, mon, tue, wed, thu, fri, sat, sun, identify, tel, cus, sup, third, gacc, ana, license, defc, defs, deft, defi, fapm);
    }
}

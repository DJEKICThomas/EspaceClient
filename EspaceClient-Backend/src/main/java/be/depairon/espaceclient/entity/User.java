package be.depairon.espaceclient.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Entity
@Table(name = "WEBUSER", schema = "YG51TT")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String password;
    private int pwdexpired;
    private String name;
    private String firstname;
    private String yguser;
    private LocalDate usrexpdate;
    private String phone;
    private String fax;
    private String function;
    private String language;
    private String title;
    private String gsm;
    private int main;
    private String profile;
    private int newsletter;
    private int active;
    private int nomail;
    private int extacces;

    @PostLoad
    public void trimData() {
        this.userid = this.userid.trim();
        this.name = this.name.trim();
        this.firstname = this.firstname.trim();
        this.profile = this.profile.trim();
        this.yguser = this.yguser.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPwdexpired() {
        return pwdexpired;
    }

    public void setPwdexpired(int pwdexpired) {
        this.pwdexpired = pwdexpired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getYguser() {
        return yguser;
    }

    public void setYguser(String yguser) {
        this.yguser = yguser;
    }

    public void setUsrexpdate(LocalDate usrexpdate) {
        this.usrexpdate = usrexpdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(int newsletter) {
        this.newsletter = newsletter;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getNomail() {
        return nomail;
    }

    public void setNomail(int nomail) {
        this.nomail = nomail;
    }

    public int getExtacces() {
        return extacces;
    }

    public void setExtacces(int extacces) {
        this.extacces = extacces;
    }

    public LocalDate getUsrexpdate() {
        return usrexpdate;
    }

    public void setUsrexpdate(LocalDateTime usrexpdate) {
        this.usrexpdate = usrexpdate != null ? LocalDate.ofInstant(usrexpdate.toInstant(ZoneOffset.UTC),
                ZoneId.systemDefault()) : null;
    }
}

package be.depairon.espaceclient.dto;

import java.time.LocalDate;

public class UserDTO {

    private Long id;
    private String yguser;
    private int nomail;
    private int pwdexpired;
    private String email;
    private String password; //TODO enlever le pwd pour l'affichage de la liste
    private String confirmPassword;
    private String title;
    private String lastname;
    private String firstname;
    private LocalDate usrexpdate;
    private String profile;
    private String function;
    private String telephone;
    private String mobile;
    private String fax;
    private int main;
    private int newsletter;
    private int active;
    private int extacces;

    public UserDTO() {
        this.id = 0L;
        this.yguser = "";
        this.nomail = 0;
        this.pwdexpired = 0;
        this.email = "";
        this.password = "";
        this.confirmPassword = "";
        this.title = "";
        this.lastname = "";
        this.firstname = "";
        this.usrexpdate = null;
        this.profile = "";
        this.function = "";
        this.telephone = "";
        this.mobile = "";
        this.fax = "";
        this.main = 0;
        this.newsletter = 0;
        this.active = 0;
        this.extacces = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYguser() {
        return yguser;
    }

    public void setYguser(String yguser) {
        this.yguser = yguser;
    }

    public int getNomail() {
        return nomail;
    }

    public void setNomail(int nomail) {
        this.nomail = nomail;
    }

    public int getPwdexpired() {
        return pwdexpired;
    }

    public void setPwdexpired(int pwdexpired) {
        this.pwdexpired = pwdexpired;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public LocalDate getUsrexpdate() {
        return usrexpdate;
    }

    public void setUsrexpdate(LocalDate usrexpdate) {
        this.usrexpdate = usrexpdate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
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

    public int getExtacces() {
        return extacces;
    }

    public void setExtacces(int extacces) {
        this.extacces = extacces;
    }
}

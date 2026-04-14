package be.depairon.espaceclient.dto;

public class UserCompanyDTO {

    private String usercmp;
    private String userdep;
    private String companyName;
    private String eGlobalUserId;

    public String getUsercmp(){
        return usercmp;
    }

    public void setUsercmp(String usercmp){
        this.usercmp = usercmp;
    }

    public String getUserdep(){
        return userdep;
    }

    public void setUserdep(String userdep) {
        this.userdep = userdep;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String geteGlobalUserId() {
        return eGlobalUserId;
    }

    public void seteGlobalUserId(String eGlobalUserId) {
        this.eGlobalUserId = eGlobalUserId;
    }
}

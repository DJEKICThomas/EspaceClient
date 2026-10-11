package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.enumeration.WebUserProfile;

import java.util.List;

public class WebUserLoginDTO {

    private Long userId;
    private boolean isLoginOk;
    private WebUserProfile profile;
    private List<WebManagedCustomerDTO> customers;
    private String eGlobalUserId;
    private String token;
    private boolean totpRequired;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isLoginOk() {
        return isLoginOk;
    }

    public void setLoginOk(boolean loginOk) {
        isLoginOk = loginOk;
    }

    public WebUserProfile getProfile() {
        return profile;
    }

    public void setProfile(WebUserProfile profile) {
        this.profile = profile;
    }

    public List<WebManagedCustomerDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<WebManagedCustomerDTO> customers) {
        this.customers = customers;
    }

    public String geteGlobalUserId() {
        return eGlobalUserId;
    }

    public void seteGlobalUserId(String eGlobalUserId) {
        this.eGlobalUserId = eGlobalUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTotpRequired() {
        return totpRequired;
    }

    public void setTotpRequired(boolean totpRequired) {
        this.totpRequired = totpRequired;
    }
}

package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.enumeration.WebUserProfile;

public class WebUserRemoteDTO {

    private long userId;
    private String username;
    private WebUserProfile profile;
    private String customerNumber;
    private String customerName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WebUserProfile getProfile() {
        return profile;
    }

    public void setProfile(WebUserProfile profile) {
        this.profile = profile;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}

package be.depairon.espaceclient.dto;

public class WebManagedCustomerDTO {

    private long id;
    private String userId;
    private String ncs;
    private long customerId;
    private String customerName;
    private String selectedCustomer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNcs() {
        return ncs;
    }

    public void setNcs(String ncs) {
        this.ncs = ncs;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(String selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}

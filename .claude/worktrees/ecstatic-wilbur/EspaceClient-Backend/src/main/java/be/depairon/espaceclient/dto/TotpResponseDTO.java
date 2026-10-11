package be.depairon.espaceclient.dto;

public class TotpResponseDTO {

    private boolean success;
    private String message;
    private String qrCodeDataUri;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQrCodeDataUri() {
        return qrCodeDataUri;
    }

    public void setQrCodeDataUri(String qrCodeDataUri) {
        this.qrCodeDataUri = qrCodeDataUri;
    }
}

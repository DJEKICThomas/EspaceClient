package be.depairon.espaceclient.dto;

import be.depairon.espaceclient.enumeration.WebUserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WebUserLoginDTO {

    private Long userId;
    private boolean isLoginOk;
    private WebUserProfile profile;
    private List<WebManagedCustomerDTO> customers;
    private String eGlobalUserId;
    private String language;
    private String token;
    private boolean totpRequired;
    private boolean mustSetup2Fa;
    private String redirectUrl;
    private List<UserCompanyDTO> companies;
}
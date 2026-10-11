package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationDTO {
    private String email;
    private String confirmEmail;
    private String password;
    private String confirmPassword;
    private String title;
    private String lastname;
    private String firstname;
    private String telephone;
    private String mobile;
    private String fax;
    private String function;

    private String socityName;
    private String vat;
    private String addess;
    private String postalCode;
    private String country;
    private String generalFax;
    private String activitySector;
    private String locality;
    private String generalTelephone;
    private String socityMail;
    private String employeesNumber;
    private String additionalNotes;
}

package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangeOwnPasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}

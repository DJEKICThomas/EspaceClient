package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionDTO {
    private String role;
    private String code;
    private String comment;
    private boolean hasAccess;
}

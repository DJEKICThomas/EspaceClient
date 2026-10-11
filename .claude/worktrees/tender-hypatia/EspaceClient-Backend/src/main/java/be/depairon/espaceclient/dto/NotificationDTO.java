package be.depairon.espaceclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private String gestionnaire;
    private Integer isNew;
    private LocalDateTime date;
}

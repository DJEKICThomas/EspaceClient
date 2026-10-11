package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class TechInfo {

    @Column(name = "USER", length = 10)
    private String user;
    @Column(name = "DATE")
    private LocalDateTime date;
}

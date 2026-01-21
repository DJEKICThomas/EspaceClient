package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "STOPS", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Stops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "REM")
    private String rem;
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "REM_WEB")
    private String remWeb;
    @Column(name = "ACTIVE_WEB")
    private String activeWeb;

}

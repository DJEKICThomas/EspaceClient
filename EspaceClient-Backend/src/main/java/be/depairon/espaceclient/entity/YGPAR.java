package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "YGPAR", schema = "YGCOMMON")
@Getter
@Setter
@NoArgsConstructor
public class Ygpar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "COM")
    private String com;

    @Column(name = "DEP")
    private String dep;

    @Column(name = "MOD")
    private String mod;

    @Column(name = "PROPERTY")
    private String property;

    @Column(name = "SYSTEM")
    private String system;

    @Column(name = "VALUE")
    private String value;
}
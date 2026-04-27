package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DSHEAD", schema = "YGS01")
@Getter
@Setter
@NoArgsConstructor
public class DsHead implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "COM")
    private String com;

    @Column(name = "NCS")
    private String ncs;

    @Column(name = "DOC")
    private String doc;

    @Column(name = "SLV")
    private Integer slv;
}

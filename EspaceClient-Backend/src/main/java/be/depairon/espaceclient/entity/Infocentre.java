package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "INFOCENTRE", schema = "YG51TT")
@Getter
@Setter
@NoArgsConstructor
public class Infocentre implements Serializable {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "NCS")
    private String ncs;

    @Column(name = "INFO")
    private String info;

    @Column(name = "DTC")
    private int dateCreation;

    @Column(name = "REFERENCE")
    private String reference;

    /* Column(name = "VISIBILITY)
    * private boolean visibility;
    * */

}
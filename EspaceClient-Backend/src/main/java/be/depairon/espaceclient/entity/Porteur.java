package be.depairon.espaceclient.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CUS_PORT", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Porteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cusPortCom;
    private String cusPortDep;
    private String cusPortNcs;
    private int cusPortNda;
    private String cusPortSite;
    private String cusPortNom;
    private String cusPortPrenom;
    private String cusPortPersnom;
    private String cusPortPersprenom;
    private String cusPortPersfonction;
    private String cusPortPersvet;
    private String cusPortPersvetExt;
    private String cusPortInactive;
}

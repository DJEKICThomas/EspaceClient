package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CUS_CTR", schema = "YGS01")
public class CusCtr {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NCS")
    private String ncs;

    @Column(name = "REF")
    private String reference;

    @Column(name = "NAT")
    private String nature;

    @Column(name = "ACTIF")
    private String actif;

    @Column(name = "DDC")
    private Integer startDate;

    @Column(name = "DFC")
    private Integer endDate;

    //@Column(name = "DESCRIPTION")
    //private String description;

    @Column(name = "RECUR")
    private Integer recur;

    //@Column(name = "CLASSIFICATION")
    //private String classification;

    //@Column(name = "CALCUL_MODE")
    //private String calculMode;

    @Column(name = "DOCCTR")
    private String docCtr;

}
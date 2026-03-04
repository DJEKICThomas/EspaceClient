package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CUS_CTR", schema = "YG51TT")
public class CusCtr {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NCS")
    private String ncs;

    @Column(name = "REF")
    private String reference;

    @Column(name = "NAT")
    private String nature; // On filtrera sur "ABO"

    @Column(name = "ACTIF")
    private String actif; // 'Y' ou 'N'

    @Column(name = "DDC")
    private Integer startDate; // Format AAAAMMJJ dans votre BDD

    @Column(name = "DFC")
    private Integer endDate;

    @Column(name = "DESCRIPTION")
    private String description;

}
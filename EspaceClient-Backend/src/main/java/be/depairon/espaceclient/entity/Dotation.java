package be.depairon.espaceclient.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "WEBDOTATION", schema = "YGDEPAIRON")
@Data
@NoArgsConstructor
public class Dotation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CUS_NCS")
    private String client;
    @Column(name = "CUS_NDA")
    private int adresse;
    @Column(name = "CUS_NM1")
    private String nomClient;
    @Column(name = "BARCODE")
    private long barcode;
    @Column(name = "ARM")
    private int armoire;
    @Column(name = "CASS")
    private int cassette;
    @Column(name = "NAT")
    private String nature;
    @Column(name = "GEN_SRC_ARG")
    private String article;
    @Column(name = "ART_DES_DES")
    private String description;
    @Column(name = "DTC")
    private int miseService;
    @Column(name = "DATETIME")
    private Timestamp dateDernierPassage;
    @Column(name = "PORTEUR")
    private String porteur;
    @Column(name = "STOP")
    private String stop;
    @Column(name = "DATE_IN")
    private Date dateIn;
    @Column(name = "DATE_OUT")
    private Date dateOUT;
}

package be.depairon.espaceclient.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BARCODEREMP", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class BarcodeRemplacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "BCR")
    private int bcr = 0;
    @Column(name = "BARCODE")
    private long barcode = 0;
    @Column(name = "NERE")
    private String nere = "";
    @Column(name = "CETI")
    private String ceti = "";
    @Column(name = "CTRF")
    private String ctrf = "";
    @Column(name = "CDEL")
    private String cdel = "";
    @Column(name = "DTC")
    private int dtc = 0;
    @Column(name = "USC")
    private String usc = "";
    @Column(name = "INUSE")
    private String inuse = "";
    @Column(name = "DTI")
    private int dti = 0;
    @Column(name = "IINREL")
    private int iinRel = 0;
}

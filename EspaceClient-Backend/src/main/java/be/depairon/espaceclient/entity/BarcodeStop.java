package be.depairon.espaceclient.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BARCODESTOP", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class BarcodeStop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barcode", referencedColumnName = "barcode")
    private Barcode barcode;
    private String stop;
    private String rem;
    private String perm;
    private int dtb;
    private int dtf;
    private int dtr;
    private long barstopencid;
    private String ce;
    private String prod;
    private String usc;

}

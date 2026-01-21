package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "BARCODEDET", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class BarcodeDet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double barcode;
    private Timestamp datetime;
    private String positionid;
    private double nbrlav;
    private String remploi;
    private String subpositionid;
    private String remplace;
    private long bcbheaderid;
    private long bcbdetailid;
    private String frombcr;
    private LocalDate lastEntryDate;
    private LocalDate lastReleaseDate;
}

package be.depairon.espaceclient.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "WEBENTRYCTRL", schema = "YGDEPAIRON")
@Data
public class EntryControl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String carrier;
    private LocalDate date;
    private int cupboard;
    private int cassette;
    private long barcode;
    private String ncs;
    private String nda;
    private long artid;

}
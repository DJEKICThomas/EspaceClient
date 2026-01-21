package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ART_XDI", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class ArtXdi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String artXdiCom;
    private String artXdiDep;
    @Column(name = "ART_XDI_IIN")
    private int artXdiIin;
    private int artXdiArtloc;
    private int artXdiArtbin;
    private int artXdiArtent;
    private int artXdiNnc;
    private int artXdiFrt;
    private int artXdiLty;
    private int artXdiLpr;
    private int artXdiLfi;
    private int artXdiLco;
    private String artXdiTyp;
    private String artXdiDim;
    private String artXdiCou;
    private String artXdiFre;
    private double artXdiRegtap;
    private double artXdiRetvet;
    private double artXdiLonvet;
    private double artXdiTce;
    private double artXdiArtpers;
    private double artXdiCco;
    private String artXdiBarcode;
    private String artXdiDot;
    private int artXdiExprspc;
    private String artXdiExjen;
    private double artXdiHlav;
    private double artXdiChv;
}

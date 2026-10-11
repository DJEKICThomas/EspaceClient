package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CUS", schema = "YG51TT")
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CUS_NCS")
    private String cusNcs;
    @Column(name = "CUS_NDA")
    private int cusNda;
    private String cusTyp;
    private String cusCom;
    private String cusDep;
    private String cusTit;
    private String cusNm1;
    private String cusNm2;
    private String cusSt1;
    private String cusSt2;
    private String cusZip;
    private String cusCty;
    private String cusLoc;
    private String cusCtr;
    private String cusLng;
    private String cusTel;
    private String cusFax;
    private String cusGsm;
    private String cusMai;
    private String cusCur;
    private String cusSts;
    private String cusKin;
    private String cusMia;
    private String cusMda;
    private int cusDtm;
    private String cusUsm;
    private int cusDtc;
    private String cusUsc;
    private String cusVat;
    private String cusVtc;
    private String cusVtn;
    private String cusVtb;
    private String cusReg;
    private String cusCos;
    private String cusKey;
    private String cusStncs;
    private int cusStnda;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CUS_SAL_NCS", referencedColumnName = "CUS_NCS", updatable = false, insertable = false),
            @JoinColumn(name = "CUS_SAL_NDA", referencedColumnName = "CUS_NDA", updatable = false, insertable = false)
    })
    private List<CusSal> cusSal;

}

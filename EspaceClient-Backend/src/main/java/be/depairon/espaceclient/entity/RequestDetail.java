package be.depairon.espaceclient.entity;

import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "REQUEST_DETAIL")
@Table(schema = "YGDEPAIRON")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetail extends TechInfo {
    // todo ajouter deux commentaires sur les requetes details (voir avec NHO)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestheader_id", referencedColumnName = "id")
    private RequestHeader requestHeader;

    @ManyToMany
    @JoinTable(
            name = "REQUEST_DETAIL_BARCODE",
            joinColumns = {@JoinColumn(name = "REQUESTDETAIL_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BARCODE_ID")},
            schema = "YGDEPAIRON"
    )
    private List<Barcode> barcodeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artid", unique = true)
    private Art article;

    @Enumerated(EnumType.STRING)
    private RequestType typ;

    @Enumerated(EnumType.STRING)
    private RequestFlag flag;
    private String text;
    private String textGes = "";
    private LocalDate returndate;
    private LocalDate closedate;

    public void addBarcodeToList(Barcode barcode) {
        if (this.barcodeList == null) this.barcodeList = new ArrayList<>();
        this.barcodeList.add(barcode);
    }
}
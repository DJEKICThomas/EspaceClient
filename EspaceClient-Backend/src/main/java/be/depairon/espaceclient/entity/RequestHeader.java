package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "REQUEST_HEADER", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class RequestHeader extends TechInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int cupboard;
    private int cassette;
    private String carrier;
    private String site;
    private String ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ncsid", referencedColumnName = "id"/*, insertable = false, updatable = false*/)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "requestheader_id", referencedColumnName = "id")
    private List<RequestDetail> requestDetail;

    public void addRequestDetailToList(RequestDetail detail) {
        if (this.requestDetail == null) this.requestDetail = new ArrayList<>();
        this.requestDetail.add(detail);
    }

    public void addAllDetailList(List<RequestDetail> requestDetail) {
        if (this.requestDetail == null) this.requestDetail = new ArrayList<>();
        this.requestDetail.addAll(requestDetail);
    }

    public void removeFromList(List<RequestDetail> requestDetail) {
        if (this.requestDetail != null) this.requestDetail.removeAll(requestDetail);
    }
}

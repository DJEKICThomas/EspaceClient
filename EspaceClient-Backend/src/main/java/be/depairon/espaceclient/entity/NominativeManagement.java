package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WEBNOMINM", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class NominativeManagement implements Serializable {

    @PostLoad
    public void trimData() {
        this.carrier = this.carrier.trim();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String carrier;
    private int quantity;
    private int cupboard;
    private int cassette;
    @Transient
    private String nature = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ncsid")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artid", unique = true)
    private Art article;

}

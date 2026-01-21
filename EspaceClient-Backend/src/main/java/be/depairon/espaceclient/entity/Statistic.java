package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WEBSTATISTIC", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Statistic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int month;
    private int year;
    private int counter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ncsid")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artid", unique = true)
    private Art article;

}

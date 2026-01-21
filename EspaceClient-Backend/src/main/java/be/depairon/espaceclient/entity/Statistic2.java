package be.depairon.espaceclient.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WEBSTATISTIC2", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Statistic2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int year1;
    private int month1;
    private int counter1;
    private int year2;
    private int month2;
    private int counter2;
    private int year3;
    private int month3;
    private int counter3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ncsid")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artid", unique = true)
    private Art article;


}

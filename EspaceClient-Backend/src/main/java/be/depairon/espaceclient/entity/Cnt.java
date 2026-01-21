package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CNT", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Cnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cntcode;
    private String com;
    private String dep;
    private int cntnr;
    private int lcnt;
}

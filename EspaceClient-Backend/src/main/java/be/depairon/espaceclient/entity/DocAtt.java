package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DOCATT", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class DocAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "DOC_DOK")
    private String docDok;

    @Column(name = "DOC_ATT")
    private String docAtt;

    @Column(name = "DOC_ACT")
    private String docAct;

    @Column(name = "IIN")
    private Long iin;
}
package be.depairon.espaceclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOCC", schema = "YGS01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DocC {
        @Id
        @Column(name = "DOCC_DOK")
        private String doccDok;

        @Column(name = "DOCC_NCS")
        private String doccNcs;

        @Column(name = "DOCC_TYP")
        private String doccTyp;

        @Column(name = "IIN")
        private Long iin;
}

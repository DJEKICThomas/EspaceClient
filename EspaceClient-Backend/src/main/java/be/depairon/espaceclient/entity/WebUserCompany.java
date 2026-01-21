package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "WEBUSERCOMPANY", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class WebUserCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERID")
    private String userid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADRESS1")
    private String adress1;

    @Column(name = "ADRESS2")
    private String adress2;

    @Column(name = "POSTALCODE")
    private String postalcode;

    @Column(name = "LOCALITY")
    private String locality;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "VAT")
    private String vat;

    @Column(name = "SECTOR")
    private String sector;

    @Column(name = "EMPLOYEE")
    private int employee;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "COMMENT")
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebUserCompany that = (WebUserCompany) o;
        return employee == that.employee && id == that.id && Objects.equals(userid, that.userid) && Objects.equals(name, that.name) && Objects.equals(adress1, that.adress1) && Objects.equals(adress2, that.adress2) && Objects.equals(postalcode, that.postalcode) && Objects.equals(locality, that.locality) && Objects.equals(country, that.country) && Objects.equals(vat, that.vat) && Objects.equals(sector, that.sector) && Objects.equals(phone, that.phone) && Objects.equals(fax, that.fax) && Objects.equals(mail, that.mail) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, name, adress1, adress2, postalcode, locality, country, vat, sector, employee, phone, fax, mail, comment, id);
    }
}

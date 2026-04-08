package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "WEBPROFILE", schema = "YGS01")
@Getter
@Setter
@NoArgsConstructor
public class Webprofile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NOEXTACCES")
    private BigInteger noextacces;

    @ManyToMany
    @JoinTable(
            name = "WEBPROFILEROLE",
            joinColumns = {@JoinColumn(name = "WEBPROFILEID")},
            inverseJoinColumns = {@JoinColumn(name = "WEBROLEID")},
            schema = "YG51TT"
    )
    private List<Webrole> webroleList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Webprofile that = (Webprofile) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(noextacces, that.noextacces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, noextacces);
    }
}

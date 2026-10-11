package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "WEBROLE", schema = "YGDEPAIRON")
@Getter
@Setter
@NoArgsConstructor
public class Webrole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "COMMENT")
    private String comment;

    @Transient
    private boolean hasAccess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Webrole webrole = (Webrole) o;
        return id == webrole.id && Objects.equals(role, webrole.role) && Objects.equals(comment, webrole.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, comment, id);
    }
}

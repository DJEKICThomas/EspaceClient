package be.depairon.espaceclient.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "WEBUSERROLE", schema = "YGS01")
@Getter
@Setter
@NoArgsConstructor
public class WebUserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "USERID")
    private String userid;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "COMMENT")
    private String comment;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebUserRole that = (WebUserRole) o;
        return id == that.id && Objects.equals(userid, that.userid) && Objects.equals(role, that.role) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, role, comment, id);
    }
}

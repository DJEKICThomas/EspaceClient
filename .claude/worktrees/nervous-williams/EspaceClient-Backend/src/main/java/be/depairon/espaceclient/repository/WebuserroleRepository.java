package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.WebUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebuserroleRepository extends CrudRepository<WebUserRole, Long> {
    Optional<WebUserRole> findByUseridAndRole(String urserId, String role);
}

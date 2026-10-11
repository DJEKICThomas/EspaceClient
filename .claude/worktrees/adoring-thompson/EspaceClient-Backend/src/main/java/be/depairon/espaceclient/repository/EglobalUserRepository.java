package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.EglobalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EglobalUserRepository extends JpaRepository<EglobalUser, Long> {
    Optional<EglobalUser> findByUserid(String userid);
}

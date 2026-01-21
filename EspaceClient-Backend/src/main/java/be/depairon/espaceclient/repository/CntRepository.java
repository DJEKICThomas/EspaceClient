package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.Cnt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CntRepository extends CrudRepository<Cnt, Long> {
    Optional<Cnt> findByCntcode(String code);
}

package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllByProfileNot(String profile);
    boolean existsByUseridIgnoreCase(String userId);
}

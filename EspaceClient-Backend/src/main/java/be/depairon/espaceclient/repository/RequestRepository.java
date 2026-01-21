package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.RequestHeader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<RequestHeader, Long> {
}

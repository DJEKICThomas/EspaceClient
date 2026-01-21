package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.RequestHeader;
import be.depairon.espaceclient.enumeration.RequestType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestHeaderRepository extends CrudRepository<RequestHeader, Long> {
    Iterable<RequestHeader> findAllByOrderByDateDescIdDesc();
    Iterable<RequestHeader> findAllByRequestDetailTypOrderByDateDescIdDesc(RequestType typ);

}

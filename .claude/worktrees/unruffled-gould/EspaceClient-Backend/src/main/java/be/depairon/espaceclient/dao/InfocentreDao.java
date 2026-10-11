package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Infocentre;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public interface InfocentreDao {
    List<Infocentre> findByNcs(String ncs);
}

package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.CusCtrDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.CusCtr;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CusCtrDaoImpl extends GenericDao implements CusCtrDao {

    @Override
    public List<CusCtr> findByNcsAndNature(String ncs, String nature) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CusCtr> cq = cb.createQuery(CusCtr.class);
        Root<CusCtr> root = cq.from(CusCtr.class); // On part de l'entité

        Predicate ncsPredicate = cb.equal(root.get("ncs"), ncs);
        Predicate naturePredicate = cb.equal(root.get("nature"), nature);

        cq.where(cb.and(ncsPredicate, naturePredicate));
        cq.orderBy(cb.desc(root.get("startDate")));

        return entityManager.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}

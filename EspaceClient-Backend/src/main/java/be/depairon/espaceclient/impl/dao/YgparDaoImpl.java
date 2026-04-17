package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.YgparDao;
import be.depairon.espaceclient.entity.Ygpar;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class YgparDaoImpl extends GenericDao implements YgparDao {

    @Override
    public Ygpar findLogoByCompany(String com) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ygpar> cq = cb.createQuery(Ygpar.class);
        Root<Ygpar> root = cq.from(Ygpar.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(cb.trim(root.get("com")), com.trim()));
        predicates.add(cb.equal(root.get("mod"), "REPORT"));
        predicates.add(cb.equal(root.get("property"), "logo"));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
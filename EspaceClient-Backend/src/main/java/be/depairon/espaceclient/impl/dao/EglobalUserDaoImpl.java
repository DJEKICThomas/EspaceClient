package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.EglobalUserDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.EglobalUser;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EglobalUserDaoImpl extends GenericDao implements EglobalUserDao {

    @Override
    public List<EglobalUser> getByEmail(String email){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EglobalUser> cq = cb.createQuery(EglobalUser.class);
        Root<EglobalUser> root = cq.from(EglobalUser.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("email"), email));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList();

    }

    @Override
    public EglobalUser getByEmailAndCompany(String email, String usercmp){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EglobalUser> cq = cb.createQuery(EglobalUser.class);
        Root<EglobalUser> root = cq.from(EglobalUser.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("email"), email));
        predicates.add(cb.equal(cb.trim(root.get("usercmp")), usercmp.trim()));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

    }


}

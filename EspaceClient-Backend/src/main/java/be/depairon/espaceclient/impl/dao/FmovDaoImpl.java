package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.FmovDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.Fmov;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FmovDaoImpl extends GenericDao implements FmovDao {


    @Override
    public List<Fmov> findInvoicesByNcs(String ncs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fmov> cq = cb.createQuery(Fmov.class);
        Root<Fmov> root = cq.from(Fmov.class);

        // Construction des filtres (Predicates)
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("fmovNcs"), ncs));
        predicates.add(cb.equal(root.get("fmovTyp"), "C"));
        predicates.add(cb.notEqual(root.get("fmovRec"), "99"));

        // Application des filtres et du tri
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("fmovDatDoc")));

        return entityManager.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    @Override
    public List<Fmov> findUnpaidInvoicesByNcs(String ncs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fmov> cq = cb.createQuery(Fmov.class);
        Root<Fmov> root = cq.from(Fmov.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("fmovNcs"), ncs));
        predicates.add(cb.equal(root.get("fmovTyp"), "C"));
        predicates.add(cb.notEqual(root.get("fmovRec"), "99"));

        // Filtre pour les non payées : Lettrage vide ou null
        Predicate lettrageVide = cb.equal(cb.trim(root.get("fmovLet")), "");
        Predicate lettrageNull = cb.isNull(root.get("fmovLet"));
        predicates.add(cb.or(lettrageVide, lettrageNull));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("fmovDatDoc")));

        return entityManager.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}
package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.ScmAllDocDao;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.entity.Fmov;
import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.entity.ScmAllDocDet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScmAllDocDaoImpl extends GenericDao implements ScmAllDocDao{

    @Override
    public List<ScmAllDoc> findDocsByNcsAndType(String ncs, String typeDoc, String docTyc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScmAllDoc> cq = cb.createQuery(ScmAllDoc.class);
        Root<ScmAllDoc> root = cq.from(ScmAllDoc.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("ncs"), ncs));

        if (typeDoc != null) {
            if (typeDoc.startsWith("!")) {
                predicates.add(cb.notEqual(root.get("docTyc"), typeDoc.substring(1)));
            } else {
                predicates.add(cb.equal(root.get("docTyc"), typeDoc));
            }
        }

        if (docTyc != null) {
            if (docTyc.startsWith("!")) {
                predicates.add(cb.notEqual(root.get("docTyc"), docTyc.substring(1)));
            } else {
                predicates.add(cb.equal(root.get("docTyc"), docTyc));
            }
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("dateCreation")));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList();
    }

    @Override
    public List<ScmAllDoc> findClosedDocsByNcs(String ncs){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScmAllDoc> cq = cb.createQuery(ScmAllDoc.class);
        Root<ScmAllDoc> root = cq.from(ScmAllDoc.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("ncs"), ncs));
        predicates.add(cb.equal(root.get("docTyc"), "N"));
        predicates.add(cb.equal(root.get("typeDoc"), "C"));
        predicates.add(cb.greaterThan(root.get("dateCloture"), 0));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("dateCloture")));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList();
    }

    @Override
    public List<ScmAllDoc> findDocsByNcsAndDateRange(String ncs, int from, int to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScmAllDoc> cq = cb.createQuery(ScmAllDoc.class);
        Root<ScmAllDoc> root = cq.from(ScmAllDoc.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("ncs"), ncs));
        predicates.add(cb.between(root.get("dateCreation"), from, to));
        // Exclure les offres (Q) et les documents supprimés (D)
        predicates.add(cb.notEqual(root.get("docTyc"), "Q"));
        predicates.add(cb.notEqual(root.get("statut"), "D"));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(root.get("dateCreation")));

        return entityManager.createQuery(cq)
                .setMaxResults(MAX_RESULTS)
                .getResultList();
    }

    @Override
    public List<ScmAllDocDet> findDetailsByDocument(String document) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScmAllDocDet> cq = cb.createQuery(ScmAllDocDet.class);
        Root<ScmAllDocDet> root = cq.from(ScmAllDocDet.class);

        List<Predicate> predicates = new ArrayList<>();

        // Lien entre l'entête et le détail via le numéro de document
        predicates.add(cb.equal(root.get("document"), document));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}

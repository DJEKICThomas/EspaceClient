package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.DocumentDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.DocAtt;
import be.depairon.espaceclient.entity.DocC;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DocumentDaoImpl extends GenericDao implements DocumentDao {

    @Override
    public List<DocAtt> findDocAttByDocNumberAndNcs(String docNumber, String ncs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocAtt> cq = cb.createQuery(DocAtt.class);

        // On définit les deux tables
        Root<DocAtt> docAtt = cq.from(DocAtt.class);
        Root<DocC> docc = cq.from(DocC.class);

        List<Predicate> predicates = new ArrayList<>();

        // Relie les deux tables par le numéro de document
        predicates.add(cb.equal(docAtt.get("docDok"), docc.get("doccDok")));

        // FILTRE 1
        predicates.add(cb.equal(docAtt.get("docDok"), docNumber));

        // FILTRE 2
        predicates.add(cb.equal(docc.get("doccNcs"), ncs));

        // FILTRE 3
        predicates.add(cb.like(docAtt.get("docAtt"), "%.pdf"));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}

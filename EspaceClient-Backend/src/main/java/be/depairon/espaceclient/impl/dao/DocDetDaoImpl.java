package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.DocDetDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.DocDet;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DocDetDaoImpl extends GenericDao implements DocDetDao {

    @Override
    public List<DocDet> findByDocNumber(String docNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocDet> cq = cb.createQuery(DocDet.class);
        Root<DocDet> root = cq.from(DocDet.class);

        // Construction du filtre sur le numéro de document
        List<Predicate> predicates = new ArrayList<>();

        // DOC_DET_DOK correspond au champ docDetDok dans l'entité
        predicates.add(cb.equal(cb.trim(root.get("docDetDok")), docNumber.trim()));

        // Exclure les lignes de type 'T'
        predicates.add(cb.or(
                root.get("docDetTyi").isNull(),
                cb.notEqual(cb.trim(root.get("docDetTyi")), "T")
        ));

        // Application du filtre
        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
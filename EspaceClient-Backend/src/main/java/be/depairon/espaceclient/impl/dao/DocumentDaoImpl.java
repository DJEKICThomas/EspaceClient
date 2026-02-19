package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.DocumentDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.DocAtt;
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
    public List<DocAtt> findDocAttByDocNumber(String docNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocAtt> cq = cb.createQuery(DocAtt.class);
        Root<DocAtt> root = cq.from(DocAtt.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("docDok"), docNumber));

        predicates.add(cb.like(root.get("docAtt"), "%.pdf%"));

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}

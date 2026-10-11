package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.InfocentreDao;
import be.depairon.espaceclient.entity.Infocentre;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class InfocentreDaoImpl extends GenericDao implements InfocentreDao {

    @Override
    public List<Infocentre> findByNcs(String ncs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Infocentre> cq = cb.createQuery(Infocentre.class);
        Root<Infocentre> root = cq.from(Infocentre.class);

        cq.where(cb.and(
                cb.equal(root.get("ncs"), ncs)
                // TODO décommenter une fois le champ visibilité ajouté en BDD (dropdown ERP)
                // , cb.equal(root.get("visibility"), "Y")
        ));
        cq.orderBy(cb.desc(root.get("dateCreation")));

        return entityManager.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}

package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.CusCtrDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.CusCtr;
import org.springframework.stereotype.Repository;

import org.apache.commons.lang3.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CusCtrDaoImpl extends GenericDao implements CusCtrDao {

    @Override
    public List<CusCtr> findByNcsAndNature(String ncs, String nature, String company) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CusCtr> cq = cb.createQuery(CusCtr.class);
        Root<CusCtr> root = cq.from(CusCtr.class); // On part de l'entité

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(cb.trim(root.get("ncs")), ncs));

        // Filtre par nature uniquement si une valeur est passée.
        // Si nature est null ou vide, on retourne toutes les natures (pas de filtrage).
        if (StringUtils.isNotBlank(nature)) {
            predicates.add(cb.equal(cb.trim(root.get("nature")), nature));
        }

        // CUS_CTR est une table globale (eGlobal F256 = 2) : pas de filtre COM.
        // Le paramètre 'company' est conservé dans la signature pour compatibilité,
        // mais n'est plus utilisé pour filtrer.
        cq.where(predicates.toArray(new Predicate[0]));

        cq.orderBy(cb.desc(root.get("startDate")));

        return entityManager.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}

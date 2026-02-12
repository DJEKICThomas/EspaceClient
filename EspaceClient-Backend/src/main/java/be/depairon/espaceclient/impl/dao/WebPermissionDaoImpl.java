package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.WebPermissionDao;
import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.Webrole;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class WebPermissionDaoImpl extends GenericDao implements WebPermissionDao {

    @Override
    public Webprofile getPermission(String profile) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webprofile> cq = cb.createQuery(Webprofile.class);
        Root<Webprofile> root = cq.from(Webprofile.class);

        cq.where(cb.equal(root.get("name"), profile));

        List<Webprofile> results = entityManager.createQuery(cq).getResultList();

        // On renvoie l'objet unique, ou null si rien n'est trouvé
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<WebUserRole> getUserPermission(String userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WebUserRole> cq = cb.createQuery(WebUserRole.class);
        Root<WebUserRole> root = cq.from(WebUserRole.class);

        cq.where(cb.equal(root.get("userid"), userId));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Webrole> getAllUserPermissions(String userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Webrole> cq = cb.createQuery(Webrole.class);
        Root<Webrole> root = cq.from(Webrole.class);

        // Tri par description pour que la liste soit propre
        cq.orderBy(cb.asc(root.get("comment")));

        return entityManager.createQuery(cq).getResultList();
    }
}
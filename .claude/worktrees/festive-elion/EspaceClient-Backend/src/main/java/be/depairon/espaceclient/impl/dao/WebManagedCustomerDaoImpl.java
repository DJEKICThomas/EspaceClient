package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.WebManagedCustomerDao;
import be.depairon.espaceclient.entity.ManagedCustomer;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WebManagedCustomerDaoImpl extends GenericDao implements WebManagedCustomerDao {

    @Override
    public List<ManagedCustomer> getList(String userId) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ManagedCustomer> query = builder.createQuery(ManagedCustomer.class);
        Root<ManagedCustomer> root = query.from(ManagedCustomer.class);
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)) {
            predicates.add(builder.equal(root.get("userId"), userId));
        }
        predicates.add(builder.equal(root.get("customer").get("cusSts"), ""));
        CriteriaQuery<ManagedCustomer> cq = query.select(root)
                .distinct(true)
                .where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}
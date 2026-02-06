package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.ManagedCustomer;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDaoImpl extends GenericDao implements CustomerDao {

    @Override
    public List<Customer> getList() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        CriteriaQuery<Customer> cq = query.select(root)
                .where(builder.and(
                        builder.equal(root.get("cusSts"), ""),
                        builder.equal(root.get("cusTyp"), "C"),
                        builder.equal(root.get("cusNda"), 0d),
                        root.get("cusNcs").isNotNull()
                ))
                .distinct(true)
                .orderBy(builder.asc(root.get("cusNm1")));
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    @Override
    public List<Customer> getFilteredList(String filter) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        CriteriaQuery<Customer> cq = query.select(root)
                .where(
                        builder.and(
                                builder.equal(root.get("cusSts"), ""),
                                builder.equal(root.get("cusTyp"), "C"),
                                builder.or(
                                        builder.like(builder.upper(root.get("cusNm1")), filter.toUpperCase() + "%"),
                                        builder.like(builder.upper(root.get("cusNcs")), filter.toUpperCase() + "%")),
                                builder.equal(root.get("cusNda"), 0d)))
                .distinct(true)
                .orderBy(builder.asc(root.get("cusNm1")));
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    @Override
    public Customer getByNcsNda(String ncs, int nda) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        CriteriaQuery<Customer> cq = query.select(root)
                .where(builder.and(
                        builder.equal(root.get("cusNcs"), ncs),
                        builder.equal(root.get("cusNda"), String.valueOf(nda)),
                        builder.equal(root.get("cusTyp"), "C"),
                        builder.equal(root.get("cusSts"), "")))
                .orderBy(builder.asc(root.get("cusNcs")));
        return session.createQuery(cq)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

    }

    @Override
    public Customer getById(long id) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        CriteriaQuery<Customer> cq = query.select(root)
                .where(builder.equal(root.get("id"), id));
        return session.createQuery(cq).getSingleResult();
    }

    @Override
    public List<String> getNcsListByUserId(String userId) {
        String hql = "SELECT mc FROM ManagedCustomer mc WHERE mc.userId = :userId";

        List<ManagedCustomer> managedCustomers = entityManager.createQuery(hql, ManagedCustomer.class)
                .setParameter("userId", userId)
                .getResultList();

        List<String> ncsList = new ArrayList<>();
        for (ManagedCustomer mc : managedCustomers) {
            if (mc.getCustomer() != null) {
                ncsList.add(mc.getCustomer().getCusNcs());
            }
        }

        return ncsList;
    }
}

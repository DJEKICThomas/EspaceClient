package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.CusCtr;
import be.depairon.espaceclient.entity.Fmov;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.entity.ScmAllDoc;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDaoImpl extends GenericDao implements CustomerDao {

    @Override
    public List<Customer> getList(String company) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("cusSts"), ""));
        predicates.add(builder.equal(root.get("cusTyp"), "C"));
        predicates.add(builder.equal(root.get("cusNda"), 0d));
        predicates.add(root.get("cusNcs").isNotNull());

        // Filtrage par société désactivé : le scoping se fait via WEBMANAGCUS
        // (NCS attribués à l'utilisateur). À réactiver si une autre logique est définie.
        // if (StringUtils.isNotBlank(company)) {
        //     predicates.add(buildCompanyScopePredicate(builder, query, root, company));
        // }

        CriteriaQuery<Customer> cq = query.select(root)
                .where(predicates.toArray(new Predicate[0]))
                .distinct(true)
                .orderBy(builder.asc(root.get("cusNm1")));
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    @Override
    public List<Customer> getFilteredList(String filter, String company) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("cusSts"), ""));
        predicates.add(builder.equal(root.get("cusTyp"), "C"));
        predicates.add(builder.equal(root.get("cusNda"), 0d));
        predicates.add(builder.or(
                builder.like(builder.upper(root.get("cusNm1")), filter.toUpperCase() + "%"),
                builder.like(builder.upper(root.get("cusNcs")), filter.toUpperCase() + "%")
        ));

        // Filtrage par société désactivé : le scoping se fait via WEBMANAGCUS
        // (NCS attribués à l'utilisateur). À réactiver si une autre logique est définie.
        // if (StringUtils.isNotBlank(company)) {
        //     predicates.add(buildCompanyScopePredicate(builder, query, root, company));
        // }

        CriteriaQuery<Customer> cq = query.select(root)
                .where(predicates.toArray(new Predicate[0]))
                .distinct(true)
                .orderBy(builder.asc(root.get("cusNm1")));
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    /**
     * Construit la condition qui vérifie qu'un client a au moins une ligne
     * liée à la société donnée, dans l'une des 3 tables métier :
     *   - FMOV (mouvements / factures)
     *   - CUS_CTR (abonnements)
     *   - SCMALLDOC (commandes + offres)
     */
    private Predicate buildCompanyScopePredicate(CriteriaBuilder builder,
                                                 CriteriaQuery<?> query,
                                                 Root<Customer> customerRoot,
                                                 String company) {
        return builder.or(
                existsInFmov(builder, query, customerRoot, company),
                existsInCusCtr(builder, query, customerRoot, company),
                existsInScmAllDoc(builder, query, customerRoot, company)
        );
    }

    private Predicate existsInFmov(CriteriaBuilder builder, CriteriaQuery<?> query,
                                   Root<Customer> customerRoot, String company) {
        Subquery<Long> sub = query.subquery(Long.class);
        Root<Fmov> fmovRoot = sub.from(Fmov.class);
        sub.select(builder.literal(1L)).where(
                builder.equal(fmovRoot.get("fmovNcs"), customerRoot.get("cusNcs")),
                builder.equal(builder.trim(fmovRoot.get("fmovCom")), company.trim())
        );
        return builder.exists(sub);
    }

    private Predicate existsInCusCtr(CriteriaBuilder builder, CriteriaQuery<?> query,
                                     Root<Customer> customerRoot, String company) {
        Subquery<Long> sub = query.subquery(Long.class);
        Root<CusCtr> ctrRoot = sub.from(CusCtr.class);
        sub.select(builder.literal(1L)).where(
                builder.equal(ctrRoot.get("ncs"), customerRoot.get("cusNcs")),
                builder.equal(builder.trim(ctrRoot.get("com")), company.trim())
        );
        return builder.exists(sub);
    }

    private Predicate existsInScmAllDoc(CriteriaBuilder builder, CriteriaQuery<?> query,
                                        Root<Customer> customerRoot, String company) {
        Subquery<Long> sub = query.subquery(Long.class);
        Root<ScmAllDoc> scmRoot = sub.from(ScmAllDoc.class);
        sub.select(builder.literal(1L)).where(
                builder.equal(scmRoot.get("ncs"), customerRoot.get("cusNcs")),
                builder.equal(builder.trim(scmRoot.get("com")), company.trim())
        );
        return builder.exists(sub);
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
                        builder.equal(root.get("cusNda"), nda),
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
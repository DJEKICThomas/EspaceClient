package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.UserDao;
import be.depairon.espaceclient.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UserDaoImpl extends GenericDao implements UserDao {

    @Override
    public List<User> getList(String customer) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> cq = query.select(root);
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }

    @Override
    public User getByUsername(String username) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> cq = query.select(root)
                .where(builder.and(
                        builder.equal(builder.trim(root.get("userid")), username.trim()),
                        builder.equal(root.get("active"), 1),
                        builder.greaterThanOrEqualTo(root.get("usrexpdate"), LocalDate.now())
                ));
        return session.createQuery(cq)
                .uniqueResultOptional()
                .orElse(null);
    }

    @Override
    public User getByUsernameNoCondition(String username) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> cq = query.select(root)
                .where(builder.equal(builder.trim(root.get("userid")), username.trim()));
        return session.createQuery(cq)
                .uniqueResultOptional()
                .orElse(null);
    }

    @Override
    public String getEGlobalUserId(User user) {
        Session session = getSession();
        return (String) session.createNativeQuery("select USERID from YG51TT.USR u where EMAIL = :mail ")
                .setParameter("mail", user.getUserid())
                .uniqueResult();
    }

    @Override
    public List<User> getByGestionnaireCompanies(String userId) {
        Session session = getSession();
        return session.createQuery("select distinct usr " +
                        "from ManagedCustomer manag " +
                        "join User usr on manag.userId = usr.userid " +
                        "where manag.customer.cusNcs in (select customer.cusNcs " +
                        "              from ManagedCustomer " +
                        "              where userId = :userId) " +
                        "and usr.active = 1 and usr.profile not in ('ADMINISTRATEUR', 'DEPAIRON') ", User.class)
                .setParameter("userId", userId)
                .list();
    }

    @Override
    public User getByResetToken(String resetToken){
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        CriteriaQuery<User> cq = query.select(root).where(builder.equal(root.get("resetToken"), resetToken));

        return session.createQuery(cq).uniqueResultOptional().orElse(null);
    }
}

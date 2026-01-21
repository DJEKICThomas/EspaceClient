package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.DotationDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.Dotation;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DotationDaoImpl extends GenericDao implements DotationDao {

    @Override
    public List<Dotation> getList(String customer) {
        Session session = getSession();
        return session.createQuery("select dot from Dotation as dot where dot.client = :customer " +
                " order by dot.description", Dotation.class)
                .setParameter("customer", customer)
                .getResultList();
    }
}

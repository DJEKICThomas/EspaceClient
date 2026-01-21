package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.OrderDao;
import be.depairon.espaceclient.entity.Order;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends GenericDao implements OrderDao {

    @Override
    public List<Order> getList(String customer) {
        Session session = getSession();
        String query = "select NCS, NDA, CARRIER, IIN, TYPE, QUANTITY, " +
                " DATE, CODE, o.ID, NCSID, ARTID, FROM, DELIVERY, " +
                " trim(gen.GEN_SRC_ARG) as reference  " +
                " from YGDEPAIRON.WEBORDER as o " +
                " left join YGDEPAIRON.GEN_SRC as gen on gen.GEN_SRC_TYP = 'DC' and gen.GEN_SRC_SRC = 'S01' " +
                " and trim(gen.GEN_SRC_KEY) = concat('C', cast(o.CODE as char(9))) " +
                " where o.NCS = :customer";
        return session.createNativeQuery(query, Order.class)
                .setParameter("customer", customer)
                .getResultList();
    }

}

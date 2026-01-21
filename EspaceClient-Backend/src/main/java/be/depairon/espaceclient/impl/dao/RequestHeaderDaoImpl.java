package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.RequestHeaderDao;
import be.depairon.espaceclient.entity.RequestHeader;
import be.depairon.espaceclient.enumeration.RequestFlag;
import be.depairon.espaceclient.enumeration.RequestType;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.SessionScope;

import java.util.List;

@Repository
public class RequestHeaderDaoImpl extends GenericDao implements RequestHeaderDao {
    @Override
    public List<RequestHeader> getList(List<RequestType> requestTypeList) {
        Session session = getSession();
        return session.createQuery("select rh from RequestHeader as rh" +
                        " left outer join rh.requestDetail as rd " +
                        " where rh.customer.cusTyp = 'C' and rd.typ in (:requestTypeList)", RequestHeader.class)
                .setParameterList("requestTypeList", requestTypeList)
                .getResultList();
    }

    @Override
    public List<Object[]> getNativeListDetail(RequestFlag flag, RequestType type) {
        Session session = getSession();
        return session.createNativeQuery("select rd.ID, rh.CUPBOARD, rh.CASSETTE, rh.CARRIER, rh.SITE, rd.TEXT, cs.CUS_SAL_RES, rd.TYP, rd.TEXT_GES, rh.DATE, rh.USER, rh.TICKET, rd.FLAG, wu.NAME, wu.FIRSTNAME, coalesce(c.CUS_NCS, ''), coalesce(c.CUS_NDA, 0) " +
                "from YGDEPAIRON.REQUEST_DETAIL as rd " +
                "         left outer join YGDEPAIRON.REQUEST_HEADER rh on rh.ID = rd.REQUESTHEADER_ID " +
                "         left outer join YGDEPAIRON.WEBUSER wu on wu.USERID = rh.USER " +
                "         left outer join YGDEPAIRON.CUS c on rh.NCSID = c.ID " +
                "         left outer join YGDEPAIRON.CUS_SAL cs on cs.ID = " +
                "                                                  (select cs1.id " +
                "                                                   from YGDEPAIRON.CUS_SAL cs1 " +
                "                                                   where cs1.CUS_SAL_NCS = c.CUS_NCS " +
                "                                                     and cs1.CUS_SAL_NDA = c.CUS_NDA " +
                "                                                     and cs1.CUS_SAL_TYP = c.CUS_TYP " +
                "                                                     order by cs1.ID desc " +
                "                                                       fetch first 1 row only) " +
                "where rd.flag = :flag " +
                "  and rd.typ <> :typ " +
                "order by rh.DATE desc, rd.id desc ").setParameter("flag", flag.toString()).setParameter("typ", type.toString()).getResultList();
    }

    @Override
    public List<Object[]> getNativeListDetail(RequestFlag flag) {
        Session session = getSession();
        return session.createNativeQuery("select rd.ID, rh.CUPBOARD, rh.CASSETTE, rh.CARRIER, rh.SITE, rd.TEXT, cs.CUS_SAL_RES, rd.TYP, rd.TEXT_GES, rh.DATE, rh.USER, rh.TICKET, rd.FLAG, wu.NAME, wu.FIRSTNAME, coalesce(c.CUS_NCS, ''), coalesce(c.CUS_NDA, 0) " +
                "from YGDEPAIRON.REQUEST_DETAIL as rd " +
                "         left outer join YGDEPAIRON.REQUEST_HEADER rh on rh.ID = rd.REQUESTHEADER_ID " +
                "         left outer join YGDEPAIRON.WEBUSER wu on wu.USERID = rh.USER " +
                "         left outer join YGDEPAIRON.CUS c on rh.NCSID = c.ID " +
                "         left outer join YGDEPAIRON.CUS_SAL cs on cs.ID = " +
                "                                                  (select cs1.id " +
                "                                                   from YGDEPAIRON.CUS_SAL cs1 " +
                "                                                   where cs1.CUS_SAL_NCS = c.CUS_NCS " +
                "                                                     and cs1.CUS_SAL_NDA = c.CUS_NDA " +
                "                                                     and cs1.CUS_SAL_TYP = c.CUS_TYP " +
                "                                                     order by cs1.ID desc " +
                "                                                       fetch first 1 row only) " +
                "where rd.flag = :flag or rd.flag = 'TREATED' " +
                "order by rh.DATE desc, rd.id desc ").setParameter("flag", flag.toString()).getResultList();
    }

    @Override
    public List<Object[]> getNativeUserListDetail(String userMail) {
        Session session = getSession();
        return session.createNativeQuery("select rd.ID, rh.CUPBOARD, rh.CASSETTE, rh.CARRIER, rh.SITE, rd.TEXT, cs.CUS_SAL_RES, rd.TYP, rd.TEXT_GES, rh.DATE, rh.USER, rh.TICKET, rd.FLAG, wu.NAME, wu.FIRSTNAME, coalesce(c.CUS_NCS, ''), coalesce(c.CUS_NDA, 0) " +
                "from YGDEPAIRON.REQUEST_DETAIL as rd " +
                "         left outer join YGDEPAIRON.REQUEST_HEADER rh on rh.ID = rd.REQUESTHEADER_ID " +
                "         left outer join YGDEPAIRON.WEBUSER wu on wu.USERID = rh.USER " +
                "         left outer join YGDEPAIRON.CUS c on rh.NCSID = c.ID " +
                "         left outer join YGDEPAIRON.CUS_SAL cs on cs.ID = " +
                "                                                  (select cs1.id " +
                "                                                   from YGDEPAIRON.CUS_SAL cs1 " +
                "                                                   where cs1.CUS_SAL_NCS = c.CUS_NCS " +
                "                                                     and cs1.CUS_SAL_NDA = c.CUS_NDA " +
                "                                                     and cs1.CUS_SAL_TYP = c.CUS_TYP " +
                "                                                     order by cs1.ID desc " +
                "                                                       fetch first 1 row only) " +
                "where rh.USER = :userMail " +
                "order by rh.DATE desc, rd.id desc ").setParameter("userMail", userMail).getResultList();
    }
}

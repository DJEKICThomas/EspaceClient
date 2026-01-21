package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.EntryControlDao;
import be.depairon.espaceclient.dao.GenericDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntryControlDaoImpl extends GenericDao implements EntryControlDao {

    private final Logger log = LoggerFactory.getLogger(EntryControlDaoImpl.class);

    @Override
    public List<Object[]> getNativeList(String customer, int hisYear) {
        List<Object[]> list = new ArrayList<>();
        try {
            LocalDate lastYear = LocalDate.now().minusYears(hisYear);
            Session session = getSession();
            session.setDefaultReadOnly(true);
            list = session.createNativeQuery("select jtd.ID, cus.CUS_NM1, cp.CUS_PORT_PERSVET, artDes.ART_DES_DES, jtd.datetime, bar.arm, " +
                            " bar.cass, jtd.barcode, bar.lovepe, (max(trim(cp.CUS_PORT_NOM)) || ' ' || max(trim(cp.CUS_PORT_PRENOM))) as nom " +
                            "from YGDEPAIRON.JENTRACKDEP as jtd " +
                            "inner join YGDEPAIRON.BARCODE as bar on jtd.barcode = bar.barcode and bar.flag = '' " +
                            "inner join YGDEPAIRON.ART as art on bar.iin = art.ART_IIN " +
                            "inner join YGDEPAIRON.ART_XDI as xdi on art.ART_IIN = xdi.ART_XDI_IIN and xdi.ART_XDI_EXJEN <> 'Y' " +
                            "left join YGDEPAIRON.ART_DES as artDes on art.ART_IIN = artDes.ART_DES_IIN and artDes.ART_DES_LNG = 'fr' " +
                            "inner join YGDEPAIRON.CUS as cus on bar.NCS = cus.CUS_NCS  and bar.NDA = CUS_NDA and CUS_TYP = 'C' and cus.CUS_STS = '' " +
                            "inner join YGDEPAIRON.CUS_PORT as cp on cp.ID = bar.PORTEURID " +
                            "where jtd.datetime >= :date and bar.ncs = :customer and jtd.POSITIONID like 'CE%' " +
                            "group by jtd.ID, cus.CUS_NM1, cp.CUS_PORT_PERSVET, artDes.ART_DES_DES, jtd.datetime, bar.ARM, " +
                            "         bar.CASS, jtd.barcode, bar.lovepe " +
                            "order by jtd.datetime desc, artDes.ART_DES_DES, cp.CUS_PORT_PERSVET ")
                    .setParameter("customer", customer)
                    .setParameter("date", lastYear)
//                    .setMaxResults(1000)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return list;
    }

    @Override
    public List<Object[]> getNativeList(String customer, Long skip, Long take, String whereClause, String orderByClause) {
        whereClause += (StringUtils.isBlank(whereClause) ? " where " : " and ") + " ec.date >= :date and ec.ncs = :customer ";
        orderByClause = StringUtils.defaultIfBlank(orderByClause, "order by ec.date desc, artDes.ART_DES_DES, ec.CARRIER");
        List<Object[]> list = new ArrayList<>();
        try {
            LocalDate lastYear = LocalDate.now().minusYears(10); // TODO: Change by eGlobal parameter
            Session session = getSession();
            session.setDefaultReadOnly(true);
            list = session.createNativeQuery("select ec.ID, cus.CUS_NM1, EC.CARRIER, artDes.ART_DES_DES, ec.DATE, ec.cupboard, " +
                            " ec.cassette, ec.barcode, bar.lovepe " +
                            "from YGDEPAIRON.WEBENTRYCTRL as ec " +
                            "inner join YGDEPAIRON.ART as art on ec.ARTID = art.ID " +
                            "inner join YGDEPAIRON.ART_XDI as xdi on art.ART_IIN = xdi.ART_XDI_IIN and xdi.ART_XDI_EXJEN <> 'Y' " +
                            "left join YGDEPAIRON.ART_DES as artDes on art.ART_IIN = artDes.ART_DES_IIN and artDes.ART_DES_LNG = 'fr' " +
                            "inner join YGDEPAIRON.CUS as cus on ec.NCSID = cus.ID and cus.CUS_STS = '' " +
                            "inner join YGDEPAIRON.BARCODE as bar on ec.barcode = bar.barcode and bar.flag = '' " +
                            whereClause +
                            "group by ec.ID, cus.CUS_NM1, EC.CARRIER, artDes.ART_DES_DES, ec.DATE, ec.cupboard, " +
                            "         ec.cassette, ec.barcode, bar.lovepe " +
                            orderByClause +
                            " limit :skip, :take")
                    .setParameter("customer", customer)
                    .setParameter("date", lastYear)
                    .setParameter("skip", skip)
                    .setParameter("take", take)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return list;
    }

    @Override
    public Integer countNativeList(String customer, String whereClause) {
        whereClause += (StringUtils.isBlank(whereClause) ? " where " : " and ") + " ec.date >= :date and ec.ncs = :customer ";
        Integer result = 0;
        try {
            LocalDate lastYear = LocalDate.now().minusYears(10); // TODO: Change by eGlobal parameter
            Session session = getSession();
            session.setDefaultReadOnly(true);
            result = (Integer) session.createNativeQuery("select count(ec.ID) " +
                            "from YGDEPAIRON.WEBENTRYCTRL as ec " +
                            "inner join YGDEPAIRON.ART as art on ec.ARTID = art.ID " +
                            "inner join YGDEPAIRON.ART_XDI as xdi on art.ART_IIN = xdi.ART_XDI_IIN and xdi.ART_XDI_EXJEN <> 'Y' " +
                            "left join YGDEPAIRON.ART_DES as artDes on art.ART_IIN = artDes.ART_DES_IIN and artDes.ART_DES_LNG = 'fr' " +
                            "inner join YGDEPAIRON.CUS as cus on ec.NCSID = cus.ID and cus.CUS_STS = '' " +
                            "inner join YGDEPAIRON.BARCODE as bar on ec.barcode = bar.barcode and bar.flag = '' " +
                            whereClause)
                    .setParameter("customer", customer)
                    .setParameter("date", lastYear)
                    .uniqueResult();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return result;
    }
}
package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.StatisticDao;
import be.depairon.espaceclient.entity.Statistic;
import be.depairon.espaceclient.entity.query.StatisticHeader;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticDaoImpl extends GenericDao implements StatisticDao {

    @Override
    public List<Statistic> getList(String customer, int fromYear) {
        Session session = getSession();
        String query = "select stat.* from YGDEPAIRON.WEBSTATISTIC as stat " +
                " inner join YGDEPAIRON.CUS as cus on stat.NCSID = cus.ID and cus.CUS_TYP = 'C' and cus.CUS_STS = '' " +
                " and cus.CUS_COM = :com and cus.CUS_DEP = :dep " +
                " inner join YGDEPAIRON.ART as art on stat.ARTID = art.ID and art.ART_GR1 <> :artGr1 " +
                " where stat.YEAR >= :fromYear and stat.NCS = :ncs and stat.COUNTER > 0";
        return session.createNativeQuery(query, Statistic.class)
                .setParameter("artGr1", "1")
                .setParameter("com", "DEP")
                .setParameter("dep", "DEP")
                .setParameter("fromYear", fromYear)
                .setParameter("ncs", customer)
                .getResultList();
    }

    @Override
    public List<Statistic> getWebStatisticList(long ncsid, long artid, int year) {
        Session session = getSession();
        String query = "select stat.* from YGDEPAIRON.WEBSTATISTIC as stat " +
                " inner join YGDEPAIRON.CUS as cus on stat.NCSID = cus.ID and cus.CUS_TYP = 'C' and cus.CUS_STS = '' " +
                " and cus.CUS_COM = :com and cus.CUS_DEP = :dep " +
                " inner join YGDEPAIRON.ART as art on stat.ARTID = art.ID and art.ART_GR1 <> :artGr1 " +
                " where stat.YEAR = :year and stat.NCSID = :ncsid and stat.ARTID = :artid";
        return session.createNativeQuery(query, Statistic.class)
                .setParameter("artGr1", "1")
                .setParameter("com", "DEP")
                .setParameter("dep", "DEP")
                .setParameter("year", year)
                .setParameter("ncsid", ncsid)
                .setParameter("artid", artid)
                .getResultList();
    }

    //(StringUtils.equals(nature, "L") ? "=" : "<>")
    @Override
    public List<StatisticHeader> getList2(String customer, String nature, int yearFrom) {
        List<StatisticHeader> statisticHeaderList = new ArrayList<>();
        Session session = getSession();
        String query = "select NCSID, ARTID, MAX(SITE), MAX(ART_DES), SUM(SUM_YEAR1), SUM(SUM_YEAR2), SUM(SUM_YEAR3) " +
                " from (select w.NCSID AS NCSID, w.ARTID AS ARTID, MAX(coalesce(c.CUS_NM1, '')) AS SITE, MAX(coalesce(ad.ART_DES_DES, '')) AS ART_DES, sum(w.COUNTER1) AS SUM_YEAR1, 0 AS SUM_YEAR2, 0 AS SUM_YEAR3 " +
                " from YGDEPAIRON.WEBSTATISTIC2 w " +
                " join YGDEPAIRON.CUS c on c.ID = w.NCSID and c.CUS_NDA = w.NDA and c.CUS_TYP = 'C' and c.CUS_STS = '' " +
                " join YGDEPAIRON.ART as art on w.ARTID = art.ID and art.ART_GR1 <> 1 " +
                " left outer join YGDEPAIRON.ART_DES ad on ad.ART_DES_IIN = w.IIN and ad.ART_DES_LNG = 'fr' " +
                " where NCS = :ncs and YEAR1 = :yearFrom and art.ART_GR1 = :nature " +
                " group by w.NCSID, w.ARTID " +
                " union " +
                " select w.NCSID AS NCSID, w.ARTID AS ARTID, MAX(coalesce(c.CUS_NM1, '')) AS SITE, MAX(coalesce(ad.ART_DES_DES, '')) AS ART_DES, 0 AS SUM_YEAR1, sum(w.COUNTER2) AS SUM_YEAR2, 0 AS SUM_YEAR3 " +
                " from YGDEPAIRON.WEBSTATISTIC2 w " +
                " join YGDEPAIRON.CUS c on c.ID = w.NCSID and c.CUS_NDA = w.NDA and c.CUS_TYP = 'C' and c.CUS_STS = '' " +
                " join YGDEPAIRON.ART as art on w.ARTID = art.ID and art.ART_GR1 <> 1 " +
                " left outer join YGDEPAIRON.ART_DES ad on ad.ART_DES_IIN = w.IIN and ad.ART_DES_LNG = 'fr' " +
                " where NCS = :ncs and YEAR2 = :yearFrom2 and art.ART_GR1 = :nature " +
                " group by w.NCSID, w.ARTID " +
                " union " +
                " select w.NCSID AS NCSID, w.ARTID AS ARTID, MAX(coalesce(c.CUS_NM1, '')) AS SITE, MAX(coalesce(ad.ART_DES_DES, '')) AS ART_DES, 0 AS SUM_YEAR1, 0 AS SUM_YEAR2, sum(w.COUNTER3) AS SUM_YEAR3 " +
                " from YGDEPAIRON.WEBSTATISTIC2 w " +
                " join YGDEPAIRON.CUS c on c.ID = w.NCSID and c.CUS_NDA = w.NDA and c.CUS_TYP = 'C' and c.CUS_STS = '' " +
                " join YGDEPAIRON.ART as art on w.ARTID = art.ID and art.ART_GR1 <> 1 " +
                " left outer join YGDEPAIRON.ART_DES ad on ad.ART_DES_IIN = w.IIN and ad.ART_DES_LNG = 'fr' " +
                " where NCS = :ncs and YEAR3 = :yearFrom3 and art.ART_GR1 = :nature " +
                " group by w.NCSID, w.ARTID) as req group by ncsid, ARTID";

        List<Object[]> resultList = session.createNativeQuery(query)
                .setParameter("ncs", customer)
                .setParameter("yearFrom", yearFrom)
                .setParameter("yearFrom2", yearFrom + 1)
                .setParameter("yearFrom3", yearFrom + 2)
                .setParameter("nature", nature)
                .getResultList();

        for (Object[] objects : resultList) {
            statisticHeaderList.add(new StatisticHeader(((BigInteger) objects[0]).longValue(), ((BigInteger) objects[1]).longValue(), (String) objects[2], (String) objects[3], ((BigDecimal) objects[4]).longValue(), ((BigDecimal) objects[5]).longValue(), ((BigDecimal) objects[6]).longValue()));
        }

        return statisticHeaderList;
    }
}
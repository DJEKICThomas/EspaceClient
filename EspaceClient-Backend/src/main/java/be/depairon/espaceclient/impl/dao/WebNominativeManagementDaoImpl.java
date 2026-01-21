package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.WebNominativeManagementDao;
import be.depairon.espaceclient.entity.NominativeManagement;
import org.apache.commons.collections4.CollectionUtils;
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
public class WebNominativeManagementDaoImpl extends GenericDao implements WebNominativeManagementDao {

    @Override
    public List<Object[]> getList(String customer) {
        Session session = getSession();
        String query = "select nomin.ID as id, cus.CUS_NM1 as site, CARRIER, CUPBOARD, CASSETTE, artDes.ART_DES_DES as artDes, " +
                " QUANTITY, NCSID, ARTID, nomin.NATURE as nature, (max(trim(porteur.CUS_PORT_NOM)) || ' ' || max(trim(porteur.CUS_PORT_PRENOM))) as nom " +
                " from YGDEPAIRON.WEBNOMINM as nomin " +
                " inner join YGDEPAIRON.CUS_PORT as porteur on porteur.id = nomin.CUS_PORT_ID " +
                " inner join YGDEPAIRON.CUS as cus on porteur.CUS_PORT_NCS = cus.CUS_NCS " +
                " and porteur.CUS_PORT_NDA = cus.CUS_NDA and cus.CUS_STS = '' and cus.CUS_TYP = 'C' " +
                " inner join YGDEPAIRON.ART_DES as artDes on artDes.ART_DES_IIN = nomin.IIN and artDes.ART_DES_LNG = 'fr' " +
//                " inner join YGDEPAIRON.BARCODE as bar on " +
//                " bar.IIN = nomin.IIN and bar.NCS = nomin.NCS and bar.NDA = nomin.NDA and " +
//                " bar.ARM = nomin.CUPBOARD and bar.CASS = nomin.CASSETTE and bar.PORTEURID = porteur.ID and bar.FLAG = '' " +
                " where nomin.NCS = :customer " +
                " group by nomin.ID, cus.CUS_NM1, CARRIER, CUPBOARD, CASSETTE, artDes.ART_DES_DES, " +
                " QUANTITY, NCSID, ARTID, nomin.NATURE";
        return session.createNativeQuery(query)
                .setParameter("customer", customer)
                .getResultList();
    }

    @Override
    public List<NominativeManagement> getListByPorteur(Long cusId, String carrier) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<NominativeManagement> query = builder.createQuery(NominativeManagement.class);
        Root<NominativeManagement> root = query.from(NominativeManagement.class);
        // Filters
        List<Predicate> predicates = new ArrayList<>();
        if (cusId != null) {
            predicates.add(builder.equal(root.join("customer").get("id"), cusId));
        }
        if (StringUtils.isNotBlank(carrier)) {
            predicates.add(builder.like(builder.trim(builder.lower(root.get("carrier"))),
                    carrier.trim().toLowerCase() + "%"));
        }
        CriteriaQuery<NominativeManagement> cq = query.select(root);
        if (CollectionUtils.isNotEmpty(predicates)) {
            cq.where(predicates.toArray(new Predicate[]{}));
        }
        return session.createQuery(cq).getResultList();
    }
    @Override
    public List<Object[]> getNativeListByPorteur(Long cusId, String carrier) {
        Session session = getSession();
        String query = "select nomin.ID as id, cus.CUS_NM1 as site, CARRIER, CUPBOARD, CASSETTE, artDes.ART_DES_DES as artDes, " +
                " QUANTITY, NCSID, ARTID, bar.LOVEPE as nature, (max(trim(porteur.CUS_PORT_NOM)) || ' ' || max(trim(porteur.CUS_PORT_PRENOM))) as nom " +
                " from YGDEPAIRON.WEBNOMINM as nomin " +
                " inner join YGDEPAIRON.CUS_PORT as porteur on nomin.CUS_PORT_ID = porteur.ID " +
                " inner join YGDEPAIRON.CUS as cus on porteur.CUS_PORT_NCS = cus.CUS_NCS " +
                " and porteur.CUS_PORT_NDA = cus.CUS_NDA and cus.CUS_STS = '' and cus.CUS_TYP = 'C' " +
                " inner join YGDEPAIRON.ART_DES as artDes on artDes.ART_DES_IIN = nomin.IIN and artDes.ART_DES_LNG = 'fr' " +
                " inner join YGDEPAIRON.BARCODE as bar on " +
                " bar.IIN = nomin.IIN and bar.NCS = nomin.NCS and bar.NDA = nomin.NDA and " +
                " bar.ARM = nomin.CUPBOARD and bar.CASS = nomin.CASSETTE and bar.PORTEURID = porteur.ID and bar.FLAG = '' " +
                " where nomin.NCSID = :cusId and nomin.CARRIER = :carrier " +
                " group by nomin.ID, cus.CUS_NM1, CARRIER, CUPBOARD, CASSETTE, artDes.ART_DES_DES, " +
                " QUANTITY, NCSID, ARTID, bar.LOVEPE";
        return session.createNativeQuery(query)
                .setParameter("cusId", cusId)
                .setParameter("carrier", carrier)
                .getResultList();
    }
}

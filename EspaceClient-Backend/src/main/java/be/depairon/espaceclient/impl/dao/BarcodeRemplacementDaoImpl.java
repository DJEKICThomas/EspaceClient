package be.depairon.espaceclient.impl.dao;


import be.depairon.espaceclient.dao.BarcodeRemplacementDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.BarcodeRemplacement;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BarcodeRemplacementDaoImpl extends GenericDao implements BarcodeRemplacementDao {

    @Override
    public List<BarcodeRemplacement> getByBarcode(int barcode) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<BarcodeRemplacement> query = builder.createQuery(BarcodeRemplacement.class);
        Root<BarcodeRemplacement> root = query.from(BarcodeRemplacement.class);
        CriteriaQuery<BarcodeRemplacement> cq = query.select(root);
        if (barcode != 0) {
            cq.where(builder.equal(root.get("barcode"), barcode));
        }
        return session.createQuery(cq).setMaxResults(MAX_RESULTS).getResultList();
    }
}

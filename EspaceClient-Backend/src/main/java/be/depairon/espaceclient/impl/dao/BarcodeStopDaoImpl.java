package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.BarcodeStopDao;
import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.entity.query.CustomBarcodeStop;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BarcodeStopDaoImpl extends GenericDao implements BarcodeStopDao {

    @Override
    public List<CustomBarcodeStop> getListForNcs(String ncs) {
        /*
        return session.createQuery("select new be.depairon.espaceclient.entity.query.CustomBarcodeStop(bs, stops) from BarcodeStop as bs " +
                        " inner join Barcode as b on bs.barcode.barcode = b.barcode and b.flag = '' " +
                        " and b.com = 'DEP' and b.dep = 'DEP' and b.ncs = :customer " +
                        " inner join Stops as stops on stops.code = bs.stop and stops.activeWeb = 'Y'", CustomBarcodeStop.class)
                .setParameter("customer", ncs)
         */
        /*Session session = getSession();
        return session.createQuery("select new be.depairon.espaceclient.entity.query.CustomBarcodeStop(bs, bs.stops) from BarcodeStop as bs " +
                        " where bs.barcode.flag = ''  and bs.barcode.ncs = :customer and bs.stops.activeWeb = 'Y' ", CustomBarcodeStop.class)
                .setParameter("customer", ncs)
                .getResultList();*/

        Session session = getSession();
        List<CustomBarcodeStop> barcodeStops = new ArrayList<>();
/*        List<Object[]> customer = session.createQuery("select bs, stops from BarcodeStop as bs " +
                        " inner join Stops as stops on stops.code = bs.stop and stops.activeWeb = 'Y'" +
                        " where bs.barcode.flag = ''  and bs.barcode.ncs = :customer ")
                .setParameter("customer", ncs)
                .getResultList();*/
        List<Object[]> customer = session.createNativeQuery(
                        "select bs.id, b.barcode, bs.stop, bs.rem, stops.REM_WEB, ad.ART_DES_DES, cp.CUS_PORT_PERSVET, bs.dtb, bs.dtf, bs.dtr, bs.usc, c.CUS_NM1, cp.CUS_PORT_NOM, cp.CUS_PORT_PRENOM " +
                                "from YGDEPAIRON.BarcodeStop as bs " +
                                "         inner join YGDEPAIRON.Stops as stops on stops.code = bs.stop and stops.ACTIVE_WEB = 'Y' " +
                                "         inner join YGDEPAIRON.BARCODE as b on b.barcode = bs.BARCODE " +
                                "         inner join YGDEPAIRON.CUS as c on c.CUS_NCS = b.NCS and c.CUS_NDA = b.NDA " +
                                "         inner join YGDEPAIRON.CUS_PORT cp on cp.ID = b.PORTEURID " +
                                "         inner join YGDEPAIRON.ART_DES ad on ad.ART_DES_IIN = b.IIN and ART_DES_LNG = 'fr' " +
                                "where b.flag = '' and b.ncs = :customer ")
                .setParameter("customer", ncs)
                .getResultList();
        for (Object[] o : customer) {
            Barcode b = new Barcode();
            BarcodeStop bs = new BarcodeStop();
            Stops stops = new Stops();

            bs.setId(((BigInteger) o[0]).longValue());
            b.setBarcode(((BigDecimal) o[1]).longValue());
            bs.setStop((String) o[2]);

            bs.setRem((String) o[3]);
            stops.setRemWeb((String) o[4]);

            List<ArtDes> artDesList = new ArrayList<>();
            ArtDes e = new ArtDes();
            e.setArtDesDes((String) o[5]);
            e.setArtDesLng("fr");
            artDesList.add(e);

            Art article = new Art();
            article.setDescriptions(artDesList);
            b.setArticle(article);

            Porteur p = new Porteur();
            p.setCusPortPersvet((String) o[6]);
            p.setCusPortNom((String)o[12]);
            p.setCusPortPrenom((String)o[13]);
            b.setPorteur(p);
            bs.setDtb(((BigDecimal) o[7]).intValue());
            bs.setDtf(((BigDecimal) o[8]).intValue());
            bs.setDtr(((BigDecimal) o[9]).intValue());
            bs.setUsc((String) o[10]);
            bs.setBarcode(b);
            barcodeStops.add(new CustomBarcodeStop(bs, stops, (String) o[11]));
        }
        return barcodeStops;
    }
}
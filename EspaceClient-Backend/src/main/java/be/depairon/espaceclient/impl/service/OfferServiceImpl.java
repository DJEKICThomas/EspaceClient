package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.ScmAllDocDao;
import be.depairon.espaceclient.dto.OfferDetailDTO;
import be.depairon.espaceclient.dto.OfferDTO;
import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.entity.ScmAllDocDet;
import be.depairon.espaceclient.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class OfferServiceImpl implements OfferService {

    private final CustomerDao customerDao;
    private final ScmAllDocDao scmAllDocDao;

    @Autowired
    public OfferServiceImpl(CustomerDao customerDao, ScmAllDocDao scmAllDocDao){
        this.customerDao = customerDao;
        this.scmAllDocDao = scmAllDocDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfferDTO> getAllOffersForUser(String userId){
        List<String> ncsList = customerDao.getNcsListByUserId(userId);
        List<OfferDTO> allOffers = new ArrayList<>();

        for(String ncs : ncsList){
            List<ScmAllDoc> entities = scmAllDocDao.findOffersByNcs(ncs);
            for(ScmAllDoc e : entities){
                allOffers.add(mapToOfferDTO(e));
            }
        }
        return allOffers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfferDetailDTO> getOfferDetails(String document){
        List<ScmAllDocDet> details = scmAllDocDao.findDetailsByDocument(document);
        List<OfferDetailDTO> dtos = new ArrayList<>();
        for(ScmAllDocDet d : details){
            dtos.add(mapToDetailDTO(d));
        }
        return dtos;
    }

    private OfferDTO mapToOfferDTO(ScmAllDoc e){
        OfferDTO dto = new OfferDTO();
        dto.setDocument(e.getDocument());
        dto.setNda(e.getNda());
        dto.setNcs(e.getNcs());
        dto.setDocDes(e.getDocDes());
        dto.setReference(e.getReference());
        dto.setMontantTotal(e.getMontantTotal());
        dto.setStatut(e.getStatut());
        dto.setFournisseur(e.getFournisseur());
        dto.setDocCreation(parseDate(e.getDateCreation()));
        return dto;
    }

    private OfferDetailDTO mapToDetailDTO(ScmAllDocDet d) {
        OfferDetailDTO dto = new OfferDetailDTO();
        dto.setArticle(d.getArticle());
        dto.setArticleLab(d.getArticleLab());
        dto.setQuantity(d.getQuantiteTotal());
        dto.setPriceUnit(d.getPriceUnit());
        // Calcul du montant de la ligne
        if (d.getQuantiteTotal() != null && d.getPriceUnit() != null) {
            dto.setLineTotal(d.getQuantiteTotal().multiply(d.getPriceUnit()));
        }
        return dto;
    }

    private LocalDate parseDate(int dateInt) {
        String s = String.valueOf(dateInt);
        if (s.length() >= 8) {
            try {
                int year = Integer.parseInt(s.substring(0, 4));
                int month = Integer.parseInt(s.substring(4, 6));
                int day = Integer.parseInt(s.substring(6, 8));
                return LocalDate.of(year, month, day);
            } catch (Exception e) { return null; }
        }
        return null;
    }


}

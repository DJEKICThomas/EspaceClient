package be.depairon.espaceclient.impl.service;
import be.depairon.espaceclient.entity.ScmAllDoc;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.ScmAllDocDao;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.entity.ScmAllDocDet;
import be.depairon.espaceclient.service.ScmAllDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScmAllDocServiceImpl implements ScmAllDocService {

    private final CustomerDao customerDao;
    private final ScmAllDocDao scmAllDocDao;

    @Autowired
    public ScmAllDocServiceImpl(CustomerDao customerDao, ScmAllDocDao scmAllDocDao){
        this.customerDao = customerDao;
        this.scmAllDocDao = scmAllDocDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScmAllDocDTO> getDocumentsForUser(String userId, String typeDoc) {

        List<String> ncsList = customerDao.getNcsListByUserId(userId);
        List<ScmAllDocDTO> allDocs = new ArrayList<>();

        for (String ncs : ncsList) {
            List<be.depairon.espaceclient.entity.ScmAllDoc> entities =
                    scmAllDocDao.findDocsByNcsAndType(ncs, typeDoc);

            for (be.depairon.espaceclient.entity.ScmAllDoc e : entities) {
                allDocs.add(mapToDTO(e));
            }
        }
        return allDocs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScmAllDocDetDTO> getDocumentDetails(String document) {
        // On appelle le DAO que tu as déjà préparé
        List<ScmAllDocDet> entities = scmAllDocDao.findDetailsByDocument(document);
        List<ScmAllDocDetDTO> dtos = new ArrayList<>();

        for (ScmAllDocDet e : entities) {
            dtos.add(mapToDetailDTO(e));
        }
        return dtos;
    }

    public ScmAllDocDetDTO mapToDetailDTO(ScmAllDocDet d) {
        ScmAllDocDetDTO dto = new ScmAllDocDetDTO();
        dto.setArticle(d.getArticle());
        dto.setArticleLab(d.getArticleLab());
        dto.setQuantity(d.getQuantiteTotal());
        dto.setPriceUnit(d.getPriceUnit());
        if (d.getQuantiteTotal() != null && d.getPriceUnit() != null) {
            dto.setLineTotal(d.getQuantiteTotal().multiply(d.getPriceUnit()));
        }
        return dto;
    }

    public ScmAllDocDTO mapToDTO(ScmAllDoc e) {
        ScmAllDocDTO dto = new ScmAllDocDTO();
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

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ScmAllDocServiceImpl implements ScmAllDocService {

    private static final Map<Character, String> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put('A', "status_accepted");
        STATUS_MAP.put('B', "status_delivery_note");
        STATUS_MAP.put('D', "status_deleted");
        STATUS_MAP.put('O', "status_open");
        STATUS_MAP.put('P', "status_prepared");
        STATUS_MAP.put('R', "status_received");
        STATUS_MAP.put('S', "status_submitted");
        STATUS_MAP.put('T', "status_in_transit");
        STATUS_MAP.put('X', "status_cancelled");
        STATUS_MAP.put('Y', "status_closed");
    }

    private final CustomerDao customerDao;
    private final ScmAllDocDao scmAllDocDao;

    @Autowired
    public ScmAllDocServiceImpl(CustomerDao customerDao, ScmAllDocDao scmAllDocDao){
        this.customerDao = customerDao;
        this.scmAllDocDao = scmAllDocDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScmAllDocDTO> getDocumentsForUser(String userId, String docTyc, String typeDoc, String ncs, String company) {
        List<String> ncsList;
        if (ncs != null && !ncs.isEmpty()) {
            ncsList = List.of(ncs);
        } else {
            ncsList = customerDao.getNcsListByUserId(userId);
        }

        List<ScmAllDocDTO> allDocs = new ArrayList<>();
        for (String n : ncsList) {
            List<ScmAllDoc> entities = scmAllDocDao.findDocsByNcsAndType(n, docTyc, typeDoc, company);
            for (ScmAllDoc e : entities) {
                allDocs.add(mapToDTO(e));
            }
        }
        return allDocs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScmAllDocDTO> getClosedDocumentsForUser(String userId, String ncs, String company){
        List<String> ncsList;
        if(ncs != null && !ncs.isEmpty()){
            ncsList = List.of(ncs);
        } else {
            ncsList = customerDao.getNcsListByUserId(userId);
        }

        List<ScmAllDocDTO> allDocs = new ArrayList<>();
        for(String n : ncsList){
            List<ScmAllDoc> entities = scmAllDocDao.findClosedDocsByNcs(n, company);
            for(ScmAllDoc e : entities){
                allDocs.add(mapToDTO(e));
            }
        }
        return allDocs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScmAllDocDetDTO> getDocumentDetails(String document) {
        List<ScmAllDocDet> entities = scmAllDocDao.findDetailsByDocument(document);
        List<ScmAllDocDetDTO> dtos = new ArrayList<>();

        for (ScmAllDocDet e : entities) {
            dtos.add(mapToDetailDTO(e));
        }
        return dtos;
    }

    private String resolveStatus(String statusCode) {
        if (statusCode == null || statusCode.isBlank()) {
            return "";
        }

        statusCode = statusCode.trim();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < statusCode.length(); i++) {
            char c = statusCode.charAt(i);
            String label = STATUS_MAP.getOrDefault(c, String.valueOf(c));
            if (i > 0) result.append(" / ");
            result.append(label);
        }
        return result.toString();
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
        dto.setStatut(resolveStatus(e.getStatut()));
        dto.setFournisseur(e.getFournisseur());
        dto.setDocCreation(parseDate(e.getDateCreation()));
        dto.setDocCloture(parseDate(e.getDateCloture()));

        dto.setExpiryDate("2026-06-30");
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

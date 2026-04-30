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

    // Mapping pour les COMMANDES (DOC_TYC = 'N') — basé sur VAL_DES catégorie STATUSC
    private static final Map<Character, String> COMMAND_STATUS_MAP = new HashMap<>();

    // Mapping pour les OFFRES (DOC_TYC = 'Q') — basé sur VAL_DES catégorie STATUSCQ
    private static final Map<Character, String> OFFER_STATUS_MAP = new HashMap<>();

    static {
        // STATUSC (commandes)
        COMMAND_STATUS_MAP.put('B', "status_backorder");
        COMMAND_STATUS_MAP.put('D', "status_done");
        COMMAND_STATUS_MAP.put('P', "status_ready_to_ship");
        COMMAND_STATUS_MAP.put('R', "status_refused");
        COMMAND_STATUS_MAP.put('T', "status_in_progress");

        // STATUSCQ (offres)
        OFFER_STATUS_MAP.put('A', "status_allocated");
        OFFER_STATUS_MAP.put('O', "status_offered");
        OFFER_STATUS_MAP.put('R', "status_refused");
        OFFER_STATUS_MAP.put('X', "status_accepted");
        OFFER_STATUS_MAP.put('Y', "status_already_accepted");
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

    private String resolveStatus(String statusCode, String docTyc) {
        if (statusCode == null || statusCode.isBlank()) {
            return "";
        }

        // Choix du mapping selon le type de document
        // (trim car les colonnes char(1) peuvent revenir paddées avec un espace sur SQL Server)
        Map<Character, String> map;
        if (docTyc != null && "Q".equalsIgnoreCase(docTyc.trim())) {
            map = OFFER_STATUS_MAP;
        } else {
            // Par défaut on prend le mapping commandes (couvre 'N' et autres cas)
            map = COMMAND_STATUS_MAP;
        }

        statusCode = statusCode.trim();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < statusCode.length(); i++) {
            char c = statusCode.charAt(i);
            String label = map.getOrDefault(c, String.valueOf(c));
            if (i > 0) result.append(" / ");
            result.append(label);
        }
        return result.toString();
    }

    public ScmAllDocDetDTO mapToDetailDTO(ScmAllDocDet d) {
        ScmAllDocDetDTO dto = new ScmAllDocDetDTO();
        dto.setArticle(d.getArticle());
        dto.setArticleLab(d.getDesArt());
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
        dto.setStatut(resolveStatus(e.getStatut(), e.getDocTyc()));
        dto.setFournisseur(e.getFournisseur());
        dto.setDocCreation(parseDate(e.getDateCreation()));
        dto.setDocCloture(parseDate(e.getDateCloture()));

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

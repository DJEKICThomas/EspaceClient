package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.CusCtrDao;
import be.depairon.espaceclient.dao.DocDetDao; // Ajout du DAO détail
import be.depairon.espaceclient.dto.CusCtrDTO;
import be.depairon.espaceclient.dto.SubscriptionDetailDto;
import be.depairon.espaceclient.entity.CusCtr;
import be.depairon.espaceclient.entity.DocDet;
import be.depairon.espaceclient.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private CusCtrDao cusCtrDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private DocDetDao docDetDao; // Injecté ici pour le détail

    @Override
    @Transactional(readOnly = true)
    public List<CusCtrDTO> getAllSubscriptionsForUser(String userId, String ncs, String company) {
        List<String> ncsList;
        if (ncs != null && !ncs.isEmpty()) {
            ncsList = List.of(ncs);
        } else {
            ncsList = customerDao.getNcsListByUserId(userId);
        }

        List<CusCtrDTO> allDtos = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (String n : ncsList) {
            String cleanNcs = (n != null) ? n.trim() : null;
            if (cleanNcs != null) {
                List<CusCtr> entities = cusCtrDao.findByNcsAndNature(cleanNcs, "ABO", company);
                for (CusCtr e : entities) {
                    CusCtrDTO dto = mapToDTO(e);
                    boolean isStillValid = (dto.getEndDate() == null || !dto.getEndDate().isBefore(today));
                    if (dto.isActive() && isStillValid) {
                        allDtos.add(dto);
                    }
                }
            }
        }
        return allDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDetailDto> getSubscriptionDetails(String docNumber) {
        List<DocDet> entities = docDetDao.findByDocNumber(docNumber);
        return entities.stream()
                .map(this::mapToDetailDTO)
                .collect(Collectors.toList());
    }

    private SubscriptionDetailDto mapToDetailDTO(DocDet e) {
        SubscriptionDetailDto dto = new SubscriptionDetailDto();
        dto.setArticleCode(e.getDocDetIin() != null ? e.getDocDetIin().trim() : "");
        dto.setDescription(e.getDocDetLn0() != null ? e.getDocDetLn0().trim() : "");

        // On envoie le BigDecimal brut, le formatage (virgule/point) se fait côté Front
        dto.setPrice(e.getDocDetPrcNet());
        dto.setQuantity(e.getDocDetQwh());

        // Utilisation de la logique identique à InvoiceServiceImpl
        dto.setStartDate(parseDate(e.getDocDetDtc()));
        dto.setNextDueDate(parseDate(e.getDocDetRdt()));

        dto.setStatus(calculateStatus(dto.getNextDueDate()));
        return dto;
    }

    private CusCtrDTO mapToDTO(CusCtr e) {
        CusCtrDTO dto = new CusCtrDTO();
        dto.setId(e.getId());
        dto.setNcs(e.getNcs());
        dto.setReference(e.getReference());
        //dto.setDescription(e.getDescription());
        dto.setNature(e.getNature());
        dto.setRecur(e.getRecur());
        dto.setActive("Y".equalsIgnoreCase(e.getActif()));
        dto.setStartDate(parseDate(e.getStartDate()));
        dto.setEndDate(parseDate(e.getEndDate()));
        dto.setDocCtr(e.getDocCtr() != null ? e.getDocCtr().trim() : "");
        return dto;
    }

    private String calculateStatus(LocalDate dueDate) {
        if (dueDate == null) return "inconnu";
        LocalDate today = LocalDate.now();
        LocalDate inOneMonth = today.plusMonths(1);
        if (dueDate.isBefore(today)) return "expired";
        else if (dueDate.isBefore(inOneMonth)) return "expiring_soon";
        else return "active";
    }

    private LocalDate parseDate(int dateInt) {
        String s = String.valueOf(dateInt);
        if (s.length() >= 8) {
            try {
                // substring(0,4) = YYYY, (4,6) = MM, (6,8) = DD
                int year = Integer.parseInt(s.substring(0, 4));
                int month = Integer.parseInt(s.substring(4, 6));
                int day = Integer.parseInt(s.substring(6, 8));
                return LocalDate.of(year, month, day);
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }
}
package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.CusCtrDao;
import be.depairon.espaceclient.dto.CusCtrDTO;
import be.depairon.espaceclient.entity.CusCtr;
import be.depairon.espaceclient.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private CusCtrDao cusCtrDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public List<CusCtrDTO> getAllSubscriptionsForUser(String userId) {
        List<String> ncsList = customerDao.getNcsListByUserId(userId);
        List<CusCtrDTO> allDtos = new ArrayList<>();

        for (String ncs : ncsList) {
            List<CusCtr> entities = cusCtrDao.findByNcsAndNature(ncs, "ABO");
            for (CusCtr e : entities) {
                allDtos.add(mapToDTO(e));
            }
        }
        return allDtos;
    }

    private CusCtrDTO mapToDTO(CusCtr e) {
        CusCtrDTO dto = new CusCtrDTO();
        dto.setId(e.getId());
        dto.setNcs(e.getNcs());
        dto.setReference(e.getReference());
        dto.setDescription(e.getDescription());
        dto.setNature(e.getNature());
        // Transformation logique Y/N -> boolean
        dto.setActive("Y".equalsIgnoreCase(e.getActif()));
        dto.setStartDate(parseDate(e.getStartDate()));
        dto.setEndDate(parseDate(e.getEndDate()));
        return dto;
    }

    private LocalDate parseDate(Integer dateInt) {
        if (dateInt == null || dateInt <= 0) return null;
        String s = String.valueOf(dateInt);
        if (s.length() >= 8) {
            try {
                int year = Integer.parseInt(s.substring(0, 4));
                int month = Integer.parseInt(s.substring(4, 6));
                int day = Integer.parseInt(s.substring(6, 8));
                return LocalDate.of(year, month, day);
            } catch (Exception ex) { return null; }
        }
        return null;
    }
}
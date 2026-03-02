package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.InfocentreDao;
import be.depairon.espaceclient.dto.InfocentreDTO;
import be.depairon.espaceclient.entity.Infocentre;
import be.depairon.espaceclient.service.InfocentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InfocentreServiceImpl implements InfocentreService {

    @Autowired
    private InfocentreDao infocentreDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(readOnly = true)
    public List<InfocentreDTO> getAllDocsForUser(String userId) {
        List<String> ncsList = customerDao.getNcsListByUserId(userId);
        List<InfocentreDTO> allDocs = new ArrayList<>();

        for (String ncs : ncsList) {
            List<Infocentre> entities = infocentreDao.findByNcs(ncs);
            for (Infocentre e : entities) {
                allDocs.add(mapToDTO(e));
            }
        }
        return allDocs;
    }

    private InfocentreDTO mapToDTO(Infocentre e) {
        InfocentreDTO dto = new InfocentreDTO();
        dto.setId(e.getId());
        dto.setNcs(e.getNcs());
        dto.setInfo(e.getInfo());
        dto.setReference(e.getReference());
        dto.setDateDoc(parseDate(e.getDateCreation()));
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
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}

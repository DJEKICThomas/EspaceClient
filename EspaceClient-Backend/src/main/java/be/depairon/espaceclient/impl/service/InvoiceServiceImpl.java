package be.depairon.espaceclient.impl.service;
import be.depairon.espaceclient.entity.Fmov;
import be.depairon.espaceclient.dao.FmovDao;

import be.depairon.espaceclient.dto.InvoiceDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.CusSal;
import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final CustomerService customerService;
    private final CustomerDao customerDao;
    private final FmovDao fmovDao;

    // Injecte le service client existant
    public InvoiceServiceImpl(CustomerService customerService, CustomerDao customerDao, FmovDao fmovDao) {
        this.customerService = customerService;
        this.customerDao = customerDao;
        this.fmovDao = fmovDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoicesByCustomer(String ncs) {
        List<Fmov> fmovs = fmovDao.findInvoicesByNcs(ncs);
        List<InvoiceDTO> dtos = new ArrayList<>();
        for (Fmov f : fmovs) {
            dtos.add(mapFmovToDTO(f));
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoiceDetails(String num) {
        List<Fmov> entities = fmovDao.findDetailsByInvoiceNum(num);
        List<InvoiceDTO> dtos = new ArrayList<>();

        for (Fmov f : entities) {
            InvoiceDTO dto = new InvoiceDTO();
            dto.setId(f.getId());
            dto.setNum(f.getFmovNum());
            dto.setDes(f.getFmovDes() != null ? f.getFmovDes().trim() : "");
            dto.setValCurD(f.getFmovValCur());

            dto.setQt1(f.getFmovQt1());
            dto.setAcc(f.getFmovAcc() != null ? f.getFmovAcc().trim() : "");
            dto.setDc(f.getFmovDc());

            dtos.add(dto);
        }
        return dtos;
    }

    private InvoiceDTO mapFmovToDTO(Fmov f) {
        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(f.getId());
        dto.setNcs(f.getFmovNcs());
        dto.setRec(f.getFmovRec());
        dto.setDes(f.getFmovDes());
        dto.setJnl(f.getFmovJnl());
        dto.setCur(f.getFmovCur());
        dto.setType(f.getFmovTyp());

        // Numéro de facture
        if (f.getFmovNum() != null) {
            dto.setNum(f.getFmovNum().trim());
        } else {
            dto.setNum("");
        }

        // Formatage des dates (YYYYMMDD -> DD/MM/YYYY)
        dto.setDocDate(parseDate(f.getFmovDatDoc()));
        dto.setDueDate(parseDate(f.getFmovDatDue()));

        // Logique Débit / Crédit
        if ("D".equals(f.getFmovDc())) {
            dto.setValCurD(f.getFmovValCur());
            dto.setValCurC(BigDecimal.ZERO);
        } else {
            dto.setValCurC(f.getFmovValCur());
            dto.setValCurD(BigDecimal.ZERO);
        }

        // Statut pour le tri
        boolean isPaid = f.getFmovLet() != null && !f.getFmovLet().trim().isEmpty();
        dto.setPaid(isPaid);
        dto.setLet(f.getFmovLet());

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

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoicesForUser(String userId, String ncs) {
        List<String> ncsList;
        if (ncs != null && !ncs.isEmpty()) {
            ncsList = List.of(ncs);
        } else {
            ncsList = customerDao.getNcsListByUserId(userId);
        }

        List<InvoiceDTO> allInvoices = new ArrayList<>();
        for (String n : ncsList) {
            List<Fmov> fmovs = fmovDao.findInvoicesByNcs(n);
            for (Fmov f : fmovs) {
                InvoiceDTO dto = mapFmovToDTO(f);
                if (dto.getValCurD() != null && dto.getValCurD().compareTo(BigDecimal.ZERO) != 0) {
                    allInvoices.add(dto);
                }
            }
        }
        return allInvoices;
    }
}
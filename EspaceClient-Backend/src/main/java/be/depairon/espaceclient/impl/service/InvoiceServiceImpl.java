package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dto.InvoiceDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.CusSal;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final CustomerService customerService;

    // Injecte le service client existant
    public InvoiceServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getInvoicesByCustomer(String ncs) {
        // Récupère le client avec ses factures (NDA 0)
        Customer customer = customerService.getByNcsNda(ncs, 0);

        List<InvoiceDTO> dtos = new ArrayList<>();

        if (customer != null && customer.getCusSal() != null) {
            for (CusSal sal : customer.getCusSal()) {
                dtos.add(mapToDTO(sal));
            }
        }
        return dtos;
    }

    private InvoiceDTO mapToDTO(CusSal sal) {
        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(sal.getId());
        dto.setNcs(sal.getCusSalNcs());
        dto.setRec("OK");

        // On n'a pas de colonne "Période" (Per) dans ton entité CusSal
        // On met 0 par défaut pour éviter le plantage
        dto.setPer(BigDecimal.ZERO);

        dto.setDes(sal.getCusSalCom());
        dto.setJnl(sal.getCusSalDep());
        dto.setCur("EUR");

        // ATTENTION : cusSalNum n'existe pas dans ton entité.
        // On met l'ID en attendant de trouver la vraie colonne numéro de pièce.
        dto.setNum(BigDecimal.valueOf(sal.getId()));

        // Dates formatées (CUS_SAL_DTC est un int)
        String rawDate = String.valueOf(sal.getCusSalDtc());
        if (rawDate != null && rawDate.length() >= 8) {
            dto.setCcyy(rawDate.substring(0, 4));
            String formatted = rawDate.substring(6, 8) + "/" + rawDate.substring(4, 6) + "/" + rawDate.substring(0, 4);
            dto.setImpDate(formatted);
            dto.setDocDate(formatted);
        } else {
            dto.setCcyy("0");
            dto.setImpDate(rawDate);
            dto.setDocDate(rawDate);
        }

        BigDecimal montant = BigDecimal.ZERO;
        try {
            if (sal.getCusSalCna() != null && !sal.getCusSalCna().trim().isEmpty()) {
                montant = new BigDecimal(sal.getCusSalCna().trim());
            }
        } catch (Exception e) {
            montant = BigDecimal.ZERO;
        }

        // pas de colonnes débits crédits dans l'entité cussal
        dto.setValCurD(montant);
        dto.setValCurC(BigDecimal.ZERO);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoicesForUser(String userId) {
        return new ArrayList<>();
    }
}
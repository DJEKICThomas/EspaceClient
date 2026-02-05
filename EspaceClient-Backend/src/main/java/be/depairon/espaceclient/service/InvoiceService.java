package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.InvoiceDTO;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> getInvoicesByCustomer(String ncs);
    List<InvoiceDTO> getAllInvoicesForUser(String userId);
}

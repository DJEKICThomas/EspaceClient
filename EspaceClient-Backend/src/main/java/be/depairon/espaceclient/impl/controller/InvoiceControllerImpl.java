package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.InvoiceController;
import be.depairon.espaceclient.dto.InvoiceDTO;
import be.depairon.espaceclient.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class InvoiceControllerImpl implements InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public List<InvoiceDTO> getInvoicesByCustomer(String ncs) {
        return invoiceService.getInvoicesByCustomer(ncs);
    }
}
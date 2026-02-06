package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.InvoiceDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/invoices")
public interface InvoiceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<InvoiceDTO> getInvoicesByCustomer(@RequestParam(value = "ncs") String ncs);

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    List<InvoiceDTO> getAllInvoicesForUser(@RequestParam(value ="userId") String userId);

}
package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.CustomerDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/customers")
public interface CustomerController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CustomerDTO> getList();

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CustomerDTO> getListByName(@RequestParam("filter") String filter, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

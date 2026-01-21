package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.DotationDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/dotations")
public interface DotationController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getList(@RequestParam("customer") String customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}

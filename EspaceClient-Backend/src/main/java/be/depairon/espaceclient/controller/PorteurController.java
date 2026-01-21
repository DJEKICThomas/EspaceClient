package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.PorteurDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/porteurs")
public interface PorteurController {

    @GetMapping
    List<PorteurDTO> getList(@RequestParam("customer") String customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("/details")
    ResponseEntity<?> getDetail(@RequestParam("cusId") Long cusId,
                                @RequestParam(value = "porteur", required = false) String carrier, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
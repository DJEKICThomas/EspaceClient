package be.depairon.espaceclient.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/statistics")
public interface StatisticController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getListByCustomer(@RequestParam("customer") String customer, @RequestParam("nature") String nature, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/details",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetails(@RequestParam("cusId") long cusId, @RequestParam("artId") long artId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

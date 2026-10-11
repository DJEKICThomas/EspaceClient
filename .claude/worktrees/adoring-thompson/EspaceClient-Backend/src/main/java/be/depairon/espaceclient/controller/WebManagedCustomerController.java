package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.WebManagedCustomerDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/wm-customers")
public interface WebManagedCustomerController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<WebManagedCustomerDTO> getWebManagedCustomerForUser(@RequestParam("userId") String userId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

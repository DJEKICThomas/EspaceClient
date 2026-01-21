package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.BarcodeStopDTO;
import be.depairon.espaceclient.dto.RequestHeaderDTO;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/requests")
public interface RequestController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getList( @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getUserList(@RequestParam(value = "userMail") String userMail, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetailList(@PathVariable long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createRequest(@RequestBody RequestHeaderDTO requestHeaderDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    List<BarcodeStopDTO> getCustomerRequestsList(@RequestParam String ncs, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping(path = "/improvement", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getImprovementRequestList(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(path = "/comment", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getDetailCommentRequestList(@RequestBody String requestDetail, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}

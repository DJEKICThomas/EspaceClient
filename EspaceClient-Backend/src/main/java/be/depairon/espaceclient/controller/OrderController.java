package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/orders")
public interface OrderController {

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScmAllDocDTO> getAllOrdersForUser(@RequestParam String userId, @RequestParam(required = false) String ncs, @RequestParam(required = false) String company);


    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScmAllDocDetDTO> getDocumentDetails(@RequestParam("document") String document);
}
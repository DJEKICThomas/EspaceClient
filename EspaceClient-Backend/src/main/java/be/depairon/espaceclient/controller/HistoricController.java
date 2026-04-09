package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/historic")
public interface HistoricController {
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScmAllDocDTO> getAllHistoricForUser(@RequestParam("userId") String userId, @RequestParam(value = "ncs", required = false) String ncs);

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ScmAllDocDetDTO> getDocumentDetails(@RequestParam("document") String document);
}

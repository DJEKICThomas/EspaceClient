package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.DocAttDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/documents")
public interface DocumentController {

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    List<DocAttDTO> getDocumentByDocNumber(@RequestParam("docNumber") String docNumber);
}
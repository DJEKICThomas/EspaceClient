package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.DocumentController;
import be.depairon.espaceclient.dto.DocAttDTO;
import be.depairon.espaceclient.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DocumentControllerImpl implements DocumentController {

    @Autowired
    private DocumentService documentService;


    @Override
    public List<DocAttDTO> getDocumentByDocNumber(String docNumber) {
        return documentService.getDocumentByDocNumber(docNumber);
    }
}
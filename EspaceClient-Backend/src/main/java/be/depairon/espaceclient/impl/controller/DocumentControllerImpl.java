package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.DocumentController;
import be.depairon.espaceclient.dto.DocAttDTO;
import be.depairon.espaceclient.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DocumentControllerImpl implements DocumentController {

    @Autowired
    private DocumentService documentService;


    @Override
    public List<DocAttDTO> getDocumentPDFByDocNumber(String docNumber, String ncs) {
        return documentService.getDocumentPDFByDocNumber(docNumber, ncs);
    }

    @Override
    public ResponseEntity<Resource> viewPdf(String path) {
        File file = new File(path);
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(resource);
    }
}
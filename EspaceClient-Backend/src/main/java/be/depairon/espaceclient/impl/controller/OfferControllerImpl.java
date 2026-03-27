package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.OfferController;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.service.ScmAllDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class
OfferControllerImpl implements OfferController {

    @Autowired
    private ScmAllDocService scmAllDocService;

    @Override
    public java.util.List<ScmAllDocDTO> getAllOffersForUser(String userId){
        return scmAllDocService.getDocumentsForUser(userId, "Q", "!D");
    }

    @Override
    public List<ScmAllDocDetDTO> getDocumentDetails(String document){
        return scmAllDocService.getDocumentDetails(document);
    }
}

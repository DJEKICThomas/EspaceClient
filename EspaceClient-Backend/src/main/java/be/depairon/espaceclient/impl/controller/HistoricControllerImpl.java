package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.HistoricController;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.service.ScmAllDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HistoricControllerImpl implements HistoricController {

    @Autowired
    private ScmAllDocService scmAllDocService;

    @Override
    public List<ScmAllDocDTO> getAllHistoricForUser(String userId, String ncs) {
        return scmAllDocService.getDocumentsForUser(userId, null, "D", ncs);
    }

    @Override
    public List<ScmAllDocDetDTO> getDocumentDetails(String document){
        return scmAllDocService.getDocumentDetails(document);
    }
}

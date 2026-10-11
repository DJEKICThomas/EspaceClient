package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.InfocentreController;
import be.depairon.espaceclient.dto.InfocentreDTO;
import be.depairon.espaceclient.service.InfocentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class InfocentreControllerImpl implements InfocentreController {

    @Autowired
    private InfocentreService infocentreService;

    @Override
    public List<InfocentreDTO> getDocs(String userId, String ncs) {
        return infocentreService.getAllDocsForUser(userId, ncs);
    }
}
package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.CompanyLogoController;
import be.depairon.espaceclient.dto.CompanyLogoDTO;
import be.depairon.espaceclient.service.CompanyLogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CompanyLogoControllerImpl implements CompanyLogoController {

    @Autowired
    private CompanyLogoService companyLogoService;

    @Override
    public ResponseEntity<byte[]> getLogo(String com){
        CompanyLogoDTO logo = companyLogoService.getLogoByCompany(com);
        if (logo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(logo.getContentType()))
                .body(logo.getContent());
    }
}

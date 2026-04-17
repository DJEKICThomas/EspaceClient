package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.YgparDao;
import be.depairon.espaceclient.dto.CompanyLogoDTO;
import be.depairon.espaceclient.entity.Ygpar;
import be.depairon.espaceclient.service.CompanyLogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CompanyLogoServiceImpl implements CompanyLogoService {

    private final YgparDao ygparDao;
    private final String basePath;

    @Autowired
    public CompanyLogoServiceImpl(YgparDao ygparDao,  @Value("${com.yglobal.logo.basepath}") String basePath){
        this.ygparDao = ygparDao;
        this.basePath = basePath;
    }

    @Override
    public CompanyLogoDTO getLogoByCompany(String com){
        //1. Aller chercher le chemin du logo dans YGPAR
        Ygpar ygpar = ygparDao.findLogoByCompany(com);
        if (ygpar == null || ygpar.getValue() == null){
            return null;
        }

        //2. Construction du chemin
        Path fullPath = Paths.get(basePath, ygpar.getValue());

        //3. Lecture du fichier
        try{
            byte[] content = Files.readAllBytes(fullPath); //Charge tt le fichier en mémoire
            String contentType = Files.probeContentType(fullPath); //Regarde l'extension et en déduit le MIME
            if(contentType == null){
                contentType = "application/octet-stream";
            }
            return new CompanyLogoDTO(content, contentType);
        } catch(IOException e){
            return null;
        }
    }

}

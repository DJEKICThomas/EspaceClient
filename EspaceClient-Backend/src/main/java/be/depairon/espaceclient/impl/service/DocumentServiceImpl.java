package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.DocumentDao;
import be.depairon.espaceclient.dto.DocAttDTO;
import be.depairon.espaceclient.entity.DocAtt;
import be.depairon.espaceclient.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;

    public DocumentServiceImpl(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    public List<DocAttDTO> getDocumentByDocNumber(String docNumber){
        List<DocAtt> entities = documentDao.findDocAttByDocNumber(docNumber);
        List<DocAttDTO> dtos = new ArrayList<>();

        for (DocAtt entity : entities){
            DocAttDTO dto = new DocAttDTO();
            dto.setDocNumber(entity.getDocDok());
            dto.setFilePath(entity.getDocAtt());

            String fullPath = entity.getDocAtt();
            if(fullPath != null && fullPath.contains("/")){
                dto.setFileName(fullPath.substring(fullPath.lastIndexOf("/") + 1));
            } else {
                dto.setFileName(fullPath);
            }
            dtos.add(dto);
        }
        return dtos;
    }
}

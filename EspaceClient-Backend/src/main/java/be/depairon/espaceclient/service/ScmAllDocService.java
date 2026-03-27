package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.entity.ScmAllDocDet;

import java.util.List;

public interface ScmAllDocService {

    List<ScmAllDocDTO> getDocumentsForUser(String userId, String docTyc, String statut);
    ScmAllDocDTO mapToDTO(ScmAllDoc e);

    List<ScmAllDocDetDTO> getDocumentDetails(String document);
    ScmAllDocDetDTO mapToDetailDTO(ScmAllDocDet document);
}

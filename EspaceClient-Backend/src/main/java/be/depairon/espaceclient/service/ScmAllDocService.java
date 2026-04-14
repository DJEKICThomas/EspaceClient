package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.dto.ScmAllDocDetDTO;
import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.entity.ScmAllDocDet;

import java.util.List;

public interface ScmAllDocService {

    List<ScmAllDocDTO> getDocumentsForUser(String userId, String docTyc, String typeDoc, String ncs, String company);
    ScmAllDocDTO mapToDTO(ScmAllDoc e);
    List<ScmAllDocDTO> getClosedDocumentsForUser(String userId, String ncs, String company);
    List<ScmAllDocDetDTO> getDocumentDetails(String document);
    ScmAllDocDetDTO mapToDetailDTO(ScmAllDocDet document);
}

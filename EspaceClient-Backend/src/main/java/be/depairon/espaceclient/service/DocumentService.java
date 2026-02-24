package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.DocAttDTO;

import java.util.List;

public interface DocumentService {
    List<DocAttDTO> getDocumentPDFByDocNumber(String docNumber, String ncs);
}

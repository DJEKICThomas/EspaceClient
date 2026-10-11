package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.InfocentreDTO;
import java.util.List;

public interface InfocentreService {
    List<InfocentreDTO> getAllDocsForUser(String userId);
}

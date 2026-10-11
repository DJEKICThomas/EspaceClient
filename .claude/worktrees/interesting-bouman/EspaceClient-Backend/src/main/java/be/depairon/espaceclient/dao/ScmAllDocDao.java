package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.dto.ScmAllDocDTO;
import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.entity.ScmAllDocDet;

import java.util.List;

public interface ScmAllDocDao {
    List<ScmAllDoc> findDocsByNcsAndType(String ncs, String typeDoc, String statut);
    List<ScmAllDocDet> findDetailsByDocument(String document);
    List<ScmAllDoc> findDocsByNcsAndDateRange(String ncs, int from, int to);
}
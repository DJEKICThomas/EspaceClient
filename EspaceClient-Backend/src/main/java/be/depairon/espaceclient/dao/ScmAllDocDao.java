package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.entity.ScmAllDocDet;

import java.util.List;

public interface ScmAllDocDao {
    List<ScmAllDoc> findOffersByNcs(String ncs);
    List<ScmAllDocDet> findDetailsByDocument(String document);
}
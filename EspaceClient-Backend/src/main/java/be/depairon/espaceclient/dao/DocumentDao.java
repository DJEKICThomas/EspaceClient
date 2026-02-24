package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.DocAtt;

import java.util.List;

public interface DocumentDao {
    List<DocAtt> findDocAttByDocNumberAndNcs(String docNumber, String ncs);
}

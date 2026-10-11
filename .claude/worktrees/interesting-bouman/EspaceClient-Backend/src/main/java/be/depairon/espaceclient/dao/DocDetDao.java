package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.DocDet;

import java.util.List;

public interface DocDetDao {
    List<DocDet> findByDocNumber(String docCtr);
}

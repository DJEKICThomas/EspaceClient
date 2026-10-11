package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.CusCtr;

import java.util.List;

public interface CusCtrDao {
    List<CusCtr> findByNcsAndNature(String ncs, String nature);
}

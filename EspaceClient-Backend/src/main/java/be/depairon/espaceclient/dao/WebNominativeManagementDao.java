package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.NominativeManagement;

import java.util.List;

public interface WebNominativeManagementDao {

    List<Object[]> getList(String customer);

    List<NominativeManagement> getListByPorteur(Long cusId, String carrier);

    List<Object[]> getNativeListByPorteur(Long cusId, String carrier);
}

package be.depairon.espaceclient.service;

import java.util.List;

public interface EntryControlService {

    List<Object[]> getNativeList(String customer);

    List<Object[]> getNativeList(String customer, Long skip, Long take, String whereClause, String orderByClause);

    Integer countNativeList(String customer, String whereClause);

}

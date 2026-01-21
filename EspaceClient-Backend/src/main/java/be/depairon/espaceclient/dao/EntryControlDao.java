package be.depairon.espaceclient.dao;

import java.util.List;

public interface EntryControlDao {

    List<Object[]> getNativeList(String customer, int hisYear);

    List<Object[]> getNativeList(String customer, Long skip, Long take, String whereClause, String orderByClause);

    Integer countNativeList(String customer, String whereClause);
}
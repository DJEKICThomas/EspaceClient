package be.depairon.espaceclient.dao;

import java.util.List;

public interface WebPermissionDao {
    List<Object[]> getPermission(String profile);
    List<Object[]> getUserPermission(String userId);
    List<Object[]> getAllUserPermissions(String userId);
}

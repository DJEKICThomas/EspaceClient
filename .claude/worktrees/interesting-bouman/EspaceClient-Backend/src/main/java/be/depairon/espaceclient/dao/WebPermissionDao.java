package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.Webrole;

import java.util.List;

public interface WebPermissionDao {
    Webprofile getPermission(String profile);
    List<WebUserRole> getUserPermission(String userId);
    List<Webrole> getAllUserPermissions(String userId);
}

package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.WebUserRole;

import java.util.List;

public interface WebprofileService {
    Webprofile getProfile(String profileName);

    List<Object[]> getAllUserPermissions(String userId);

    List<Object[]> getUserPermissions(String userId);

    WebUserRole getByUseridAndRole(String userid, String role);

    void createWebUserRole(WebUserRole webUserRole);

    void deleteWebUserRole(WebUserRole webUserRole);
}

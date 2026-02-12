package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.PermissionDTO;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.WebUserRole;

import java.util.List;

public interface WebprofileService {
    Webprofile getProfile(String profileName);

    List<PermissionDTO> getAllUserPermissions(String userId);

    List<PermissionDTO> getUserPermissions(String userId);

    WebUserRole getByUseridAndRole(String userid, String role);

    void createWebUserRole(WebUserRole webUserRole);

    void deleteWebUserRole(WebUserRole webUserRole);
}

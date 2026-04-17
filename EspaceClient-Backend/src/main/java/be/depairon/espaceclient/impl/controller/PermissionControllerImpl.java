package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.PermissionController;
import be.depairon.espaceclient.dto.MessageDTO;
import be.depairon.espaceclient.dto.PermissionDTO;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.security.AdminAccessChecker;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebprofileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionControllerImpl extends GenericController implements PermissionController {

    private final UserService userService;
    private final WebprofileService webprofileService;
    private final AdminAccessChecker adminAccessChecker;

    public PermissionControllerImpl(UserService userService, WebprofileService webprofileService,
                                    AdminAccessChecker adminAccessChecker) {
        this.userService = userService;
        this.webprofileService = webprofileService;
        this.adminAccessChecker = adminAccessChecker;
    }

    @Override
    public ResponseEntity<?> getPermission(String email, String token) {
        adminAccessChecker.requireAdmin(token);

        if (StringUtils.isNotBlank(email)) {
            User user = userService.getUser(email);
            if (user != null) {
                List<PermissionDTO> permissions = webprofileService.getAllUserPermissions(user.getUserid());
                return ResponseEntity.ok(permissions);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDTO("Aucun utilisateur trouvé avec l'email " + email));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email renseigné"));
    }

    @Override
    public ResponseEntity<?> getPermissionList(String email, String token) {
        adminAccessChecker.requireAdmin(token);

        if (StringUtils.isNotBlank(email)) {
            User user = userService.getUserNoCondition(email);
            if (user != null) {
                List<PermissionDTO> permissions = webprofileService.getAllUserPermissions(user.getUserid());
                return ResponseEntity.ok(permissions);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDTO("Aucun utilisateur trouvé avec l'email " + email));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email renseigné"));
    }

    @Override
    public ResponseEntity<?> updatePermission(String email, PermissionDTO permissionDTO, String token) {
        adminAccessChecker.requireAdmin(token);

        String comment = StringUtils.defaultString(permissionDTO != null ? permissionDTO.getComment() : "", "");
        String trimEmail = StringUtils.trim(email);
        if (StringUtils.isBlank(trimEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email encodé"));
        }

        if (permissionDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune permission encodée"));
        }

        String trimRole = StringUtils.trim(permissionDTO.getRole());
        WebUserRole webUserRole = webprofileService.getByUseridAndRole(trimEmail, trimRole);

        if (permissionDTO.isHasAccess() && webUserRole == null) {
            webUserRole = new WebUserRole();
            webUserRole.setUserid(trimEmail);
            webUserRole.setRole(trimRole);
            webUserRole.setComment(comment);
            webprofileService.createWebUserRole(webUserRole);
        } else if (!permissionDTO.isHasAccess() && webUserRole != null) {
            webprofileService.deleteWebUserRole(webUserRole);
        }

        return ResponseEntity.ok(new MessageDTO("Permission mise à jour"));
    }
}
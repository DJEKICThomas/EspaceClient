package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.PermissionController;
import be.depairon.espaceclient.dto.MessageDTO;
import be.depairon.espaceclient.dto.PermissionDTO;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.Webrole;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebprofileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PermissionControllerImpl extends GenericController implements PermissionController {

    private final UserService userService;
    private final WebprofileService webprofileService;

    public PermissionControllerImpl(UserService userService, WebprofileService webprofileService) {
        this.userService = userService;
        this.webprofileService = webprofileService;
    }

    @Override
    @Transactional
    public ResponseEntity<?> getPermission(String email) {
        if (StringUtils.isNotBlank(email)) {
            User user = userService.getUser(email);
            if (user != null) {
                List<PermissionDTO> permissionListDto = new ArrayList<>();
                List<Object[]> permissionList = webprofileService.getAllUserPermissions(user.getUserid());
                for (Object[] o : permissionList) {
                    PermissionDTO dto = new PermissionDTO();

                    dto.setCode((String) o[0]);
                    dto.setRole((String) o[0]);
                    dto.setHasAccess(o[1].equals(1));
                    dto.setComment((String) o[2]);

                    permissionListDto.add(dto);
                }
                return ResponseEntity.ok(permissionListDto);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun utilisateur trouvé avec l'email " + email));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email renseigné"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getPermissionList(String email) {
        if (StringUtils.isNotBlank(email)) {
            // GET USER ACCES EVEN IF NOT ACTIVE
            User user = userService.getUserNoCondition(email);
            if (user != null) {
                List<PermissionDTO> permissionListDto = new ArrayList<>();
                List<Object[]> permissionList = webprofileService.getUserPermissions(user.getUserid());
                for (Object[] o : permissionList) {
                    PermissionDTO dto = new PermissionDTO();

                    dto.setCode((String) o[0]);
                    dto.setRole((String) o[0]);
                    dto.setHasAccess(o[1].equals(1));
                    dto.setComment((String) o[2]);

                    permissionListDto.add(dto);
                }
                return ResponseEntity.ok(permissionListDto);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun utilisateur trouvé avec l'email " + email));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email renseigné"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updatePermission(String email, PermissionDTO permissionDTO) {
        String trimEmail = StringUtils.trim(email);
        if (StringUtils.isBlank(trimEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucun email encodé"));
        } else if (permissionDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageDTO("Aucune permision encodé"));
        } else {
            String trimRole = StringUtils.trim(permissionDTO.getRole());
            WebUserRole webUserRole = webprofileService.getByUseridAndRole(trimEmail, trimRole);
            if (permissionDTO.isHasAccess() && webUserRole == null) {
                // Create Permission
                webUserRole = new WebUserRole();
                webUserRole.setUserid(trimEmail);
                webUserRole.setRole(trimRole);
                webUserRole.setComment("");
                webprofileService.createWebUserRole(webUserRole);
            } else if (!permissionDTO.isHasAccess() && webUserRole != null) {
                // Delete permission
                webprofileService.deleteWebUserRole(webUserRole);
            }
            return ResponseEntity.ok(new MessageDTO("Permission mise à jour"));
        }
    }
}

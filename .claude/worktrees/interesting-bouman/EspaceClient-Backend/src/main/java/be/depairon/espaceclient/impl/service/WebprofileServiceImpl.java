package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.WebPermissionDao;
import be.depairon.espaceclient.dto.PermissionDTO;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.Webrole;
import be.depairon.espaceclient.repository.WebprofileRepository;
import be.depairon.espaceclient.repository.WebuserroleRepository;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebprofileService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebprofileServiceImpl implements WebprofileService {

    private final WebPermissionDao webPermissionDao;
    private final WebuserroleRepository webuserroleRepository;
    private final UserService userService;

    public WebprofileServiceImpl(WebPermissionDao webPermissionDao,
                                 WebuserroleRepository webuserroleRepository,
                                 UserService userService) {
        this.webPermissionDao = webPermissionDao;
        this.webuserroleRepository = webuserroleRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Webprofile getProfile(String profileName) {
        // Le DAO renvoie déjà l'entité complète avec sa liste ManyToMany
        return webPermissionDao.getPermission(profileName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> getUserPermissions(String userId) {
        List<WebUserRole> userRoles = webPermissionDao.getUserPermission(userId);
        List<PermissionDTO> dtos = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(userRoles)) {
            for (WebUserRole ur : userRoles) {
                dtos.add(mapWebUserRoleToDTO(ur));
            }
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> getAllUserPermissions(String userId) {
        User user = userService.getUserNoCondition(userId);
        if (user == null) return new ArrayList<>();

        Webprofile userProfile = webPermissionDao.getPermission(user.getProfile());

        List<WebUserRole> specificRoles = webPermissionDao.getUserPermission(userId);

        List<PermissionDTO> dtos = new ArrayList<>();

        if (userProfile != null && userProfile.getWebroleList() != null) {
            for (Webrole role : userProfile.getWebroleList()) {

                PermissionDTO dto = new PermissionDTO();
                dto.setCode(StringUtils.trim(role.getRole()));
                dto.setRole(dto.getCode());
                dto.setComment(StringUtils.trim(role.getComment()));

                boolean hasSpecificAccess = specificRoles.stream()
                        .anyMatch(sr -> sr.getRole().equals(role.getRole()));

                dto.setHasAccess(hasSpecificAccess);

                dtos.add(dto);
            }
        }

        return dtos;
    }


    private PermissionDTO mapWebUserRoleToDTO(WebUserRole ur) {
        PermissionDTO dto = new PermissionDTO();
        dto.setCode(StringUtils.trim(ur.getRole()));
        dto.setRole(dto.getCode());
        dto.setComment(StringUtils.trim(ur.getComment()));
        dto.setHasAccess(true); // Si c'est dans cette table, il a l'accès
        return dto;
    }

    private PermissionDTO mapToFullPermissionDTO(Webrole role, Webprofile profile, List<WebUserRole> specificRoles) {
        PermissionDTO dto = new PermissionDTO();
        dto.setCode(StringUtils.trim(role.getRole()));
        dto.setRole(dto.getCode());
        dto.setComment(StringUtils.trim(role.getComment()));

        // Check si accès via Profil OU via accès spécifique
        boolean hasProfileAccess = profile != null && profile.getWebroleList().stream()
                .anyMatch(r -> r.getRole().equals(role.getRole()));
        boolean hasSpecificAccess = specificRoles.stream()
                .anyMatch(sr -> sr.getRole().equals(role.getRole()));

        dto.setHasAccess(hasProfileAccess || hasSpecificAccess);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public WebUserRole getByUseridAndRole(String userid, String role) {
        return webuserroleRepository.findByUseridAndRole(userid, role).orElse(null);
    }

    @Override
    @Transactional
    public void createWebUserRole(WebUserRole webUserRole) {
        webuserroleRepository.save(webUserRole);
    }

    @Override
    @Transactional
    public void deleteWebUserRole(WebUserRole webUserRole) {
        webuserroleRepository.delete(webUserRole);
    }
}

package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.WebPermissionDao;
import be.depairon.espaceclient.entity.WebUserRole;
import be.depairon.espaceclient.entity.Webprofile;
import be.depairon.espaceclient.entity.Webrole;
import be.depairon.espaceclient.repository.WebprofileRepository;
import be.depairon.espaceclient.repository.WebuserroleRepository;
import be.depairon.espaceclient.service.WebprofileService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebprofileServiceImpl implements WebprofileService {

    private final WebprofileRepository webprofileRepository;
    private final WebPermissionDao webPermissionDao;
    private final WebuserroleRepository webuserroleRepository;

    public WebprofileServiceImpl(WebprofileRepository webprofileRepository, WebPermissionDao webPermissionDao, WebuserroleRepository webuserroleRepository) {
        this.webprofileRepository = webprofileRepository;
        this.webPermissionDao = webPermissionDao;
        this.webuserroleRepository = webuserroleRepository;
    }

    @Override
    public Webprofile getProfile(String profileName) {
//        return webprofileRepository.findByName(profileName).orElse(null);
        List<Object[]> permissionList = webPermissionDao.getPermission(profileName);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            Webprofile webprofile = new Webprofile();
            List<Webrole> webroleList = new ArrayList<>();
            webprofile.setId(((BigInteger) permissionList.get(0)[0]).longValue());
            webprofile.setName((String) permissionList.get(0)[1]);
            webprofile.setDescription((String) permissionList.get(0)[2]);
            webprofile.setNoextacces(((BigDecimal) permissionList.get(0)[3]).toBigInteger());

            for (Object[] objects : permissionList) {
                Webrole webrole = new Webrole();
                webrole.setId(((BigInteger) objects[4]).longValue());
                webrole.setRole((String) objects[5]);
                webrole.setComment((String) objects[6]);
                webrole.setHasAccess(((Integer) objects[7]) == 1);
                webroleList.add(webrole);
            }
            webprofile.setWebroleList(webroleList);
            return webprofile;
        } else {
            return null;
        }
    }

    @Override
    public List<Object[]> getUserPermissions(String userId) {
        List<Object[]> permissionList = webPermissionDao.getUserPermission(userId);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            return permissionList;
        }
        return null;
    }

    @Override
    public List<Object[]> getAllUserPermissions(String userId) {
        List<Object[]> permissionList = webPermissionDao.getAllUserPermissions(userId);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            return permissionList;
        }
        return null;
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

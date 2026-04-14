package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dao.EglobalUserDao;
import be.depairon.espaceclient.entity.EglobalUser;

import java.util.List;

public interface EglobalUserService {
    EglobalUser getByUserId(String userid);
    List<EglobalUser> getCompaniesByEmail(String email);
    EglobalUser getByEmailAndCompany(String email, String usercmp);
}

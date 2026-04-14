package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.EglobalUser;

import java.util.List;

public interface EglobalUserDao {
    List<EglobalUser> getByEmail(String email);
    EglobalUser getByEmailAndCompany(String email, String usercmp);
}

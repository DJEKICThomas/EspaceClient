package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.User;

import java.util.List;

public interface UserService {

    List<User> getList(String customer);

    User getUser(String username);

    User getUserNoCondition(String username);

    boolean checkExist(String userId);

    User createOrUpdate(User user);

    User getUserById(Long id);

    List<User> getList();

    List<User> getList(User user);

    void delete(Long id);

    String getEGlobalUserId(User user);

    List<User> getByGestionnaireCompanies(String gestionnaireUserId);

    User getByResetToken(String resetToken);
}

package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getList(String customer);

    User getByUsername(String username);

    User getByUsernameNoCondition(String username);

    String getEGlobalUserId(User user);

    List<User> getByGestionnaireCompanies(String userId);

    User getByResetToken(String resetToken);
}

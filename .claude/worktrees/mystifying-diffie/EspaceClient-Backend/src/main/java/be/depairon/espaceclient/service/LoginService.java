package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.User;

public interface LoginService {

    User login(String username, String password);
}

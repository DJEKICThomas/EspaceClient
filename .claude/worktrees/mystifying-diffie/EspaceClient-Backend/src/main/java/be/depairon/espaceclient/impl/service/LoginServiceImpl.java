package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.service.LoginService;
import be.depairon.espaceclient.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserService userService;

    public LoginServiceImpl(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public User login(String username, String password) {
        User user = userService.getUser(username);
        if (user != null) {
            boolean isPasswordOk = StringUtils.trim(user.getPassword()).equals(password);
            if (isPasswordOk) {
                return user;
            }
        }
        return null;
    }
}
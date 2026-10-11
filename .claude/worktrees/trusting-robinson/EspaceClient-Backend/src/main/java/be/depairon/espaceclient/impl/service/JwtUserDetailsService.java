package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService /*implements UserDetailsService*/ {
    // FIXME IMPLEMENTS THIS IN LOGINSERVICEIMPL

//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private PasswordEncoder bcryptEncoder;
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        be.depairon.espaceclient.entity.User byUsername = this.userDao.getByUsername(username);
//        if (byUsername != null) {
//            return new User(byUsername.getUserid(), bcryptEncoder.encode(StringUtils.trim(byUsername.getPassword())),
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//    }
}

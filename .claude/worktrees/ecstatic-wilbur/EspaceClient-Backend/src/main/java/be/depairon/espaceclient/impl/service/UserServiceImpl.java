package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.UserDao;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.enumeration.WebUserProfile;
import be.depairon.espaceclient.repository.UserRepository;
import be.depairon.espaceclient.service.UserService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static be.depairon.espaceclient.enumeration.WebUserProfile.INCONNU;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getList(String customer) {
        return userDao.getList(customer);
    }

    @Override
    @Transactional
    public List<User> getList() {
        return IteratorUtils.toList(userRepository.findAll().iterator());
    }

    @Override
    public List<User> getList(User user) {
        if (user.getProfile().equals(WebUserProfile.ADMINISTRATEUR.toString())) {
            return getList();
        } else {
            return userRepository.findAllByProfileNot(WebUserProfile.ADMINISTRATEUR.toString());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserNoCondition(String username) {
        return userDao.getByUsernameNoCondition(username);
    }

    @Override
    @Transactional
    public boolean checkExist(String userId) {
        return userRepository.existsByUseridIgnoreCase(userId);
    }

    @Override
    @Transactional
    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setActive(-10);
            userRepository.save(user);
        } else {
            System.out.println("Erreur : Utilisateur avec l'ID " + id + " introuvable.");
        }
    }

    @Override
    @Transactional
    public String getEGlobalUserId(User user) {
        WebUserProfile profile = WebUserProfile.getWebUserProfile(user.getProfile());
        if (!profile.equals(INCONNU)) {
            return userDao.getEGlobalUserId(user);
        } else {
            return "";
        }
    }

    @Override
    public List<User> getByGestionnaireCompanies(String gestionnaireUserId) {
        return userDao.getByGestionnaireCompanies(gestionnaireUserId);
    }
}

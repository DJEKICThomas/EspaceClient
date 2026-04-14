package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.EglobalUserDao;
import be.depairon.espaceclient.entity.EglobalUser;
import be.depairon.espaceclient.repository.EglobalUserRepository;
import be.depairon.espaceclient.service.EglobalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EglobalUserServiceImpl implements EglobalUserService {

    private final EglobalUserRepository eglobalUserRepository;
    private final EglobalUserDao eglobalUserDao;

    @Autowired
    public EglobalUserServiceImpl(EglobalUserRepository eglobalUserRepository, EglobalUserDao eglobalUserDao) {
        this.eglobalUserRepository = eglobalUserRepository;
        this.eglobalUserDao = eglobalUserDao;
    }

    @Override
    public EglobalUser getByUserId(String userid) {
        return eglobalUserRepository.findByUserid(userid).orElse(null);
    }

    @Override
    public List<EglobalUser> getCompaniesByEmail(String email){
        return eglobalUserDao.getByEmail(email);
    }

    @Override
    public EglobalUser getByEmailAndCompany(String email, String usercmp){
        return eglobalUserDao.getByEmailAndCompany(email, usercmp);
    }
}

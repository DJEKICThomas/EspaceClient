package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.EglobalUser;
import be.depairon.espaceclient.repository.EglobalUserRepository;
import be.depairon.espaceclient.service.EglobalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class EglobalUserServiceImpl implements EglobalUserService {

    private final EglobalUserRepository eglobalUserRepository;

    @Autowired
    public EglobalUserServiceImpl(EglobalUserRepository eglobalUserRepository) {
        this.eglobalUserRepository = eglobalUserRepository;
    }

    @Override
    public EglobalUser getByUserId(String userid) {
        return eglobalUserRepository.findByUserid(userid).orElse(null);
    }
}

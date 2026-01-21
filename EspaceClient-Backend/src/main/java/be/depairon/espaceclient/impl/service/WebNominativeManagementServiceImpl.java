package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.WebNominativeManagementDao;
import be.depairon.espaceclient.entity.NominativeManagement;
import be.depairon.espaceclient.service.WebNominativeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebNominativeManagementServiceImpl implements WebNominativeManagementService {

    private final WebNominativeManagementDao webNominativeManagementDao;

    @Autowired
    public WebNominativeManagementServiceImpl(WebNominativeManagementDao webNominativeManagementDao) {
        this.webNominativeManagementDao = webNominativeManagementDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getNativeList(String customer) {
        return webNominativeManagementDao.getList(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NominativeManagement> getListByPorteur(Long cusId, String carrier) {
        return webNominativeManagementDao.getListByPorteur(cusId, carrier);
    }

    @Override
    public List<Object[]> getNativeListByPorteur(Long cusId, String carrier) {
        return webNominativeManagementDao.getNativeListByPorteur(cusId, carrier);
    }

}

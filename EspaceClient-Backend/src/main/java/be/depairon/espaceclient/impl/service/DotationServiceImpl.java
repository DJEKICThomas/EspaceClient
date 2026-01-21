package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.DotationDao;
import be.depairon.espaceclient.entity.Dotation;
import be.depairon.espaceclient.service.DotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DotationServiceImpl implements DotationService {

    private final DotationDao dotationDao;

    @Autowired
    public DotationServiceImpl(DotationDao dotationDao) {
        this.dotationDao = dotationDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dotation> getList(String customer) {
        return dotationDao.getList(customer);
    }
}

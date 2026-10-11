package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.WebManagedCustomerDao;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.service.WebManagedCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebManagedCustomerServiceImpl implements WebManagedCustomerService {

    private final WebManagedCustomerDao webManagedCustomerDao;

    @Autowired
    public WebManagedCustomerServiceImpl(WebManagedCustomerDao webManagedCustomerDao) {
        this.webManagedCustomerDao = webManagedCustomerDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManagedCustomer> getList(@Nullable String userId) {
        return webManagedCustomerDao.getList(userId);
    }
}

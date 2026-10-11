package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getList() {
        return customerDao.getList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getFilteredList(String filter) {
        return customerDao.getFilteredList(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getByNcsNda(String ncs, int nda) {
        return customerDao.getByNcsNda(ncs, nda);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getById(long id) {
        return customerDao.getById(id);
    }
}

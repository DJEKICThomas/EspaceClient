package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getList(String company);
    List<Customer> getFilteredList(String filter, String company);
    Customer getByNcsNda(String ncs, int nda);
    Customer getById(long id);
}

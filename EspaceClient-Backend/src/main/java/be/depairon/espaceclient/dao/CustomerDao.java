package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> getList();
    List<Customer> getFilteredList(String filter);
    Customer getByNcsNda(String ncs, int nda);
    Customer getById(long id);
}

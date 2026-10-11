package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.ManagedCustomer;

import java.util.List;

public interface WebManagedCustomerDao {

    List<ManagedCustomer> getList(String userId);
}

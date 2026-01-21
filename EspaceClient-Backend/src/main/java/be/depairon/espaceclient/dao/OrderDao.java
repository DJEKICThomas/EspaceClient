package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> getList(String customer);

}

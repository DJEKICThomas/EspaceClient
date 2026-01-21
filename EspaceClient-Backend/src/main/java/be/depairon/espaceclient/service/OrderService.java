package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getList(String customer);

}

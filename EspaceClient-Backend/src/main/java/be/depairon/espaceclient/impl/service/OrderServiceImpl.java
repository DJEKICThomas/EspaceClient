package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.OrderDao;
import be.depairon.espaceclient.entity.Order;
import be.depairon.espaceclient.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getList(String customer) {
        return orderDao.getList(customer);
    }

}

package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.OrderController;
import be.depairon.espaceclient.dto.OrderDTO;
import be.depairon.espaceclient.entity.Art;
import be.depairon.espaceclient.entity.ArtDes;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.Order;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.OrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OrderControllerImpl extends GenericController implements OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderControllerImpl(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getList(String customer, String token) {
        List<Order> orders = orderService.getList(customer);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orders)) {
            orderDTOList = orders.stream()
                    .map(this::convert)
                    .toList()
                    .stream()
                    .sorted(Comparator.comparing(OrderDTO::getArticle))
                    .toList();
        }
        return orderDTOList;
    }

    private OrderDTO convert(Order order) {
        Map<String, String> customersMap = new HashMap<>();
        OrderDTO orderDTO = mapper.map(order, OrderDTO.class);
        long code = order.getCode();
        if (code != 0L) {
            orderDTO.setCode(String.valueOf(order.getCode()));
        }
        Art art = order.getArticle();
        if (art != null) {
            List<ArtDes> descriptions = art.getDescriptions();
            ArtDes artDes = descriptions.stream()
                    .filter(des -> des.getArtDesLng().trim().equalsIgnoreCase("fr"))
                    .findFirst().orElse(null);
            if (artDes != null) {
                String article = artDes.getArtDesDes();
                orderDTO.setArticle(article);
            }
        }
        Customer customerSite = order.getCustomer();
        if (customerSite != null) {
            orderDTO.setSite(customerSite.getCusNm1());
            String customerName = customersMap.getOrDefault(order.getNcs(), null);
            if (customerName == null) {
                Customer mainCustomer = customerService.getByNcsNda(order.getNcs(), 0);
                if (mainCustomer != null) {
                    customersMap.put(mainCustomer.getCusNcs(), mainCustomer.getCusNm1());
                    customerName = mainCustomer.getCusNm1();
                }
            }
            orderDTO.setCustomer(customerName);
        }
        String from = order.getFrom().trim();
        String source = switch (from) {
            case "SA" -> "Depairon SA";
            case "DISTRI" -> "Depairon Distribution";
            default -> "";
        };
        orderDTO.setFrom(source);
        return orderDTO;
    }
}

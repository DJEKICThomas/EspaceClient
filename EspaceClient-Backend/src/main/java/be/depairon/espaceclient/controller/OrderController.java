package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.OrderDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/orders")
public interface OrderController {

    @GetMapping
    List<OrderDTO> getList(@RequestParam("customer") String customer, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

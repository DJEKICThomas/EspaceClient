package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.CustomerController;
import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.dto.CustomerDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.service.CustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerControllerImpl extends GenericController implements CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getList() {
        List<Customer> customers = customerService.getList();
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(customers)) {
            customerDTOList = customers.stream().map(this::convert).toList();
        }
        return customerDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getListByName(String filter, String token) {
        List<Customer> customers = customerService.getFilteredList(filter);
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(customers)) {
            customerDTOList = customers.stream().distinct().map(this::convert).toList();
        }
        return customerDTOList;
    }

    private CustomerDTO convert(Customer customer) {
        CustomerDTO customerDTO = mapper.map(customer, CustomerDTO.class);
        customerDTO.setCusNcs(StringUtils.trim(customer.getCusNcs()));
        String customerName = StringUtils.trim(customer.getCusNm1())
                + " (" + StringUtils.trim(customer.getCusNcs()) + ")";
        customerDTO.setCusNm1(customerName);
        return customerDTO;
    }
}

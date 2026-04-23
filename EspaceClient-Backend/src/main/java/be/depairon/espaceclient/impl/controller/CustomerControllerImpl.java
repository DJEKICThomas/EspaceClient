package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.CustomerController;
import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.dto.WebManagedCustomerDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerControllerImpl extends GenericController implements CustomerController{

    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService){
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebManagedCustomerDTO> getCustomers(String filter, String company, String token){
        List<Customer> customers;
        if(StringUtils.isNotBlank(filter)){
            customers = customerService.getFilteredList(filter, company);
        } else {
            customers = customerService.getList(company);
        }
        return customers.stream().map(this::convert).collect(Collectors.toList());
    }

    private WebManagedCustomerDTO convert(Customer customer){
        WebManagedCustomerDTO dto = new WebManagedCustomerDTO();
        dto.setNcs(StringUtils.trim(customer.getCusNcs()));
        String cusName = customer.getCusNm1();
        if(StringUtils.isNotBlank(cusName)){
            dto.setCustomerName(cusName.trim());
        }
        dto.setSelectedCustomer(StringUtils.trim(customer.getCusNm1()) + "(" + StringUtils.trim(customer.getCusNcs()) + ")");
        return dto;
    }
}

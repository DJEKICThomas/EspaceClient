package be.depairon.espaceclient.converter;

import be.depairon.espaceclient.dto.CustomerDTO;
import be.depairon.espaceclient.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {
    public CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer != null) {
            customerDTO.setId(customer.getId());
            customerDTO.setCusNcs(customer.getCusNcs());
            customerDTO.setCusNda(customer.getCusNda());
            customerDTO.setCusNm1(customer.getCusNm1());
            customerDTO.setCusNm2(customer.getCusNm2());
        }
        return customerDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setCusNcs(customerDTO.getCusNcs());
        customer.setCusNda(customerDTO.getCusNda());
        customer.setCusNm1(customerDTO.getCusNm1());
        customer.setCusNm2(customerDTO.getCusNm2());
        return customer;
    }

}

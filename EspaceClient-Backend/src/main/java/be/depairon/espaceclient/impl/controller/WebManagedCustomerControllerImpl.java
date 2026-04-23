package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.WebManagedCustomerController;
import be.depairon.espaceclient.dto.WebManagedCustomerDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.WebManagedCustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebManagedCustomerControllerImpl extends GenericController implements WebManagedCustomerController {

    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;

    public WebManagedCustomerControllerImpl(WebManagedCustomerService webManagedCustomerService,
                                            CustomerService customerService) {
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WebManagedCustomerDTO> getWebManagedCustomerForUser(String userId, String token, String company) {
        List<ManagedCustomer> listForUser = webManagedCustomerService.getList(userId, company);
        List<WebManagedCustomerDTO> listForUserDTO = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listForUser)) {
            listForUserDTO = listForUser.stream().map(this::convert).toList();
        }
        return listForUserDTO;
    }

    private WebManagedCustomerDTO convert(ManagedCustomer managedCustomer) {
        WebManagedCustomerDTO dto = mapper.map(managedCustomer, WebManagedCustomerDTO.class);
        long customerId = managedCustomer.getCustomer().getId();
        Customer customer = customerService.getById(customerId);
        if (customer != null) {
            dto.setSelectedCustomer(StringUtils.trim(customer.getCusNm1())
                    + " (" + StringUtils.trim(customer.getCusNcs()) + ")");
            dto.setNcs(StringUtils.trim(customer.getCusNcs()));
            String cusName = customer.getCusNm1();
            if (StringUtils.isNotBlank(cusName)) {
                dto.setCustomerName(cusName.trim());
            }
        }
        return dto;
    }
}

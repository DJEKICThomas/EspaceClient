package be.depairon.espaceclient.service;


import be.depairon.espaceclient.entity.ManagedCustomer;
import org.springframework.lang.Nullable;

import java.util.List;

public interface WebManagedCustomerService {

    List<ManagedCustomer> getList(@Nullable String userId, String company);
}

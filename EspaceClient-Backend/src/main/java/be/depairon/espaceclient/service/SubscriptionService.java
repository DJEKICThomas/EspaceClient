package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.CusCtrDTO;
import be.depairon.espaceclient.dto.SubscriptionDetailDto;

import java.util.List;


public interface SubscriptionService {
    List<CusCtrDTO> getAllSubscriptionsForUser(String userId, String ncs, String company);

    List<SubscriptionDetailDto> getSubscriptionDetails (String docNumber);
}
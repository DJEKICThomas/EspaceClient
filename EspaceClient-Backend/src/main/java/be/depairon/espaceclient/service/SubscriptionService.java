package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.CusCtrDTO;
import java.util.List;


public interface SubscriptionService {
    List<CusCtrDTO> getAllSubscriptionsForUser(String userId);
}
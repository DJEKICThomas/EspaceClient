package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.SubscriptionController;
import be.depairon.espaceclient.dto.CusCtrDTO;
import be.depairon.espaceclient.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionControllerImpl implements SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public List<CusCtrDTO> getSubscriptions(String userId) {
        return subscriptionService.getAllSubscriptionsForUser(userId);
    }
}
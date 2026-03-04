package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.CusCtrDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public interface SubscriptionController {

    @GetMapping("/user")
    List<CusCtrDTO> getSubscriptions(@RequestParam("userId") String userId);
}

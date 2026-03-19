package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.CusCtrDTO;
import be.depairon.espaceclient.dto.SubscriptionDetailDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public interface SubscriptionController {

    @GetMapping("/user")
    List<CusCtrDTO> getSubscriptions(@RequestParam("userId") String userId);

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SubscriptionDetailDto> getSubscriptionDetails(@RequestParam("docNumber") String docNumber);
}

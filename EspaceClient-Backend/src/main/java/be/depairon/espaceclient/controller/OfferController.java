package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.OfferDetailDTO;
import be.depairon.espaceclient.dto.OfferDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/offers")
public interface OfferController {

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OfferDTO> getAllOffersForUser(@RequestParam("userId") String userId);

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OfferDetailDTO> getOffersDetails(@RequestParam("document") String document);
}

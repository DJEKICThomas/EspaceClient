package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.OfferDetailDTO;
import be.depairon.espaceclient.dto.OfferDTO;

import java.util.List;

public interface OfferService {

    List<OfferDTO> getAllOffersForUser(String userId);
    List<OfferDetailDTO> getOfferDetails(String document);
}

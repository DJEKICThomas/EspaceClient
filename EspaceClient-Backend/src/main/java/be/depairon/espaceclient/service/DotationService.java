package be.depairon.espaceclient.service;


import be.depairon.espaceclient.entity.Dotation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DotationService {

    List<Dotation> getList(String customer);
}

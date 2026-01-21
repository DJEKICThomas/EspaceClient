package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.PorteurRepository;
import be.depairon.espaceclient.entity.Porteur;
import be.depairon.espaceclient.service.PorteurService;
import org.springframework.stereotype.Service;

@Service
public class PorteurServiceImpl implements PorteurService {

    private final PorteurRepository porteurRepository;

    public PorteurServiceImpl(PorteurRepository porteurRepository) {
        this.porteurRepository = porteurRepository;
    }

    @Override
    public Porteur getDetail(String ncs, int nda, String carrier) {
        return porteurRepository.findByCusPortNomAndCusPortNcsAndAndCusPortNda(carrier, ncs, nda);
    }
}

package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Porteur;

public interface PorteurService {
    Porteur getDetail(String ncs, int nda, String carrier);
}

package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Porteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorteurRepository extends CrudRepository<Porteur, Long> {
    Porteur findByCusPortNomAndCusPortNcsAndAndCusPortNda(String carrier, String ncs, int nda);// todo modifier  double AND AND
}

package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.BarcodeStop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcodeStopRepository extends CrudRepository<BarcodeStop, Long> {
    List<BarcodeStop> getAllByBarcode_Ncs(String ncs);
}
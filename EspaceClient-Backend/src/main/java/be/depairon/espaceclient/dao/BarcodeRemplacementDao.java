package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.BarcodeRemplacement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcodeRemplacementDao {
    List<BarcodeRemplacement> getByBarcode(int barcode);
}

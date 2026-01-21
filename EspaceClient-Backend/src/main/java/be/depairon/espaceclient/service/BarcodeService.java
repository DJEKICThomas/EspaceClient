package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Barcode;
import be.depairon.espaceclient.entity.BarcodeStop;
import be.depairon.espaceclient.entity.query.CustomBarcodeStop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BarcodeService {

    List<CustomBarcodeStop> getListForCustomer(String ncs);

    List<Barcode> getBarcodeList(long carrierId);

    List<Barcode> getBarcodeList(Long carrierId, Integer artId);
}

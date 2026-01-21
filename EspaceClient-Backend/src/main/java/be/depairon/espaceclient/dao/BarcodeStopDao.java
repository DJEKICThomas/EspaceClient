package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.query.CustomBarcodeStop;

import java.util.List;

public interface BarcodeStopDao {

    List<CustomBarcodeStop> getListForNcs(String ncs);
}

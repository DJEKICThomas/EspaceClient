package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.query.CustomBarcodeStop;
import be.depairon.espaceclient.repository.BarcodeRepository;
import be.depairon.espaceclient.dao.BarcodeStopDao;
import be.depairon.espaceclient.entity.Barcode;
import be.depairon.espaceclient.entity.BarcodeStop;
import be.depairon.espaceclient.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarcodeServiceImpl implements BarcodeService {

    private final BarcodeStopDao barcodeStopDao;
    private final BarcodeRepository barcodeRepository;

    @Autowired
    public BarcodeServiceImpl(BarcodeStopDao barcodeStopDao, BarcodeRepository barcodeRepository) {
        this.barcodeStopDao = barcodeStopDao;
        this.barcodeRepository = barcodeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomBarcodeStop> getListForCustomer(String ncs) {
        return barcodeStopDao.getListForNcs(ncs);
    }

    @Override
    public List<Barcode> getBarcodeList(long carrierId) {
        return barcodeRepository.findAllByPorteurIdAndFlag(carrierId, "");
    }

    @Override
    public List<Barcode> getBarcodeList(Long carrierId, Integer artId) {
        return barcodeRepository.findAllByPorteurIdAndArticleArtIinAndFlag(carrierId, artId, "");
    }
}

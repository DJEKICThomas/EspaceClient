package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CusXdcDao;
import be.depairon.espaceclient.entity.CusXdc;
import be.depairon.espaceclient.service.CusXdcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CusXdcServiceImpl implements CusXdcService {

    private CusXdcDao cusXdcDao;

    @Autowired
    public CusXdcServiceImpl(CusXdcDao cusXdcDao) {
        this.cusXdcDao = cusXdcDao;
    }

    @Override
    public List<CusXdc> listCusXdc(String ncs, int nda) {
        return cusXdcDao.listCusXdc(ncs, nda);
    }
}

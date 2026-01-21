package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CusXdcDao;
import be.depairon.espaceclient.dao.EntryControlDao;
import be.depairon.espaceclient.entity.CusXdc;
import be.depairon.espaceclient.service.EntryControlService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class EntryControlServiceImpl implements EntryControlService {

    private final EntryControlDao entryControlDao;
    private final CusXdcDao cusXdcDao;

    @Autowired
    public EntryControlServiceImpl(EntryControlDao entryControlDao, CusXdcDao cusXdcDao) {
        this.entryControlDao = entryControlDao;
        this.cusXdcDao = cusXdcDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getNativeList(String customer) {
        int hisYear = 1;
        List<CusXdc> cusXdcList = this.cusXdcDao.listCusXdc(customer, 0);
        if (CollectionUtils.isNotEmpty(cusXdcList)) {
            BigInteger cusXdcHis = cusXdcList.get(0).getCusXdcHis();
            hisYear = cusXdcHis != null && cusXdcHis.intValue() >= 1 ? cusXdcHis.intValue() : 1;
        }
        return entryControlDao.getNativeList(customer, hisYear);
    }

    @Override
    public List<Object[]> getNativeList(String customer, Long skip, Long take, String whereClause, String orderByClause) {
        return entryControlDao.getNativeList(customer, skip, take, whereClause, orderByClause);
    }

    @Override
    public Integer countNativeList(String customer, String whereClause) {
        return entryControlDao.countNativeList(customer, whereClause);
    }
}

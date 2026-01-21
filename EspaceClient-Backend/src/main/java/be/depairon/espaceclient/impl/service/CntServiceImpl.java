package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.Cnt;
import be.depairon.espaceclient.repository.CntRepository;
import be.depairon.espaceclient.service.CntService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CntServiceImpl implements CntService {

    private final CntRepository cntRepository;

    @Autowired
    public CntServiceImpl(CntRepository cntRepository) {
        this.cntRepository = cntRepository;
    }

    @Override
    @Transactional
    public String createNewRequestTicket(LocalDate date) {
        String code = "REQ" + date.getYear();
            Cnt cnt = cntRepository.findByCntcode(code).orElse(null);
            if (cnt == null) {
                cnt = new Cnt();
                cnt.setCntnr(1);
                cnt.setCom("DEP");
                cnt.setDep("DEP");
                cnt.setLcnt(0);
                cnt.setCntcode(code);
            } else {
                cnt.setCntnr(cnt.getCntnr() + 1);
        }
        String ticket = date.getYear() + "/" + StringUtils.leftPad(String.valueOf(cnt.getCntnr()), 5, "0");
        cntRepository.save(cnt);
        return ticket;
    }
}

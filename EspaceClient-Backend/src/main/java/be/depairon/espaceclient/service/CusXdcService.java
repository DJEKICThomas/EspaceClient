package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.CusXdc;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CusXdcService {
    List<CusXdc> listCusXdc(String ncs, int nda);
}

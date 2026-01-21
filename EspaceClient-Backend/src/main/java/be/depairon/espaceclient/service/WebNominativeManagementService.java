package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.NominativeManagement;
import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface WebNominativeManagementService {

    List<Object[]> getNativeList(String customer);

    List<NominativeManagement> getListByPorteur(Long cusId, @Nullable String carrier);

    List<Object[]> getNativeListByPorteur(Long cusId, @Nullable String carrier);
}
